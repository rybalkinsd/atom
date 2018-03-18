/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.tooling.internal.consumer.connection;

import org.gradle.tooling.BuildAction;
import org.gradle.tooling.internal.consumer.Distribution;
import org.gradle.tooling.internal.consumer.TestExecutionRequest;
import org.gradle.tooling.internal.consumer.parameters.ConsumerOperationParameters;
import org.gradle.tooling.model.internal.Exceptions;

/**
 * A {@code ConsumerConnection} implementation for a Gradle version that does not support the tooling API.
 *
 * <p>Used for versions < 1.0-milestone-3.</p>
 */
public class NoToolingApiConnection implements ConsumerConnection {
    private final Distribution distribution;

    public NoToolingApiConnection(Distribution distribution) {
        this.distribution = distribution;
    }

    public void stop() {
    }

    public String getDisplayName() {
        return distribution.getDisplayName();
    }

    public <T> T run(Class<T> type, ConsumerOperationParameters operationParameters) throws UnsupportedOperationException, IllegalStateException {
        throw Exceptions.unsupportedFeature(operationParameters.getEntryPointName(), distribution, "1.2");
    }

    public <T> T run(BuildAction<T> action, ConsumerOperationParameters operationParameters) throws UnsupportedOperationException, IllegalStateException {
        throw Exceptions.unsupportedFeature(operationParameters.getEntryPointName(), distribution, "1.8");
    }

    public void runTests(TestExecutionRequest testExecutionRequest, ConsumerOperationParameters operationParameters) {
        throw Exceptions.unsupportedFeature(operationParameters.getEntryPointName(), distribution, "2.6");
    }

}
