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

import org.gradle.api.artifacts.cache.ResolutionRules;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.internal.artifacts.ComponentMetadataProcessor;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.configurations.ResolutionStrategyInternal;
import org.gradle.api.internal.artifacts.configurations.dynamicversion.CachePolicy;
import org.gradle.api.internal.artifacts.ivyservice.dynamicversions.ModuleVersionsCache;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.memcache.InMemoryCachedRepositoryFactory;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionComparator;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionSelectorScheme;
import org.gradle.api.internal.artifacts.ivyservice.modulecache.ModuleArtifactsCache;
import org.gradle.api.internal.artifacts.ivyservice.modulecache.ModuleMetaDataCache;
import org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy.DefaultComponentSelectionRules;
import org.gradle.api.internal.artifacts.repositories.ResolutionAwareRepository;
import org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver;
import org.gradle.api.internal.component.ArtifactType;
import org.gradle.internal.component.model.ComponentArtifactMetadata;
import org.gradle.internal.component.model.ComponentOverrideMetadata;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.internal.component.model.DependencyMetadata;
import org.gradle.internal.component.model.ModuleSource;
import org.gradle.internal.resolve.resolver.ArtifactResolver;
import org.gradle.internal.resolve.resolver.ComponentMetaDataResolver;
import org.gradle.internal.resolve.resolver.DependencyToComponentIdResolver;
import org.gradle.internal.resolve.resolver.OriginArtifactSelector;
import org.gradle.internal.resolve.result.BuildableArtifactResolveResult;
import org.gradle.internal.resolve.result.BuildableArtifactSetResolveResult;
import org.gradle.internal.resolve.result.BuildableComponentIdResolveResult;
import org.gradle.internal.resolve.result.BuildableComponentResolveResult;
import org.gradle.internal.resource.cached.CachedArtifactIndex;
import org.gradle.util.BuildCommencedTimeProvider;

import java.util.Collection;

public class ResolveIvyFactory {
    private final ModuleVersionsCache moduleVersionsCache;
    private final ModuleMetaDataCache moduleMetaDataCache;
    private final ModuleArtifactsCache moduleArtifactsCache;
    private final CachedArtifactIndex artifactAtRepositoryCachedResolutionIndex;
    private final StartParameterResolutionOverride startParameterResolutionOverride;
    private final BuildCommencedTimeProvider timeProvider;
    private final InMemoryCachedRepositoryFactory inMemoryCache;
    private final VersionSelectorScheme versionSelectorScheme;
    private final VersionComparator versionComparator;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;
    private final RepositoryBlacklister repositoryBlacklister;

    public ResolveIvyFactory(ModuleVersionsCache moduleVersionsCache, ModuleMetaDataCache moduleMetaDataCache, ModuleArtifactsCache moduleArtifactsCache,
                             CachedArtifactIndex artifactAtRepositoryCachedResolutionIndex,
                             StartParameterResolutionOverride startParameterResolutionOverride,
                             BuildCommencedTimeProvider timeProvider, InMemoryCachedRepositoryFactory inMemoryCache, VersionSelectorScheme versionSelectorScheme,
                             VersionComparator versionComparator, ImmutableModuleIdentifierFactory moduleIdentifierFactory, RepositoryBlacklister repositoryBlacklister) {
        this.moduleVersionsCache = moduleVersionsCache;
        this.moduleMetaDataCache = moduleMetaDataCache;
        this.moduleArtifactsCache = moduleArtifactsCache;
        this.artifactAtRepositoryCachedResolutionIndex = artifactAtRepositoryCachedResolutionIndex;
        this.startParameterResolutionOverride = startParameterResolutionOverride;
        this.timeProvider = timeProvider;
        this.inMemoryCache = inMemoryCache;
        this.versionSelectorScheme = versionSelectorScheme;
        this.versionComparator = versionComparator;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
        this.repositoryBlacklister = repositoryBlacklister;
    }

