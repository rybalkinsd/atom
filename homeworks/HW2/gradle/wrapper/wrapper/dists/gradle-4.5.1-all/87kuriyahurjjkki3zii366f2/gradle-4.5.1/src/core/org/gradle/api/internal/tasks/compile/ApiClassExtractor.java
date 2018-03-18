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

package org.gradle.api.internal.tasks.compile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Extracts an "API class" from an original "runtime class".
 */
public class ApiClassExtractor {

    private static final Pattern LOCAL_CLASS_PATTERN = Pattern.compile(".+\\$[0-9]+(?:[\\p{Alnum}_$]+)?$");

    private final Set<String> exportedPackages;
    private final boolean apiIncludesPackagePrivateMembers;

    public ApiClassExtractor(Set<String> exportedPackages) {
        this.exportedPackages = exportedPackages.isEmpty() ? null : exportedPackages;
        this.apiIncludesPackagePrivateMembers = exportedPackages.isEmpty();
    }

    /**
     * Indicates whether the class held by the given reader is a candidate for extraction to
     * an API class. Checks whether the class's package is in the list of packages
     * explicitly exported by the library (if any), and whether the class should be
     * included in the public API based on its visibility. If the list of exported
     * packages is empty (e.g. the library has not declared an explicit {@code api {...}}
     * specification, then package-private classes are included in the public API. If the
     * list of exported packages is non-empty (i.e. the library has declared an
     * {@code api {...}} specification, then package-private classes are excluded.
     *
     * <p>For these reasons, this method should be called as a test on every original
     * .class file prior to invoking processed through
     * {@link #extractApiClassFrom(ClassReader)}.</p>
     *
     * @param originalClassReader the reader containing the original class to evaluate
     * @return whether the given class is a candidate for API extraction
     */
    public boolean shouldExtractApiClassFrom(ClassReader originalClassReader) {
        if (!ApiMemberSelector.isCandidateApiMember(originalClassReader.getAccess(), apiIncludesPackagePrivateMembers)) {
            return false;
        }
        String originalClassName = originalClassReader.getClassName();
        if (isLocalClass(originalClassName)) {
            return false;
        }
        return exportedPackages == null
            || exportedPackages.contains(packageNameOf(originalClassName));
    }

    /**
     * Extracts an API class from a given original class.
     *
     * @param originalClassReader the reader containing the original class
     * @return bytecode of the API class extracted from the original class. Returns null when class should not be included, due to some reason that is not known until visited.
     */
    @Nullable
    public byte[] extractApiClassFrom(ClassReader originalClassReader) {
        ClassWriter apiClassWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ApiMemberSelector visitor = new ApiMemberSelector(originalClassReader.getClassName(), new MethodStubbingApiMemberAdapter(apiClassWriter), apiIncludesPackagePrivateMembers);
        originalClassReader.accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        if (visitor.isPrivateInnerClass()) {
            return null;
        }
        return apiClassWriter.toByteArray();
    }

    private static String packageNameOf(String internalClassName) {
        int packageSeparatorIndex = internalClassName.lastIndexOf('/');
        return packageSeparatorIndex > 0
            ? internalClassName.substring(0, packageSeparatorIndex).replace('/', '.')
            : "";
    }

    // See JLS3 "Binary Compatibility" (13.1)
    private static boolean isLocalClass(String className) {
        return LOCAL_CLASS_PATTERN.matcher(className).matches();
    }
}
