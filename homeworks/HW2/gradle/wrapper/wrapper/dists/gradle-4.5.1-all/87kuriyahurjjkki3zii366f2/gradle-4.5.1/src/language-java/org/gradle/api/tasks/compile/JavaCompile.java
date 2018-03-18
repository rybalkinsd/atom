/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.tasks.compile;

import com.google.common.collect.ImmutableList;
import org.gradle.api.Incubating;
import org.gradle.api.JavaVersion;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.internal.changedetection.changes.IncrementalTaskInputsInternal;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.internal.tasks.JavaToolChainFactory;
import org.gradle.api.internal.tasks.compile.AnnotationProcessorDetector;
import org.gradle.api.internal.tasks.compile.CleaningJavaCompiler;
import org.gradle.api.internal.tasks.compile.CompilerForkUtils;
import org.gradle.api.internal.tasks.compile.DefaultJavaCompileSpec;
import org.gradle.api.internal.tasks.compile.DefaultJavaCompileSpecFactory;
import org.gradle.api.internal.tasks.compile.JavaCompileSpec;
import org.gradle.api.internal.tasks.compile.incremental.IncrementalCompilerFactory;
import org.gradle.api.internal.tasks.compile.incremental.analyzer.ClassAnalysisCache;
import org.gradle.api.internal.tasks.compile.incremental.cache.CompileCaches;
import org.gradle.api.internal.tasks.compile.incremental.cache.GeneralCompileCaches;
import org.gradle.api.internal.tasks.compile.incremental.deps.LocalClassSetAnalysisStore;
import org.gradle.api.internal.tasks.compile.incremental.jar.JarSnapshotCache;
import org.gradle.api.internal.tasks.compile.incremental.jar.LocalJarClasspathSnapshotStore;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.CompileClasspath;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.WorkResult;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.cache.CacheRepository;
import org.gradle.internal.hash.FileHasher;
import org.gradle.internal.hash.StreamHasher;
import org.gradle.jvm.internal.toolchain.JavaToolChainInternal;
import org.gradle.jvm.platform.JavaPlatform;
import org.gradle.jvm.platform.internal.DefaultJavaPlatform;
import org.gradle.jvm.toolchain.JavaToolChain;
import org.gradle.language.base.internal.compile.Compiler;
import org.gradle.language.base.internal.compile.CompilerUtil;

import javax.inject.Inject;

/**
 * Compiles Java source files.
 *
 * <pre class='autoTested'>
 *     apply plugin: 'java'
 *
 *     tasks.withType(JavaCompile) {
 *         //enable compilation in a separate daemon process
 *         options.fork = true
 *
 *         //enable incremental compilation
 *         options.incremental = true
 *     }
 * </pre>
 */
@CacheableTask
public class JavaCompile extends AbstractCompile {
    private final CompileOptions compileOptions;
    private JavaToolChain toolChain;