    public ComponentResolvers create(ResolutionStrategyInternal resolutionStrategy,
                                     Collection<? extends ResolutionAwareRepository> repositories,
                                     ComponentMetadataProcessor metadataProcessor) {
        if (repositories.isEmpty()) {
            return new NoRepositoriesResolver();
        }

        ResolutionRules resolutionRules = resolutionStrategy.getResolutionRules();
        CachePolicy cachePolicy = resolutionStrategy.getCachePolicy();

        startParameterResolutionOverride.addResolutionRules(resolutionRules);

        UserResolverChain moduleResolver = new UserResolverChain(versionSelectorScheme, versionComparator, resolutionStrategy.getComponentSelection(), moduleIdentifierFactory);
        ParentModuleLookupResolver parentModuleResolver = new ParentModuleLookupResolver(versionSelectorScheme, versionComparator, moduleIdentifierFactory);

        for (ResolutionAwareRepository repository : repositories) {
            ConfiguredModuleComponentRepository baseRepository = repository.createResolver();

            if (baseRepository instanceof ExternalResourceResolver) {
                ((ExternalResourceResolver) baseRepository).setComponentResolvers(parentModuleResolver);
            }

            ModuleComponentRepository moduleComponentRepository = baseRepository;
            if (baseRepository.isLocal()) {
                moduleComponentRepository = new LocalModuleComponentRepository(baseRepository, metadataProcessor);
            } else {
                moduleComponentRepository = startParameterResolutionOverride.overrideModuleVersionRepository(moduleComponentRepository);
                moduleComponentRepository = new CachingModuleComponentRepository(moduleComponentRepository, moduleVersionsCache, moduleMetaDataCache, moduleArtifactsCache, artifactAtRepositoryCachedResolutionIndex,
                    cachePolicy, timeProvider, metadataProcessor, moduleIdentifierFactory);
            }

            if (baseRepository.isDynamicResolveMode()) {
                moduleComponentRepository = IvyDynamicResolveModuleComponentRepositoryAccess.wrap(moduleComponentRepository);
            }
            moduleComponentRepository = inMemoryCache.cached(moduleComponentRepository);
            moduleComponentRepository = new ErrorHandlingModuleComponentRepository(moduleComponentRepository, repositoryBlacklister);

            moduleResolver.add(moduleComponentRepository);
            parentModuleResolver.add(moduleComponentRepository);
        }

        return moduleResolver;
    }

    /**
     * Provides access to the top-level resolver chain for looking up parent modules when parsing module descriptor files.
     */
    private static class ParentModuleLookupResolver implements ComponentResolvers, DependencyToComponentIdResolver, ComponentMetaDataResolver, ArtifactResolver {
        private final UserResolverChain delegate;

        public ParentModuleLookupResolver(VersionSelectorScheme versionSelectorScheme, VersionComparator versionComparator, ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
            this.delegate = new UserResolverChain(versionSelectorScheme, versionComparator, new DefaultComponentSelectionRules(moduleIdentifierFactory), moduleIdentifierFactory);
        }

        public void add(ModuleComponentRepository moduleComponentRepository) {
            delegate.add(moduleComponentRepository);
        }

        public DependencyToComponentIdResolver getComponentIdResolver() {
            return this;
        }

        public ComponentMetaDataResolver getComponentResolver() {
            return this;
        }

        public ArtifactResolver getArtifactResolver() {
            return this;
        }

        @Override
        public OriginArtifactSelector getArtifactSelector() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void resolve(final DependencyMetadata dependency, final BuildableComponentIdResolveResult result) {
            delegate.getComponentIdResolver().resolve(dependency, result);
        }

        @Override
        public void resolve(final ComponentIdentifier identifier, final ComponentOverrideMetadata componentOverrideMetadata, final BuildableComponentResolveResult result) {
            delegate.getComponentResolver().resolve(identifier, componentOverrideMetadata, result);
        }

        @Override
        public boolean isFetchingMetadataCheap(ComponentIdentifier identifier) {
            return delegate.getComponentResolver().isFetchingMetadataCheap(identifier);
        }

        @Override
        public void resolveArtifactsWithType(final ComponentResolveMetadata component, final ArtifactType artifactType, final BuildableArtifactSetResolveResult result) {
            delegate.getArtifactResolver().resolveArtifactsWithType(component, artifactType, result);
        }

        @Override
        public void resolveArtifact(final ComponentArtifactMetadata artifact, final ModuleSource moduleSource, final BuildableArtifactResolveResult result) {
            delegate.getArtifactResolver().resolveArtifact(artifact, moduleSource, result);
        }
    }
}
