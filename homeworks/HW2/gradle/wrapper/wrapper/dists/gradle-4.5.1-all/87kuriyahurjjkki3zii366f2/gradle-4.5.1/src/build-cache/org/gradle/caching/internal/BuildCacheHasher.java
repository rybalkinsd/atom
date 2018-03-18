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

package org.gradle.caching.internal;

import org.gradle.internal.hash.HashCode;

/**
 * Hasher used for building hashes for build cache keys.
 */
public interface BuildCacheHasher {
    BuildCacheHasher putByte(byte b);
    BuildCacheHasher putBytes(byte[] bytes);
    BuildCacheHasher putBytes(byte[] bytes, int off, int len);
    BuildCacheHasher putHash(HashCode hashCode);
    BuildCacheHasher putInt(int i);
    BuildCacheHasher putLong(long l);
    BuildCacheHasher putDouble(double d);
    BuildCacheHasher putBoolean(boolean b);
    BuildCacheHasher putString(CharSequence charSequence);
    BuildCacheHasher putNull();

    /**
     * Marks this build cache hash as invalid.
     */
    void markAsInvalid();

    /**
     * Whether the build cache hash is valid.
     */
    boolean isValid();

    /**
     * Returns the combined hash.
     *
     * If the build cache hash is invalid, an exception is thrown.
     */
    HashCode hash();
}