    public JavaCompile() {
        CompileOptions compileOptions = getServices().get(ObjectFactory.class).newInstance(CompileOptions.class);
        this.compileOptions = compileOptions;
        CompilerForkUtils.doNotCacheIfForkingViaExecutable(compileOptions, getOutputs());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PathSensitive(PathSensitivity.NAME_ONLY)
    public FileTree getSource() {
        return super.getSource();
    }

    /**
     * Returns the tool chain that will be used to compile the Java source.
     *
     * @return The tool chain.
     */
    @Nested
    @Incubating
    public JavaToolChain getToolChain() {
        if (toolChain != null) {
            return toolChain;
        }
        return getJavaToolChainFactory().forCompileOptions(getOptions());
    }

    /**
     * Sets the tool chain that should be used to compile the Java source.
     *
     * @param toolChain The tool chain.
     */
    @Incubating
    public void setToolChain(JavaToolChain toolChain) {
        this.toolChain = toolChain;
    }

    @TaskAction
    protected void compile(IncrementalTaskInputs inputs) {
        if (!compileOptions.isIncremental()) {
            compile();
            return;
        }

        DefaultJavaCompileSpec spec = createSpec();
        CompileCaches compileCaches = createCompileCaches();
        IncrementalCompilerFactory factory = new IncrementalCompilerFactory(
            getFileOperations(), getStreamHasher(), getCachingFileHasher(), getPath(), createCompiler(spec), source, compileCaches, (IncrementalTaskInputsInternal) inputs, getEffectiveAnnotationProcessorPath());
        Compiler<JavaCompileSpec> compiler = factory.createCompiler();
        performCompilation(spec, compiler);
    }

    private CompileCaches createCompileCaches() {
        final GeneralCompileCaches generalCaches = getGeneralCompileCaches();
        final LocalClassSetAnalysisStore localClassSetAnalysisStore = generalCaches.createLocalClassSetAnalysisStore(getPath());
        final LocalJarClasspathSnapshotStore localJarClasspathSnapshotStore = generalCaches.createLocalJarClasspathSnapshotStore(getPath());
        return new CompileCaches() {
            public ClassAnalysisCache getClassAnalysisCache() {
                return generalCaches.getClassAnalysisCache();
            }

            public JarSnapshotCache getJarSnapshotCache() {
                return generalCaches.getJarSnapshotCache();
            }

            public LocalJarClasspathSnapshotStore getLocalJarClasspathSnapshotStore() {
                return localJarClasspathSnapshotStore;
            }

            public LocalClassSetAnalysisStore getLocalClassSetAnalysisStore() {
                return localClassSetAnalysisStore;
            }
        };
    }

    @Inject
    protected StreamHasher getStreamHasher() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected FileHasher getCachingFileHasher() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected FileOperations getFileOperations() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected GeneralCompileCaches getGeneralCompileCaches() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected CacheRepository getCacheRepository() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected JavaToolChainFactory getJavaToolChainFactory() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void compile() {
        DefaultJavaCompileSpec spec = createSpec();
        performCompilation(spec, createCompiler(spec));
    }


    private CleaningJavaCompiler createCompiler(JavaCompileSpec spec) {
        Compiler<JavaCompileSpec> javaCompiler = CompilerUtil.castCompiler(((JavaToolChainInternal) getToolChain()).select(getPlatform()).newCompiler(spec.getClass()));
        return new CleaningJavaCompiler(javaCompiler, getOutputs());
    }

    @Nested
    protected JavaPlatform getPlatform() {
        return new DefaultJavaPlatform(JavaVersion.toVersion(getTargetCompatibility()));
    }

    private void performCompilation(JavaCompileSpec spec, Compiler<JavaCompileSpec> compiler) {
        WorkResult result = compiler.execute(spec);
        setDidWork(result.getDidWork());
    }

    private DefaultJavaCompileSpec createSpec() {
        final DefaultJavaCompileSpec spec = new DefaultJavaCompileSpecFactory(compileOptions).create();
        spec.setSource(getSource());
        spec.setDestinationDir(getDestinationDir());
        spec.setWorkingDir(getProject().getProjectDir());
        spec.setTempDir(getTemporaryDir());
        spec.setCompileClasspath(ImmutableList.copyOf(getClasspath()));
        spec.setAnnotationProcessorPath(ImmutableList.copyOf(getEffectiveAnnotationProcessorPath()));
        spec.setTargetCompatibility(getTargetCompatibility());
        spec.setSourceCompatibility(getSourceCompatibility());
        spec.setCompileOptions(compileOptions);
        return spec;
    }

    /**
     * Returns the compilation options.
     *
     * @return The compilation options.
     */
    @Nested
    public CompileOptions getOptions() {
        return compileOptions;
    }

    @Override
    @CompileClasspath
    public FileCollection getClasspath() {
        return super.getClasspath();
    }

    /**
     * Returns the path to use for annotation processor discovery. Returns an empty collection when no processing should be performed, for example when no annotation processors are present in the compile classpath or annotation processing has been disabled.
     *
     * <p>You can specify this path using {@link CompileOptions#setAnnotationProcessorPath(FileCollection)} or {@link CompileOptions#setCompilerArgs(java.util.List)}. When not explicitly set using one of the methods on {@link CompileOptions}, the compile classpath will be used when there are annotation processors present in the compile classpath. Otherwise this path will be empty.
     *
     * <p>This path is always empty when annotation processing is disabled.</p>
     *
     * @since 3.4
     */
    @Incubating
    @Classpath
    public FileCollection getEffectiveAnnotationProcessorPath() {
        AnnotationProcessorDetector annotationProcessorDetector = getServices().get(AnnotationProcessorDetector.class);
        return annotationProcessorDetector.getEffectiveAnnotationProcessorClasspath(compileOptions, getClasspath());
    }
}
