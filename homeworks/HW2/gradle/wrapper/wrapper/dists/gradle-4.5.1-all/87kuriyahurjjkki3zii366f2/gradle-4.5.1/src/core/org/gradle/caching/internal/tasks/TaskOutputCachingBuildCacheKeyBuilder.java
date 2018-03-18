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

package org.gradle.caching.internal.tasks;

import org.gradle.api.internal.changedetection.state.ImplementationSnapshot;
import org.gradle.internal.hash.HashCode;

import java.util.Collection;

public interface TaskOutputCachingBuildCacheKeyBuilder {
    void appendTaskImplementation(ImplementationSnapshot taskImplementation);

    void appendTaskActionImplementations(Collection<ImplementationSnapshot> taskActionImplementations);

    void appendInputPropertyHash(String propertyName, HashCode hashCode);

    void appendOutputPropertyName(String propertyName);

    TaskOutputCachingBuildCacheKey build();

    void inputPropertyLoadedByUnknownClassLoader(String propertyName);
}
