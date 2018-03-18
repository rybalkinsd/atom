/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.process.internal.worker.request;

import org.gradle.api.Action;
import org.gradle.api.internal.AsmBackedClassGenerator;
import org.gradle.api.internal.DefaultInstantiatorFactory;
import org.gradle.api.internal.InstantiatorFactory;
import org.gradle.cache.internal.CrossBuildInMemoryCacheFactory;
import org.gradle.internal.UncheckedException;
import org.gradle.internal.event.DefaultListenerManager;
import org.gradle.internal.operations.BuildOperationIdentifierRegistry;
import org.gradle.internal.remote.ObjectConnection;
import org.gradle.internal.remote.internal.hub.StreamFailureHandler;
import org.gradle.process.internal.worker.WorkerProcessContext;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

public class WorkerAction implements Action<WorkerProcessContext>, Serializable, RequestProtocol, StreamFailureHandler {
    private final String workerImplementationName;
    private transient CountDownLatch completed;
    private transient ResponseProtocol responder;
    private transient Throwable failure;
    private transient Class<?> workerImplementation;
    private transient Object implementation;
    private InstantiatorFactory instantiatorFactory;

    public WorkerAction(Class<?> workerImplementation) {
        this.workerImplementationName = workerImplementation.getName();
    }

    @Override
    public void execute(WorkerProcessContext workerProcessContext) {
        completed = new CountDownLatch(1);
        try {
            if (instantiatorFactory == null) {
                instantiatorFactory = new DefaultInstantiatorFactory(new AsmBackedClassGenerator(), new CrossBuildInMemoryCacheFactory(new DefaultListenerManager()));
            }
            workerImplementation = Class.forName(workerImplementationName);
            implementation = instantiatorFactory.inject(workerProcessContext.getServiceRegistry()).newInstance(workerImplementation);
        } catch (Throwable e) {
            failure = e;
        }

        ObjectConnection connection = workerProcessContext.getServerConnection();
        connection.addIncoming(RequestProtocol.class, this);
        responder = connection.addOutgoing(ResponseProtocol.class);
        connection.connect();

        try {
            completed.await();
        } catch (InterruptedException e) {
            throw UncheckedException.throwAsUncheckedException(e);
        }
    }

    @Override
    public void stop() {
        completed.countDown();
        BuildOperationIdentifierRegistry.clearCurrentOperationIdentifier();
    }

    @Override
    public void runThenStop(String methodName, Class<?>[] paramTypes, Object[] args, Object operationIdentifier) {
        try {
            run(methodName, paramTypes, args, operationIdentifier);
        } finally {
            stop();
        }
    }

    @Override
    public void run(String methodName, Class<?>[] paramTypes, Object[] args, Object operationIdentifier) {
        if (failure != null) {
            responder.infrastructureFailed(failure);
            return;
        }
        try {
            Method method = workerImplementation.getDeclaredMethod(methodName, paramTypes);
            BuildOperationIdentifierRegistry.setCurrentOperationIdentifier(operationIdentifier);
            Object result;
            try {
                result = method.invoke(implementation, args);
            } catch (InvocationTargetException e) {
                Throwable failure = e.getCause();
                if (failure instanceof NoClassDefFoundError) {
                    // Assume an infrastructure problem
                    responder.infrastructureFailed(failure);
                } else {
                    responder.failed(failure);
                }
                return;
            }
            responder.completed(result);
        } catch (Throwable t) {
            responder.infrastructureFailed(t);
        } finally {
            BuildOperationIdentifierRegistry.clearCurrentOperationIdentifier();
        }
    }

    @Override
    public void handleStreamFailure(Throwable t) {
        responder.failed(t);
    }
}
