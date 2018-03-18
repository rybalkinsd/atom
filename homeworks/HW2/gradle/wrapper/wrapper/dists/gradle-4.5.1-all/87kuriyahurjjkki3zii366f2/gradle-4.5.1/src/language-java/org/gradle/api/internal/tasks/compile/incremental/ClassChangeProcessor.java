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
package org.gradle.api.internal.tasks.compile.incremental;

import com.google.common.io.Files;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import org.gradle.api.internal.tasks.compile.incremental.asm.ClassDependenciesVisitor;
import org.gradle.api.internal.tasks.compile.incremental.deps.DependentsSet;
import org.gradle.api.internal.tasks.compile.incremental.jar.PreviousCompilation;
import org.gradle.api.internal.tasks.compile.incremental.recomp.RecompilationSpec;
import org.gradle.api.tasks.incremental.InputFileDetails;
import org.gradle.util.internal.PatchedClassReader;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class ClassChangeProcessor {

    private final PreviousCompilation previousCompilation;

    public ClassChangeProcessor(PreviousCompilation previousCompilation) {
        this.previousCompilation = previousCompilation;
    }

    public void processChange(final InputFileDetails input, final RecompilationSpec spec) {
        if (input.isRemoved()) {
            // this is a really heavyweight way of getting the dependencies of a file which was removed
            // hopefully this is not going to happen too often
            String className = previousCompilation.getClassName(input.getFile().getAbsolutePath());
            update(input, spec, className, IntSets.EMPTY_SET);
            return;
        }

        final ClassReader classReader;
        try {
            classReader = new PatchedClassReader(Files.toByteArray(input.getFile()));
            String className = classReader.getClassName().replaceAll("/", ".");
            IntSet constants = ClassDependenciesVisitor.retrieveConstants(classReader);
            update(input, spec, className, constants);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Unable to read class file: '%s'", input.getFile()));
        }


    }

    protected void update(InputFileDetails input, RecompilationSpec spec, String className, IntSet newConstants) {
        DependentsSet actualDependents = previousCompilation.getDependents(className, newConstants);
        if (actualDependents.isDependencyToAll()) {
            spec.setFullRebuildCause(actualDependents.getDescription(), input.getFile());
        } else {
            spec.getClassNames().addAll(actualDependents.getDependentClasses());
        }
    }
}
