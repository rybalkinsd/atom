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
package org.gradle.initialization;

import org.gradle.api.artifacts.component.ProjectComponentIdentifier;
import org.gradle.util.Path;

import java.util.Set;

public interface ProjectPathRegistry {
    /**
     * Returns an path for every project in a build, including projects from included builds.
     * Always returns the same set for the lifetime of the build.
     */
    Set<Path> getAllProjectPaths();

    /**
     * Returns an path for every implicit project in a build, including projects from included builds.
     */
    Set<Path> getAllImplicitProjectPaths();

    /**
     * Returns an path for every explicit project in a build, including projects from included builds.
     */
    Set<Path> getAllExplicitProjectPaths();

    /**
     * Returns a ProjectComponentIdentifier for the given identity path in this build.
     */
    ProjectComponentIdentifier getProjectComponentIdentifier(Path identityPath);
}
