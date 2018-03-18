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

package org.gradle.internal.nativeintegration.filesystem.services;

import org.gradle.internal.file.FileMetadataSnapshot;
import org.gradle.internal.nativeintegration.filesystem.DefaultFileMetadata;
import org.gradle.internal.nativeintegration.filesystem.FileMetadataAccessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FallbackFileMetadataAccessor implements FileMetadataAccessor {
    @Override
    public FileMetadataSnapshot stat(File f) {
        if (!f.exists()) {
            return DefaultFileMetadata.missing();
        }
        if (f.isDirectory()) {
            return DefaultFileMetadata.directory();
        }
        return DefaultFileMetadata.file(f.lastModified(), f.length());
    }

    @Override
    @SuppressWarnings("Since15")
    public FileMetadataSnapshot stat(Path path) throws IOException {
        return stat(path.toFile());
    }
}
