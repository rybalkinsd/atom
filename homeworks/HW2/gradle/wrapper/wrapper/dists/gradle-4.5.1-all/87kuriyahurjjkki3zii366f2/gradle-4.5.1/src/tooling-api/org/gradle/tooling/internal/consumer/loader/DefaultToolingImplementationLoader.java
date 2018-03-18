/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.tooling.internal.consumer.loader;

import org.gradle.initialization.BuildCancellationToken;
import org.gradle.internal.Factory;
import org.gradle.internal.classloader.FilteringClassLoader;
import org.gradle.internal.classloader.VisitableURLClassLoader;
import org.gradle.internal.classpath.ClassPath;
import org.gradle.internal.logging.progress.ProgressLoggerFactory;
import org.gradle.internal.service.DefaultServiceLocator;
import org.gradle.internal.service.ServiceLocator;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.UnsupportedVersionException;
import org.gradle.tooling.internal.adapter.ProtocolToModelAdapter;
import org.gradle.tooling.internal.consumer.ConnectionParameters;
import org.gradle.tooling.internal.consumer.Distribution;
import org.gradle.tooling.internal.consumer.connection.AbstractConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.ActionAwareConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.BuildActionRunnerBackedConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.CancellableConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.ConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.DeprecatedVersionConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.ModelBuilderBackedConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.ParameterAcceptingConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.NoToolingApiConnection;
import org.gradle.tooling.internal.consumer.connection.NonCancellableConsumerConnectionAdapter;
import org.gradle.tooling.internal.consumer.connection.ParameterValidatingConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.ShutdownAwareConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.TestExecutionConsumerConnection;
import org.gradle.tooling.internal.consumer.connection.UnsupportedOlderVersionConnection;
import org.gradle.tooling.internal.consumer.converters.ConsumerTargetTypeProvider;
import org.gradle.tooling.internal.consumer.versioning.ModelMapping;
import org.gradle.tooling.internal.consumer.versioning.VersionDetails;
import org.gradle.tooling.internal.protocol.BuildActionRunner;
import org.gradle.tooling.internal.protocol.ConnectionVersion4;
import org.gradle.tooling.internal.protocol.InternalBuildActionExecutor;
import org.gradle.tooling.internal.protocol.InternalBuildProgressListener;
import org.gradle.tooling.internal.protocol.InternalCancellableConnection;
import org.gradle.tooling.internal.protocol.InternalParameterAcceptingConnection;
import org.gradle.tooling.internal.protocol.ModelBuilder;
import org.gradle.tooling.internal.protocol.StoppableConnection;
import org.gradle.tooling.internal.protocol.test.InternalTestExecutionConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DefaultToolingImplementationLoader implements ToolingImplementationLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultToolingImplementationLoader.class);
    private final ClassLoader classLoader;

    public DefaultToolingImplementationLoader() {
        this(DefaultToolingImplementationLoader.class.getClassLoader());
    }

    DefaultToolingImplementationLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ConsumerConnection create(Distribution distribution, ProgressLoggerFactory progressLoggerFactory, InternalBuildProgressListener progressListener, ConnectionParameters connectionParameters, BuildCancellationToken cancellationToken) {
        LOGGER.debug("Using tooling provider from {}", distribution.getDisplayName());
        ClassLoader serviceClassLoader = createImplementationClassLoader(distribution, progressLoggerFactory, progressListener, connectionParameters.getGradleUserHomeDir(), cancellationToken);
        ServiceLocator serviceLocator = new DefaultServiceLocator(serviceClassLoader);
        try {
            Factory<ConnectionVersion4> factory = serviceLocator.findFactory(ConnectionVersion4.class);
            if (factory == null) {
                return new NoToolingApiConnection(distribution);
            }
            // ConnectionVersion4 is a part of the protocol and cannot be easily changed.
            ConnectionVersion4 connection = factory.create();

            ProtocolToModelAdapter adapter = new ProtocolToModelAdapter(new ConsumerTargetTypeProvider());
            ModelMapping modelMapping = new ModelMapping();

            // Adopting the connection to a refactoring friendly type that the consumer owns
            if (connection instanceof InternalParameterAcceptingConnection) {
                return createConnection(new ParameterAcceptingConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof InternalTestExecutionConnection) {
                return createConnection(new TestExecutionConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof StoppableConnection) {
                return createDeprecatedConnection(new ShutdownAwareConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof InternalCancellableConnection) {
                return createDeprecatedConnection(new CancellableConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof ModelBuilder && connection instanceof InternalBuildActionExecutor) {
                return createDeprecatedConnection(new ActionAwareConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof ModelBuilder) {
                return createDeprecatedConnection(new ModelBuilderBackedConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else if (connection instanceof BuildActionRunner) {
                return createDeprecatedConnection(new BuildActionRunnerBackedConsumerConnection(connection, modelMapping, adapter), connectionParameters);
            } else {
                return new UnsupportedOlderVersionConnection(connection, adapter);
            }
        } catch (UnsupportedVersionException e) {
            throw e;
        } catch (Throwable t) {
            throw new GradleConnectionException(String.format("Could not create an instance of Tooling API implementation using the specified %s.", distribution.getDisplayName()), t);
        }
    }

    private ConsumerConnection createConnection(AbstractConsumerConnection adaptedConnection, ConnectionParameters connectionParameters) {
        adaptedConnection.configure(connectionParameters);
        VersionDetails versionDetails = adaptedConnection.getVersionDetails();
        ConsumerConnection delegate = versionDetails.supportsCancellation() ? adaptedConnection : new NonCancellableConsumerConnectionAdapter(adaptedConnection);
        return new ParameterValidatingConsumerConnection(versionDetails, delegate);
    }

    private ConsumerConnection createDeprecatedConnection(AbstractConsumerConnection adaptedConnection, ConnectionParameters connectionParameters) {
        return new DeprecatedVersionConsumerConnection(createConnection(adaptedConnection, connectionParameters), adaptedConnection.getVersionDetails());
    }

    private ClassLoader createImplementationClassLoader(Distribution distribution, ProgressLoggerFactory progressLoggerFactory, InternalBuildProgressListener progressListener, File userHomeDir, BuildCancellationToken cancellationToken) {
        ClassPath implementationClasspath = distribution.getToolingImplementationClasspath(progressLoggerFactory, progressListener, userHomeDir, cancellationToken);
        LOGGER.debug("Using tooling provider classpath: {}", implementationClasspath);
        FilteringClassLoader.Spec filterSpec = new FilteringClassLoader.Spec();
        filterSpec.allowPackage("org.gradle.tooling.internal.protocol");
        FilteringClassLoader filteringClassLoader = new FilteringClassLoader(classLoader, filterSpec);
        return new VisitableURLClassLoader(filteringClassLoader, implementationClasspath);
    }
}
