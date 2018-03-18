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

package org.gradle.language.cpp.internal;

import org.gradle.api.artifacts.ArtifactCollection;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.artifacts.result.ResolvedArtifactResult;
import org.gradle.api.attributes.Usage;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.internal.file.TemporaryFileProvider;
import org.gradle.api.internal.file.collections.FileCollectionAdapter;
import org.gradle.api.internal.file.collections.MinimalFileSet;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.language.cpp.CppBinary;
import org.gradle.language.cpp.CppPlatform;
import org.gradle.language.cpp.tasks.CppCompile;
import org.gradle.language.internal.DefaultNativeBinary;
import org.gradle.language.nativeplatform.internal.Names;
import org.gradle.nativeplatform.toolchain.internal.NativeToolChainInternal;
import org.gradle.nativeplatform.toolchain.internal.PlatformToolProvider;

import javax.inject.Inject;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultCppBinary extends DefaultNativeBinary implements CppBinary {
    private final Provider<String> baseName;
    private final boolean debuggable;
    private final boolean optimized;
    private final FileCollection sourceFiles;
    private final FileCollection includePath;
    private final FileCollection linkLibraries;
    private final FileCollection runtimeLibraries;
    private final DirectoryProperty objectsDir;
    private final CppPlatform targetPlatform;
    private final NativeToolChainInternal toolChain;
    private final PlatformToolProvider platformToolProvider;
    private final Configuration includePathConfiguration;
    private final Property<CppCompile> compileTaskProperty;
    private final Configuration implementation;

    public DefaultCppBinary(String name, ProjectLayout projectLayout, ObjectFactory objects, Provider<String> baseName, boolean debuggable, boolean optimized, FileCollection sourceFiles, FileCollection componentHeaderDirs, ConfigurationContainer configurations, Configuration implementation, CppPlatform targetPlatform, NativeToolChainInternal toolChain, PlatformToolProvider platformToolProvider) {
        super(name);
        this.baseName = baseName;
        this.debuggable = debuggable;
        this.optimized = optimized;
        this.sourceFiles = sourceFiles;
        this.objectsDir = projectLayout.directoryProperty();
        this.targetPlatform = targetPlatform;
        this.toolChain = toolChain;
        this.platformToolProvider = platformToolProvider;
        this.implementation = implementation;
        this.compileTaskProperty = objects.property(CppCompile.class);

        Names names = getNames();

        // TODO - reduce duplication with Swift binary
        Configuration includePathConfig = configurations.create(names.withPrefix("cppCompile"));
        includePathConfig.setCanBeConsumed(false);
        includePathConfig.getAttributes().attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, Usage.C_PLUS_PLUS_API));
        includePathConfig.getAttributes().attribute(DEBUGGABLE_ATTRIBUTE, debuggable);
        includePathConfig.getAttributes().attribute(OPTIMIZED_ATTRIBUTE, optimized);

        Configuration nativeLink = configurations.create(names.withPrefix("nativeLink"));
        nativeLink.setCanBeConsumed(false);
        nativeLink.getAttributes().attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, Usage.NATIVE_LINK));
        nativeLink.getAttributes().attribute(DEBUGGABLE_ATTRIBUTE, debuggable);
        nativeLink.getAttributes().attribute(OPTIMIZED_ATTRIBUTE, optimized);

        Configuration nativeRuntime = configurations.create(names.withPrefix("nativeRuntime"));
        nativeRuntime.setCanBeConsumed(false);
        nativeRuntime.getAttributes().attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.class, Usage.NATIVE_RUNTIME));
        nativeRuntime.getAttributes().attribute(DEBUGGABLE_ATTRIBUTE, debuggable);
        nativeRuntime.getAttributes().attribute(OPTIMIZED_ATTRIBUTE, optimized);

        includePathConfig.extendsFrom(implementation);
        nativeLink.extendsFrom(implementation);
        nativeRuntime.extendsFrom(implementation);

        includePathConfiguration = includePathConfig;
        includePath = componentHeaderDirs.plus(new FileCollectionAdapter(new IncludePath(includePathConfig)));
        linkLibraries = nativeLink;
        runtimeLibraries = nativeRuntime;
    }

    @Inject
    protected FileOperations getFileOperations() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected TemporaryFileProvider getTemporaryFileProvider() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected NativeDependencyCache getNativeDependencyCache() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Provider<String> getBaseName() {
        return baseName;
    }

    @Override
    public boolean isDebuggable() {
        return debuggable;
    }

    @Override
    public boolean isOptimized() {
        return optimized;
    }

    @Override
    public FileCollection getCppSource() {
        return sourceFiles;
    }

    @Override
    public FileCollection getCompileIncludePath() {
        return includePath;
    }

    @Override
    public FileCollection getLinkLibraries() {
        return linkLibraries;
    }

    @Override
    public FileCollection getRuntimeLibraries() {
        return runtimeLibraries;
    }

    public Configuration getImplementationDependencies() {
        return implementation;
    }

    public DirectoryProperty getObjectsDir() {
        return objectsDir;
    }

    public Configuration getIncludePathConfiguration() {
        return includePathConfiguration;
    }

    @Override
    public FileCollection getObjects() {
        return objectsDir.getAsFileTree().matching(new PatternSet().include("**/*.obj", "**/*.o"));
    }

    @Override
    public CppPlatform getTargetPlatform() {
        return targetPlatform;
    }

    @Override
    public NativeToolChainInternal getToolChain() {
        return toolChain;
    }

    @Override
    public Property<CppCompile> getCompileTask() {
        return compileTaskProperty;
    }

    public PlatformToolProvider getPlatformToolProvider() {
        return platformToolProvider;
    }

    private class IncludePath implements MinimalFileSet {
        private final Configuration includePathConfig;
        private Set<File> result;

        IncludePath(Configuration includePathConfig) {
            this.includePathConfig = includePathConfig;
        }

        @Override
        public String getDisplayName() {
            return "Include path for " + DefaultCppBinary.this.toString();
        }

        @Override
        public Set<File> getFiles() {
            if (result == null) {
                // All this is intended to go away as more Gradle-specific metadata is included in the publications and the dependency resolution engine can just figure this stuff out for us
                // This is intentionally dumb and will improve later

                // Collect the files from anything other than an external component and use these directly in the result
                // For external components, unzip the headers into a cache, if not already present.
                ArtifactCollection artifacts = includePathConfig.getIncoming().getArtifacts();
                Set<File> files = new LinkedHashSet<File>();
                if (!artifacts.getArtifacts().isEmpty()) {
                    NativeDependencyCache cache = getNativeDependencyCache();
                    for (ResolvedArtifactResult artifact : artifacts) {
                        if (artifact.getId().getComponentIdentifier() instanceof ModuleComponentIdentifier) {
                            // Unzip the headers into cache
                            ModuleComponentIdentifier id = (ModuleComponentIdentifier) artifact.getId().getComponentIdentifier();
                            File headerDir = cache.getUnpackedHeaders(artifact.getFile(), id.getModule() + "-" + id.getVersion());
                            files.add(headerDir);
                        } else {
                            files.add(artifact.getFile());
                        }
                    }
                }
                result = files;
            }
            return result;
        }
    }
}
