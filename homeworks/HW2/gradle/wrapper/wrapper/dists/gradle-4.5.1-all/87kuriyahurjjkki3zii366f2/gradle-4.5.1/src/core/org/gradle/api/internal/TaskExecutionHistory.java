/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.api.internal;

import org.gradle.api.internal.tasks.OriginTaskExecutionMetadata;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Set;

public interface TaskExecutionHistory {
    /**
     * Returns the set of output files and directories which the task produced.
     */
    Set<File> getOutputFiles();

    /**
     * Returns if overlapping outputs were detected
     */
    @Nullable
    OverlappingOutputs getOverlappingOutputs();

    /**
     * The ID and execution time of origin of the tasks outputs.
     *
     * If the outputs were loaded from cache, the ID/time from the build that stored to cache.
     * If the outputs were previously built locally and now reused, the ID/time from the build that built them.
     *
     * Null if outputs are not being reused.
     */
    @Nullable
    OriginTaskExecutionMetadata getOriginExecutionMetadata();

}
