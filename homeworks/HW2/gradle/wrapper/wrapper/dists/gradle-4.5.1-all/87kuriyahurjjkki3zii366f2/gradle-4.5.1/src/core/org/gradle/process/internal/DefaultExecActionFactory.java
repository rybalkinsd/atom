/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.process.internal;

import org.gradle.api.internal.file.FileResolver;
import org.gradle.internal.concurrent.DefaultExecutorFactory;
import org.gradle.internal.concurrent.Stoppable;
import org.gradle.internal.reflect.Instantiator;

import java.util.concurrent.Executor;

public class DefaultExecActionFactory implements ExecFactory, Stoppable {
    private final FileResolver fileResolver;
    private final DefaultExecutorFactory executorFactory = new DefaultExecutorFactory();
    private final Executor executor;

    public DefaultExecActionFactory(FileResolver fileResolver) {
        this.fileResolver = fileResolver;
        executor = executorFactory.create("Exec process");
    }

    @Override
    public void stop() {
        executorFactory.stop();
    }

    @Override
    public ExecFactory forContext(FileResolver fileResolver, Instantiator instantiator) {
        return new DecoratingExecActionFactory(fileResolver, instantiator, executor);
    }

    @Override
    public ExecAction newDecoratedExecAction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExecAction newExecAction() {
        return new DefaultExecAction(fileResolver, executor);
    }

    @Override
    public JavaExecAction newDecoratedJavaExecAction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JavaExecAction newJavaExecAction() {
        return new DefaultJavaExecAction(fileResolver, executor);
    }

    @Override
    public ExecHandleBuilder newExec() {
        return new DefaultExecHandleBuilder(fileResolver, executor);
    }

    @Override
    public JavaExecHandleBuilder newJavaExec() {
        return new JavaExecHandleBuilder(fileResolver, executor);
    }

    private static class DecoratingExecActionFactory implements ExecFactory {
        private final FileResolver fileResolver;
        private final Instantiator instantiator;
        private final Executor executor;

        DecoratingExecActionFactory(FileResolver fileResolver, Instantiator instantiator, Executor executor) {
            this.fileResolver = fileResolver;
            this.instantiator = instantiator;
            this.executor = executor;
        }

        @Override
        public ExecFactory forContext(FileResolver fileResolver, Instantiator instantiator) {
            return new DecoratingExecActionFactory(fileResolver, instantiator, executor);
        }

        @Override
        public ExecAction newExecAction() {
            return new DefaultExecAction(fileResolver, executor);
        }

        @Override
        public JavaExecAction newJavaExecAction() {
            return new DefaultJavaExecAction(fileResolver, executor);
        }

        @Override
        public ExecHandleBuilder newExec() {
            return new DefaultExecHandleBuilder(fileResolver, executor);
        }

        @Override
        public JavaExecHandleBuilder newJavaExec() {
            return new JavaExecHandleBuilder(fileResolver, executor);
        }

        @Override
        public ExecAction newDecoratedExecAction() {
            return instantiator.newInstance(DefaultExecAction.class, fileResolver, executor);
        }

        @Override
        public JavaExecAction newDecoratedJavaExecAction() {
            return instantiator.newInstance(DefaultJavaExecAction.class, fileResolver, executor);
        }
    }
}
