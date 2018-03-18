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
package org.gradle.api.internal.file.copy;


import org.gradle.api.Action;
import org.gradle.api.file.*;
import org.gradle.api.specs.Spec;

import java.util.Collection;
import java.util.List;

public interface CopySpecResolver {

    boolean isCaseSensitive();
    Integer getFileMode();
    Integer getDirMode();
    boolean getIncludeEmptyDirs();
    String getFilteringCharset();

    RelativePath getDestPath();

    FileTree getSource();

    FileTree getAllSource();

    Collection<? extends Action<? super FileCopyDetails>> getAllCopyActions();

    public List<String> getAllIncludes();

    public List<String> getAllExcludes();

    public List<Spec<FileTreeElement>> getAllIncludeSpecs();

    public List<Spec<FileTreeElement>> getAllExcludeSpecs();

    DuplicatesStrategy getDuplicatesStrategy();

    void walk(Action<? super CopySpecResolver> action);


}
