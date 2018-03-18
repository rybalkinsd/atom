/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.api.internal.tasks;

import org.gradle.api.NonNullApi;
import org.gradle.internal.id.UniqueId;

@NonNullApi
public class OriginTaskExecutionMetadata {

    private final UniqueId buildInvocationId;
    private final long executionTime;

    public OriginTaskExecutionMetadata(UniqueId buildInvocationId, long executionTime) {
        //noinspection ConstantConditions
        if (buildInvocationId == null) {
            throw new IllegalArgumentException("buildInvocationId cannot be null");
        }
        this.buildInvocationId = buildInvocationId;
        this.executionTime = executionTime;
    }

    public UniqueId getBuildInvocationId() {
        return buildInvocationId;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OriginTaskExecutionMetadata that = (OriginTaskExecutionMetadata) o;

        return executionTime == that.executionTime && buildInvocationId.equals(that.buildInvocationId);
    }

    @Override
    public int hashCode() {
        int result = buildInvocationId.hashCode();
        result = 31 * result + (int) (executionTime ^ (executionTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OriginTaskExecutionMetadata{"
            + "buildInvocationId=" + buildInvocationId
            + ", executionTime=" + executionTime
            + '}';
    }
}
