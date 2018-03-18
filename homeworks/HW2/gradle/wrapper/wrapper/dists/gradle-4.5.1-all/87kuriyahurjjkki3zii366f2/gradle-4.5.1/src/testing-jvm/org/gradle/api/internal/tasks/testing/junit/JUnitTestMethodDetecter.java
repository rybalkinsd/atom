/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.api.internal.tasks.testing.junit;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class JUnitTestMethodDetecter extends MethodVisitor {

    private final JUnitTestClassDetecter testClassDetecter;

    JUnitTestMethodDetecter(JUnitTestClassDetecter testClassDetecter) {
        super(Opcodes.ASM6);
        this.testClassDetecter = testClassDetecter;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("Lorg/junit/Test;".equals(desc)) {
            testClassDetecter.setTest(true);
        }
        return null;
    }
}
