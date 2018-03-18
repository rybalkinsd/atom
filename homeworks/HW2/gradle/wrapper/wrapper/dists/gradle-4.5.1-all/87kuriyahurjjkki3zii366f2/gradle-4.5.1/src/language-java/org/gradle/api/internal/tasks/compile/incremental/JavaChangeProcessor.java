/*
 * Copyright 2013 the original author or authors.
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

import org.gradle.api.internal.tasks.compile.incremental.deps.DependentsSet;
import org.gradle.api.internal.tasks.compile.incremental.jar.PreviousCompilation;
import org.gradle.api.internal.tasks.compile.incremental.recomp.RecompilationSpec;
import org.gradle.api.tasks.incremental.InputFileDetails;
import it.unimi.dsi.fastutil.ints.IntSets;

class JavaChangeProcessor {

    private final SourceToNameConverter sourceToNameConverter;
    private final PreviousCompilation previousCompilation;

    public JavaChangeProcessor(PreviousCompilation previousCompilation, SourceToNameConverter sourceToNameConverter) {
        this.previousCompilation = previousCompilation;
        this.sourceToNameConverter = sourceToNameConverter;
    }

    public void processChange(InputFileDetails input, RecompilationSpec spec) {
        String className = sourceToNameConverter.getClassName(input.getFile());
        spec.getClassNames().add(className);
        DependentsSet actualDependents = previousCompilation.getDependents(className, IntSets.EMPTY_SET);
        if (actualDependents.isDependencyToAll()) {
            spec.setFullRebuildCause(actualDependents.getDescription(), input.getFile());
            return;
        }
        spec.getClassNames().addAll(actualDependents.getDependentClasses());
    }
}
