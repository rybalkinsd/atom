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

import org.gradle.api.internal.tasks.compile.CleaningJavaCompiler;
import org.gradle.api.internal.tasks.compile.JavaCompileSpec;
import org.gradle.api.internal.tasks.compile.incremental.jar.JarClasspathSnapshot;
import org.gradle.api.internal.tasks.compile.incremental.jar.JarClasspathSnapshotProvider;
import org.gradle.api.internal.tasks.compile.incremental.jar.PreviousCompilation;
import org.gradle.api.internal.tasks.compile.incremental.recomp.RecompilationSpec;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.WorkResult;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.internal.time.Time;
import org.gradle.internal.time.Timer;

import java.util.Collection;

class SelectiveCompiler implements org.gradle.language.base.internal.compile.Compiler<JavaCompileSpec> {
    private static final Logger LOG = Logging.getLogger(SelectiveCompiler.class);
    private final IncrementalTaskInputs inputs;
    private final PreviousCompilation previousCompilation;
    private final CleaningJavaCompiler cleaningCompiler;
    private final RecompilationSpecProvider recompilationSpecProvider;
    private final IncrementalCompilationInitializer incrementalCompilationInitilizer;
    private final JarClasspathSnapshotProvider jarClasspathSnapshotProvider;

    public SelectiveCompiler(IncrementalTaskInputs inputs, PreviousCompilation previousCompilation, CleaningJavaCompiler cleaningCompiler,
                             RecompilationSpecProvider recompilationSpecProvider, IncrementalCompilationInitializer compilationInitializer, JarClasspathSnapshotProvider jarClasspathSnapshotProvider) {
        this.inputs = inputs;
        this.previousCompilation = previousCompilation;
        this.cleaningCompiler = cleaningCompiler;
        this.recompilationSpecProvider = recompilationSpecProvider;
        this.incrementalCompilationInitilizer = compilationInitializer;
        this.jarClasspathSnapshotProvider = jarClasspathSnapshotProvider;
    }

    @Override
    public WorkResult execute(JavaCompileSpec spec) {
        Timer clock = Time.startTimer();
        JarClasspathSnapshot jarClasspathSnapshot = jarClasspathSnapshotProvider.getJarClasspathSnapshot(spec.getCompileClasspath());
        RecompilationSpec recompilationSpec = recompilationSpecProvider.provideRecompilationSpec(inputs, previousCompilation, jarClasspathSnapshot);

        if (recompilationSpec.isFullRebuildNeeded()) {
            LOG.info("Full recompilation is required because {}. Analysis took {}.", recompilationSpec.getFullRebuildCause(), clock.getElapsed());
            return cleaningCompiler.execute(spec);
        }

        Collection<String> classNames = recompilationSpec.getClassNames();
        incrementalCompilationInitilizer.initializeCompilation(spec, classNames);
        if (spec.getSource().isEmpty()) {
            LOG.info("None of the classes needs to be compiled! Analysis took {}. ", clock.getElapsed());
            return new RecompilationNotNecessary();
        }

        try {
            //use the original compiler to avoid cleaning up all the files
            return cleaningCompiler.getCompiler().execute(spec);
        } finally {
            LOG.info("Incremental compilation of {} classes completed in {}.", classNames.size(), clock.getElapsed());
            LOG.debug("Recompiled classes {}", classNames);
        }
    }
}
