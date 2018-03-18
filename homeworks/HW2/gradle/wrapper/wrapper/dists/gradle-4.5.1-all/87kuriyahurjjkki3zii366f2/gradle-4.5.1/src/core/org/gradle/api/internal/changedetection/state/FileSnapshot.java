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

package org.gradle.api.internal.changedetection.state;

import org.gradle.api.file.RelativePath;
import org.gradle.internal.file.FileType;
import org.gradle.internal.hash.HashCode;

/**
 * An immutable snapshot of file meta-data and content. The file may not exist.
 */
public interface FileSnapshot {

    /**
     * The absolute path of this file. Can safely be used as a cache key.
     */
    String getPath();

    /**
     * Returns the base name of this file.
     */
    String getName();

    /**
     * The type of file.
     */
    FileType getType();

    /**
     * Is this file a root element?
     */
    boolean isRoot();

    /**
     * Path of this file relative to the root from which it was included.
     */
    RelativePath getRelativePath();

    /**
     * Returns a snapshot of the contents of this file.
     */
    FileContentSnapshot getContent();

    /**
     * Creates a copy of this file details, replacing the content hash with the given value;
     */
    FileSnapshot withContentHash(HashCode contentHash);
}
