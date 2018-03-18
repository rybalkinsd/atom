/*
 * Copyright 2016 the original author or authors.
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

import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.changedetection.state.PathNormalizationStrategy;
import org.gradle.api.internal.file.collections.SimpleFileCollection;
import org.gradle.api.tasks.FileNormalizer;

import java.io.File;
import java.util.Collections;

class CacheableTaskOutputCompositeFilePropertyElementSpec implements CacheableTaskOutputFilePropertySpec {
    private final CompositeTaskOutputPropertySpec parentProperty;
    private final String propertySuffix;
    private final FileCollection files;
    private final File file;

    public CacheableTaskOutputCompositeFilePropertyElementSpec(CompositeTaskOutputPropertySpec parentProperty, String propertySuffix, File file) {
        this.parentProperty = parentProperty;
        this.propertySuffix = propertySuffix;
        this.files = new SimpleFileCollection(Collections.singleton(file));
        this.file = file;
    }

    @Override
    public String getPropertyName() {
        return parentProperty.getPropertyName() + propertySuffix;
    }

    @Override
    public FileCollection getPropertyFiles() {
        return files;
    }

    @Override
    public File getOutputFile() {
        return file;
    }

    @Override
    public OutputType getOutputType() {
        return parentProperty.getOutputType();
    }

    @Override
    public PathNormalizationStrategy getPathNormalizationStrategy() {
        return parentProperty.getPathNormalizationStrategy();
    }

    @Override
    public Class<? extends FileNormalizer> getNormalizer() {
        return GenericFileNormalizer.class;
    }

    @Override
    public int compareTo(TaskPropertySpec o) {
        return getPropertyName().compareTo(o.getPropertyName());
    }

    @Override
    public String toString() {
        return getPropertyName();
    }
}
