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

package org.gradle.api.internal.file;

import org.gradle.api.tasks.util.PatternSet;
import org.gradle.internal.Factory;
import org.gradle.internal.nativeintegration.filesystem.FileSystem;

import java.io.File;

public class BaseDirFileResolver extends AbstractBaseDirFileResolver {
    private final File baseDir;

    public BaseDirFileResolver(FileSystem fileSystem, File baseDir, Factory<PatternSet> patternSetFactory) {
        super(fileSystem, patternSetFactory);
        assert baseDir.isAbsolute() : String.format("base dir '%s' is not an absolute file.", baseDir);
        this.baseDir = baseDir;
    }

    @Override
    protected File getBaseDir() {
        return baseDir;
    }
}
