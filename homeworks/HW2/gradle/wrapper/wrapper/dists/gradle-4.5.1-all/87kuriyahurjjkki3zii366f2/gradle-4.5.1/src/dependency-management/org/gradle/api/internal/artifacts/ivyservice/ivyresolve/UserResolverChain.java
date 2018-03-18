/*
 * Copyright 2011 the original author or authors.
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

package org.gradle.api.internal.artifacts.ivyservice.ivyresolve;

import org.gradle.api.Transformer;
import org.gradle.api.internal.artifacts.ComponentSelectionRulesInternal;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionComparator;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionSelectorScheme;
import org.gradle.internal.component.external.model.ModuleComponentResolveMetadata;
import org.gradle.internal.resolve.resolver.ArtifactResolver;
import org.gradle.internal.resolve.resolver.ComponentMetaDataResolver;
import org.gradle.internal.resolve.resolver.DependencyToComponentIdResolver;
import org.gradle.internal.resolve.resolver.OriginArtifactSelector;

public class UserResolverChain implements ComponentResolvers {
    private final RepositoryChainDependencyToComponentIdResolver componentIdResolver;
    private final RepositoryChainComponentMetaDataResolver componentResolver;
    private final RepositoryChainArtifactResolver artifactResolver;
    private final ComponentSelectionRulesInternal componentSelectionRules;

    public UserResolverChain(VersionSelectorScheme versionSelectorScheme,
                             VersionComparator versionComparator,
                             ComponentSelectionRulesInternal componentSelectionRules,
                             ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.componentSelectionRules = componentSelectionRules;
        VersionedComponentChooser componentChooser = new DefaultVersionedComponentChooser(versionComparator, componentSelectionRules);
        ModuleTransformer metaDataFactory = new ModuleTransformer();
        componentIdResolver = new RepositoryChainDependencyToComponentIdResolver(componentChooser, metaDataFactory, moduleIdentifierFactory, versionSelectorScheme);
        componentResolver = new RepositoryChainComponentMetaDataResolver(componentChooser, metaDataFactory);
        artifactResolver = new RepositoryChainArtifactResolver();
    }

    public DependencyToComponentIdResolver getComponentIdResolver() {
        return componentIdResolver;
    }

    public ComponentMetaDataResolver getComponentResolver() {
        return componentResolver;
    }

    public ArtifactResolver getArtifactResolver() {
        return artifactResolver;
    }

    @Override
    public OriginArtifactSelector getArtifactSelector() {
        return artifactResolver;
    }

    public ComponentSelectionRulesInternal getComponentSelectionRules() {
        return componentSelectionRules;
    }

    public void add(ModuleComponentRepository repository) {
        componentIdResolver.add(repository);
        componentResolver.add(repository);
        artifactResolver.add(repository);
    }

    private static class ModuleTransformer implements Transformer<ModuleComponentResolveMetadata, RepositoryChainModuleResolution> {
        public ModuleComponentResolveMetadata transform(RepositoryChainModuleResolution original) {
            RepositoryChainModuleSource moduleSource = new RepositoryChainModuleSource(original.repository.getId(), original.module.getSource());
            return original.module.withSource(moduleSource);
        }
    }
}
