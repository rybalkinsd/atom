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

import org.apache.commons.lang.StringUtils;
import org.gradle.api.Action;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.attributes.Usage;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.internal.provider.LockableSetProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.language.cpp.CppBinary;
import org.gradle.language.cpp.CppLibrary;
import org.gradle.language.cpp.CppPlatform;
import org.gradle.language.nativeplatform.internal.PublicationAwareComponent;
import org.gradle.nativeplatform.Linkage;
import org.gradle.nativeplatform.toolchain.internal.NativeToolChainInternal;
import org.gradle.nativeplatform.toolchain.internal.PlatformToolProvider;

import javax.inject.Inject;

public class DefaultCppLibrary extends DefaultCppComponent implements CppLibrary, PublicationAwareComponent {
    private final ObjectFactory objectFactory;
    private final ConfigurableFileCollection publicHeaders;
    private final FileCollection publicHeadersWithConvention;
    private final LockableSetProperty<Linkage> linkage;
    private final Property<CppBinary> developmentBinary;
    private final Configuration api;
    private final Configuration apiElements;
    private final MainLibraryVariant mainVariant;

    @Inject
    public DefaultCppLibrary(String name, ObjectFactory objectFactory, FileOperations fileOperations, ConfigurationContainer configurations) {
        super(name, fileOperations, objectFactory, configurations);
        this.objectFactory = objectFactory;
        this.developmentBinary = objectFactory.property(CppBinary.class);
        publicHeaders = fileOperations.files();
        publicHeadersWithConvention = createDirView(publicHeaders, "src/" + name + "/public");

        linkage = new LockableSetProperty<Linkage>(objectFactory.setProperty(Linkage.class));
        linkage.add(Linkage.SHARED);

        api = configurations.create(getNames().withSuffix("api"));
        api.setCanBeConsumed(false);
        api.setCanBeResolved(false);
        getImplementationDependencies().extendsFrom(api);

        Usage apiUsage = objectFactory.named(Usage.class, Usage.C_PLUS_PLUS_API);

        apiElements = configurations.create(getNames().withSuffix("cppApiElements"));
        apiElements.extendsFrom(api);
        apiElements.setCanBeResolved(false);
        apiElements.getAttributes().attribute(Usage.USAGE_ATTRIBUTE, apiUsage);

        mainVariant = new MainLibraryVariant("api", apiUsage, apiElements);
    }

    public DefaultCppSharedLibrary addSharedLibrary(String nameSuffix, boolean debuggable, boolean optimized, CppPlatform targetPlatform, NativeToolChainInternal toolChain, PlatformToolProvider platformToolProvider) {
        DefaultCppSharedLibrary result = objectFactory.newInstance(DefaultCppSharedLibrary.class, getName() + StringUtils.capitalize(nameSuffix), getBaseName(), debuggable, optimized, getCppSource(), getAllHeaderDirs(), getImplementationDependencies(), targetPlatform, toolChain, platformToolProvider);
        getBinaries().add(result);
        return result;
    }

    public DefaultCppStaticLibrary addStaticLibrary(String nameSuffix, boolean debuggable, boolean optimized, CppPlatform targetPlatform, NativeToolChainInternal toolChain, PlatformToolProvider platformToolProvider) {
        DefaultCppStaticLibrary result = objectFactory.newInstance(DefaultCppStaticLibrary.class, getName() + StringUtils.capitalize(nameSuffix), getBaseName(), debuggable, optimized, getCppSource(), getAllHeaderDirs(), getImplementationDependencies(), targetPlatform, toolChain, platformToolProvider);
        getBinaries().add(result);
        return result;
    }

    @Override
    public Configuration getApiDependencies() {
        return api;
    }

    public Configuration getApiElements() {
        return apiElements;
    }

    @Override
    public MainLibraryVariant getMainPublication() {
        return mainVariant;
    }

    @Override
    public ConfigurableFileCollection getPublicHeaders() {
        return publicHeaders;
    }

    @Override
    public void publicHeaders(Action<? super ConfigurableFileCollection> action) {
        action.execute(publicHeaders);
    }

    @Override
    public FileCollection getPublicHeaderDirs() {
        return publicHeadersWithConvention;
    }

    @Override
    public FileTree getPublicHeaderFiles() {
        PatternSet patterns = new PatternSet();
        patterns.include("**/*.h");
        patterns.include("**/*.hpp");
        return publicHeadersWithConvention.getAsFileTree().matching(patterns);
    }

    @Override
    public FileCollection getAllHeaderDirs() {
        return publicHeadersWithConvention.plus(super.getAllHeaderDirs());
    }

    @Override
    public Property<CppBinary> getDevelopmentBinary() {
        return developmentBinary;
    }

    @Override
    public LockableSetProperty<Linkage> getLinkage() {
        return linkage;
    }
}
