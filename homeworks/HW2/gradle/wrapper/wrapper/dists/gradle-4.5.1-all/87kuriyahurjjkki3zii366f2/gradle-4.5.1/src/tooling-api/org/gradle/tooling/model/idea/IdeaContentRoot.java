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

package org.gradle.tooling.model.idea;

import org.gradle.api.Incubating;
import org.gradle.tooling.model.DomainObjectSet;

import java.io.File;
import java.util.Set;

/**
 * Contains content root information.
 */
public interface IdeaContentRoot {

    /**
     * The content root directory.
     */
    File getRootDirectory();

    /**
     * The set of source directories.
     */
    DomainObjectSet<? extends IdeaSourceDirectory> getSourceDirectories();

    /**
     * The set of generated source directories. This is a subset of those directories returned by {@link #getSourceDirectories()}.
     *
     * @since 2.2
     */
    @Incubating
    DomainObjectSet<? extends IdeaSourceDirectory> getGeneratedSourceDirectories();

    /**
     * The set of test source directories.
     */
    DomainObjectSet<? extends IdeaSourceDirectory> getTestDirectories();

    /**
     * The set of generated test directories. This is a subset of those directories returned by {@link #getTestDirectories()}.
     *
     * @since 2.2
     */
    @Incubating
    DomainObjectSet<? extends IdeaSourceDirectory> getGeneratedTestDirectories();

    /**
     * The set of excluded directories.
     */
    Set<File> getExcludeDirectories();
}
