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
import org.gradle.api.artifacts.ArtifactIdentifier;
import org.gradle.api.artifacts.ComponentMetadataSupplier;
import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.component.ComponentArtifactIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentSelector;
import org.gradle.api.internal.artifacts.ComponentMetadataProcessor;
import org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.configurations.dynamicversion.CachePolicy;
import org.gradle.api.internal.artifacts.ivyservice.dynamicversions.ModuleVersionsCache;
import org.gradle.api.internal.artifacts.ivyservice.modulecache.ModuleArtifactsCache;
import org.gradle.api.internal.artifacts.ivyservice.modulecache.ModuleMetaDataCache;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ResolvableArtifact;
import org.gradle.api.internal.artifacts.repositories.resolver.MetadataFetchingCost;
import org.gradle.api.internal.component.ArtifactType;
import org.gradle.internal.component.external.model.FixedComponentArtifacts;
import org.gradle.internal.component.external.model.ModuleComponentArtifactMetadata;
import org.gradle.internal.component.external.model.ModuleComponentResolveMetadata;
import org.gradle.internal.component.external.model.ModuleDependencyMetadata;
import org.gradle.internal.component.model.ComponentArtifactMetadata;
import org.gradle.internal.component.model.ComponentOverrideMetadata;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.internal.component.model.ModuleSource;
import org.gradle.internal.resolve.ArtifactNotFoundException;
import org.gradle.internal.resolve.ArtifactResolveException;
import org.gradle.internal.resolve.result.BuildableArtifactResolveResult;
import org.gradle.internal.resolve.result.BuildableArtifactSetResolveResult;
import org.gradle.internal.resolve.result.BuildableComponentArtifactsResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleComponentMetaDataResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleVersionListingResolveResult;
import org.gradle.internal.resolve.result.DefaultBuildableArtifactSetResolveResult;
import org.gradle.internal.resource.cached.CachedArtifact;
import org.gradle.internal.resource.cached.CachedArtifactIndex;
import org.gradle.internal.resource.cached.ivy.ArtifactAtRepositoryKey;
import org.gradle.util.BuildCommencedTimeProvider;
import org.gradle.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

public class CachingModuleComponentRepository implements ModuleComponentRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingModuleComponentRepository.class);

    private final ModuleVersionsCache moduleVersionsCache;
    private final ModuleMetaDataCache moduleMetaDataCache;
    private final ModuleArtifactsCache moduleArtifactsCache;
    private final CachedArtifactIndex artifactAtRepositoryCachedResolutionIndex;

    private final CachePolicy cachePolicy;

    private final ModuleComponentRepository delegate;
    private final BuildCommencedTimeProvider timeProvider;
    private final ComponentMetadataProcessor metadataProcessor;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;
    private LocateInCacheRepositoryAccess locateInCacheRepositoryAccess = new LocateInCacheRepositoryAccess();
    private ResolveAndCacheRepositoryAccess resolveAndCacheRepositoryAccess = new ResolveAndCacheRepositoryAccess();

    public CachingModuleComponentRepository(ModuleComponentRepository delegate, ModuleVersionsCache moduleVersionsCache, ModuleMetaDataCache moduleMetaDataCache,
                                            ModuleArtifactsCache moduleArtifactsCache, CachedArtifactIndex artifactAtRepositoryCachedResolutionIndex,
                                            CachePolicy cachePolicy, BuildCommencedTimeProvider timeProvider,
                                            ComponentMetadataProcessor metadataProcessor,
                                            ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.delegate = delegate;
        this.moduleMetaDataCache = moduleMetaDataCache;
        this.moduleVersionsCache = moduleVersionsCache;
        this.moduleArtifactsCache = moduleArtifactsCache;
        this.artifactAtRepositoryCachedResolutionIndex = artifactAtRepositoryCachedResolutionIndex;
        this.timeProvider = timeProvider;
        this.cachePolicy = cachePolicy;
        this.metadataProcessor = metadataProcessor;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
    }

    public String getId() {
        return delegate.getId();
    }

    public String getName() {
        return delegate.getName();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    public ModuleComponentRepositoryAccess getLocalAccess() {
        return locateInCacheRepositoryAccess;
    }

    public ModuleComponentRepositoryAccess getRemoteAccess() {
        return resolveAndCacheRepositoryAccess;
    }

    public ComponentMetadataSupplier createMetadataSupplier() {
        return delegate.createMetadataSupplier();
    }

    @Override
    public Map<ComponentArtifactIdentifier, ResolvableArtifact> getArtifactCache() {
        throw new UnsupportedOperationException();
    }

    private ModuleIdentifier getCacheKey(ModuleComponentSelector requested) {
        return moduleIdentifierFactory.module(requested.getGroup(), requested.getModule());
    }

    private class LocateInCacheRepositoryAccess implements ModuleComponentRepositoryAccess {
        @Override
        public String toString() {
            return "cache lookup for " + delegate.toString();
        }

        @Override
        public void listModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
            // First try to determine the artifacts in-memory (e.g using the metadata): don't use the cache in this case
            delegate.getLocalAccess().listModuleVersions(dependency, result);
            if (result.hasResult()) {
                return;
            }

            listModuleVersionsFromCache(dependency, result);
        }

        private void listModuleVersionsFromCache(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
            ModuleComponentSelector requested = dependency.getSelector();
            final ModuleIdentifier moduleId = getCacheKey(requested);
            ModuleVersionsCache.CachedModuleVersionList cachedModuleVersionList = moduleVersionsCache.getCachedModuleResolution(delegate, moduleId);
            if (cachedModuleVersionList != null) {
                Set<String> versionList = cachedModuleVersionList.getModuleVersions();
                Set<ModuleVersionIdentifier> versions = CollectionUtils.collect(versionList, new Transformer<ModuleVersionIdentifier, String>() {
                    public ModuleVersionIdentifier transform(String original) {
                        return new DefaultModuleVersionIdentifier(moduleId, original);
                    }
                });
                if (cachePolicy.mustRefreshVersionList(moduleId, versions, cachedModuleVersionList.getAgeMillis())) {
                    LOGGER.debug("Version listing in dynamic revision cache is expired: will perform fresh resolve of '{}' in '{}'", requested, delegate.getName());
                } else {
                    result.listed(versionList);
                    // When age == 0, verified since the start of this build, assume listing hasn't changed
                    result.setAuthoritative(cachedModuleVersionList.getAgeMillis() == 0);
                }
            }
        }

        @Override
        public void resolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
            // First try to determine the artifacts in-memory (e.g using the metadata): don't use the cache in this case
            delegate.getLocalAccess().resolveComponentMetaData(moduleComponentIdentifier, requestMetaData, result);
            if (result.hasResult()) {
                return;
            }

            resolveComponentMetaDataFromCache(moduleComponentIdentifier, requestMetaData, result);
        }

        private void resolveComponentMetaDataFromCache(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
            ModuleMetaDataCache.CachedMetaData cachedMetaData = moduleMetaDataCache.getCachedModuleDescriptor(delegate, moduleComponentIdentifier);
            if (cachedMetaData == null) {
                return;
            }
            if (cachedMetaData.isMissing()) {
                if (cachePolicy.mustRefreshMissingModule(moduleComponentIdentifier, cachedMetaData.getAgeMillis())) {
                    LOGGER.debug("Cached meta-data for missing module is expired: will perform fresh resolve of '{}' in '{}'", moduleComponentIdentifier, delegate.getName());
                    return;
                }
                LOGGER.debug("Detected non-existence of module '{}' in resolver cache '{}'", moduleComponentIdentifier, delegate.getName());
                result.missing();
                // When age == 0, verified since the start of this build, assume still missing
                result.setAuthoritative(cachedMetaData.getAgeMillis() == 0);
                return;
            }
            ModuleComponentResolveMetadata metaData = cachedMetaData.getMetaData();
            metaData = metadataProcessor.processMetadata(metaData);
            if (requestMetaData.isChanging() || metaData.isChanging()) {
                if (cachePolicy.mustRefreshChangingModule(moduleComponentIdentifier, cachedMetaData.getModuleVersion(), cachedMetaData.getAgeMillis())) {
                    LOGGER.debug("Cached meta-data for changing module is expired: will perform fresh resolve of '{}' in '{}'", moduleComponentIdentifier, delegate.getName());
                    return;
                }
                LOGGER.debug("Found cached version of changing module '{}' in '{}'", moduleComponentIdentifier, delegate.getName());
            } else {
                if (cachePolicy.mustRefreshModule(moduleComponentIdentifier, cachedMetaData.getModuleVersion(), cachedMetaData.getAgeMillis())) {
                    LOGGER.debug("Cached meta-data for module must be refreshed: will perform fresh resolve of '{}' in '{}'", moduleComponentIdentifier, delegate.getName());
                    return;
                }
            }

            LOGGER.debug("Using cached module metadata for module '{}' in '{}'", moduleComponentIdentifier, delegate.getName());
            metaData = metaData.withSource(new CachingModuleSource(metaData.getContentHash().asBigInteger(), metaData.isChanging(), metaData.getSource()));
            result.resolved(metaData);
            // When age == 0, verified since the start of this build, assume the meta-data hasn't changed
            result.setAuthoritative(cachedMetaData.getAgeMillis() == 0);
        }

        @Override
        public void resolveArtifactsWithType(ComponentResolveMetadata component, ArtifactType artifactType, BuildableArtifactSetResolveResult result) {
            final CachingModuleSource cachedModuleSource = (CachingModuleSource) component.getSource();

            // First try to determine the artifacts in-memory (e.g using the metadata): don't use the cache in this case
            delegate.getLocalAccess().resolveArtifactsWithType(component.withSource(cachedModuleSource.getDelegate()), artifactType, result);
            if (result.hasResult()) {
                return;
            }

            resolveModuleArtifactsFromCache(cacheKey(artifactType), component, result, cachedModuleSource);
        }

        @Override
        public void resolveArtifacts(ComponentResolveMetadata component, BuildableComponentArtifactsResolveResult result) {
            final CachingModuleSource cachedModuleSource = (CachingModuleSource) component.getSource();

            // First try to determine the artifacts in-memory (e.g using the metadata): don't use the cache in this case
            delegate.getLocalAccess().resolveArtifacts(component.withSource(cachedModuleSource.getDelegate()), result);
            if (result.hasResult()) {
                return;
            }

            DefaultBuildableArtifactSetResolveResult artifactsResolveResult = new DefaultBuildableArtifactSetResolveResult();
            resolveModuleArtifactsFromCache("component:", component, artifactsResolveResult, cachedModuleSource);
            if (artifactsResolveResult.hasResult()) {
                result.resolved(new FixedComponentArtifacts(artifactsResolveResult.getResult()));
            }
        }

        private void resolveModuleArtifactsFromCache(String contextId, ComponentResolveMetadata component, BuildableArtifactSetResolveResult result, CachingModuleSource cachedModuleSource) {
            ModuleArtifactsCache.CachedArtifacts cachedModuleArtifacts = moduleArtifactsCache.getCachedArtifacts(delegate, component.getComponentId(), contextId);
            BigInteger moduleDescriptorHash = cachedModuleSource.getDescriptorHash();

            if (cachedModuleArtifacts != null) {
                if (!cachePolicy.mustRefreshModuleArtifacts(component.getId(), null, cachedModuleArtifacts.getAgeMillis(),
                    cachedModuleSource.isChangingModule(), moduleDescriptorHash.equals(cachedModuleArtifacts.getDescriptorHash()))) {
                    result.resolved(cachedModuleArtifacts.getArtifacts());
                    return;
                }

                LOGGER.debug("Artifact listing has expired: will perform fresh resolve of '{}' for '{}' in '{}'", contextId, component.getId(), delegate.getName());
            }
        }

        @Override
        public void resolveArtifact(ComponentArtifactMetadata artifact, ModuleSource moduleSource, BuildableArtifactResolveResult result) {
            final CachingModuleSource cachedModuleSource = (CachingModuleSource) moduleSource;

            // First try to determine the artifacts in-memory (e.g using the metadata): don't use the cache in this case
            delegate.getLocalAccess().resolveArtifact(artifact, cachedModuleSource.getDelegate(), result);
            if (result.hasResult()) {
                return;
            }

            resolveArtifactFromCache(artifact, cachedModuleSource, result);
        }

        @Override
        public MetadataFetchingCost estimateMetadataFetchingCost(ModuleComponentIdentifier moduleComponentIdentifier) {
            if (isMetadataAvailableInCacheAndUpToDate(moduleComponentIdentifier)) {
                return MetadataFetchingCost.FAST;
            }
            return delegate.getRemoteAccess().estimateMetadataFetchingCost(moduleComponentIdentifier);
        }

        private boolean isMetadataAvailableInCacheAndUpToDate(ModuleComponentIdentifier moduleComponentIdentifier) {
            ModuleMetaDataCache.CachedMetaData cachedMetaData = moduleMetaDataCache.getCachedModuleDescriptor(delegate, moduleComponentIdentifier);
            if (cachedMetaData == null) {
                return false;
            }
            if (cachedMetaData.isMissing()) {
                if (cachePolicy.mustRefreshMissingModule(moduleComponentIdentifier, cachedMetaData.getAgeMillis())) {
                    return false;
                }
                return true;
            }
            ModuleComponentResolveMetadata metaData = cachedMetaData.getMetaData();
            metaData = metadataProcessor.processMetadata(metaData);
            if (metaData.isChanging()) {
                if (cachePolicy.mustRefreshChangingModule(moduleComponentIdentifier, cachedMetaData.getModuleVersion(), cachedMetaData.getAgeMillis())) {
                    return false;
                }
            } else {
                if (cachePolicy.mustRefreshModule(moduleComponentIdentifier, cachedMetaData.getModuleVersion(), cachedMetaData.getAgeMillis())) {
                    return false;
                }
            }
            return true;
        }


        private void resolveArtifactFromCache(ComponentArtifactMetadata artifact, CachingModuleSource moduleSource, BuildableArtifactResolveResult result) {
            CachedArtifact cached = artifactAtRepositoryCachedResolutionIndex.lookup(artifactCacheKey(artifact.getId()));
            final BigInteger descriptorHash = moduleSource.getDescriptorHash();
            if (cached != null) {
                long age = timeProvider.getCurrentTime() - cached.getCachedAt();
                final boolean isChangingModule = moduleSource.isChangingModule();
                ArtifactIdentifier artifactIdentifier = ((ModuleComponentArtifactMetadata) artifact).toArtifactIdentifier();
                if (cached.isMissing()) {
                    if (!cachePolicy.mustRefreshArtifact(artifactIdentifier, null, age, isChangingModule, descriptorHash.equals(cached.getDescriptorHash()))) {
                        LOGGER.debug("Detected non-existence of artifact '{}' in resolver cache", artifact);
                        for (String location : cached.attemptedLocations()) {
                            result.attempted(location);
                        }
                        result.notFound(artifact.getId());
                    }
                } else {
                    File cachedArtifactFile = cached.getCachedFile();
                    if (!cachePolicy.mustRefreshArtifact(artifactIdentifier, cachedArtifactFile, age, isChangingModule, descriptorHash.equals(cached.getDescriptorHash()))) {
                        LOGGER.debug("Found artifact '{}' in resolver cache: {}", artifact, cachedArtifactFile);
                        result.resolved(cachedArtifactFile);
                    }
                }
            }
        }
    }

    private class ResolveAndCacheRepositoryAccess implements ModuleComponentRepositoryAccess {
        @Override
        public String toString() {
            return "cache > " + delegate.getRemoteAccess().toString();
        }

        @Override
        public void listModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
            delegate.getRemoteAccess().listModuleVersions(dependency, result);
            switch (result.getState()) {
                case Listed:
                    ModuleIdentifier moduleId = getCacheKey(dependency.getSelector());
                    Set<String> versionList = result.getVersions();
                    moduleVersionsCache.cacheModuleVersionList(delegate, moduleId, versionList);
                    break;
                case Failed:
                    break;
                default:
                    throw new IllegalStateException("Unexpected state on listModuleVersions: " + result.getState());
            }
        }

        @Override
        public void resolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
            ComponentOverrideMetadata forced = requestMetaData.withChanging();

            delegate.getRemoteAccess().resolveComponentMetaData(moduleComponentIdentifier, forced, result);
            switch (result.getState()) {
                case Missing:
                    moduleMetaDataCache.cacheMissing(delegate, moduleComponentIdentifier);
                    break;
                case Resolved:
                    ModuleComponentResolveMetadata metaData = result.getMetaData();
                    ModuleSource moduleSource = metaData.getSource();
                    moduleMetaDataCache.cacheMetaData(delegate, metaData);
                    metaData = metadataProcessor.processMetadata(metaData);
                    moduleSource = new CachingModuleSource(metaData.getContentHash().asBigInteger(), requestMetaData.isChanging() || metaData.isChanging(), moduleSource);
                    metaData = metaData.withSource(moduleSource);
                    result.resolved(metaData);
                    break;
                case Failed:
                    break;
                default:
                    throw new IllegalStateException("Unexpected resolve state: " + result.getState());
            }
        }

        @Override
        public void resolveArtifactsWithType(ComponentResolveMetadata component, ArtifactType artifactType, BuildableArtifactSetResolveResult result) {
            final CachingModuleSource moduleSource = (CachingModuleSource) component.getSource();
            delegate.getRemoteAccess().resolveArtifactsWithType(component.withSource(moduleSource.getDelegate()), artifactType, result);

            if (result.getFailure() == null) {
                moduleArtifactsCache.cacheArtifacts(delegate, component.getComponentId(), cacheKey(artifactType), moduleSource.getDescriptorHash(), result.getResult());
            }
        }

        @Override
        public void resolveArtifacts(ComponentResolveMetadata component, BuildableComponentArtifactsResolveResult result) {
            final CachingModuleSource moduleSource = (CachingModuleSource) component.getSource();
            delegate.getRemoteAccess().resolveArtifacts(component.withSource(moduleSource.getDelegate()), result);

            if (result.getFailure() == null) {
                FixedComponentArtifacts artifacts = (FixedComponentArtifacts) result.getResult();
                moduleArtifactsCache.cacheArtifacts(delegate, component.getComponentId(), "component:", moduleSource.getDescriptorHash(), artifacts.getArtifacts());
            }
        }

        @Override
        public void resolveArtifact(ComponentArtifactMetadata artifact, ModuleSource moduleSource, BuildableArtifactResolveResult result) {
            final CachingModuleSource cachingModuleSource = (CachingModuleSource) moduleSource;

            delegate.getRemoteAccess().resolveArtifact(artifact, cachingModuleSource.getDelegate(), result);
            LOGGER.debug("Downloaded artifact '{}' from resolver: {}", artifact, delegate.getName());

            ArtifactResolveException failure = result.getFailure();
            if (failure == null) {
                artifactAtRepositoryCachedResolutionIndex.store(artifactCacheKey(artifact.getId()), result.getResult(), cachingModuleSource.getDescriptorHash());
            } else if (failure instanceof ArtifactNotFoundException) {
                artifactAtRepositoryCachedResolutionIndex.storeMissing(artifactCacheKey(artifact.getId()), result.getAttempted(), cachingModuleSource.getDescriptorHash());
            }
        }

        @Override
        public MetadataFetchingCost estimateMetadataFetchingCost(ModuleComponentIdentifier moduleComponentIdentifier) {
            return delegate.getLocalAccess().estimateMetadataFetchingCost(moduleComponentIdentifier);
        }
    }

    private String cacheKey(ArtifactType artifactType) {
        return "artifacts:" + artifactType.name();
    }

    private ArtifactAtRepositoryKey artifactCacheKey(ComponentArtifactIdentifier id) {
        return new ArtifactAtRepositoryKey(delegate.getId(), id);
    }

    static class CachingModuleSource implements ModuleSource {
        private final BigInteger descriptorHash;
        private final boolean changingModule;
        private final ModuleSource delegate;

        public CachingModuleSource(BigInteger descriptorHash, boolean changingModule, ModuleSource delegate) {
            this.delegate = delegate;
            this.descriptorHash = descriptorHash;
            this.changingModule = changingModule;
        }

        @Override
        public String toString() {
            return "{descriptor: " + descriptorHash + ", changing: " + changingModule + ", source: " + delegate + "}";
        }

        public BigInteger getDescriptorHash() {
            return descriptorHash;
        }

        public boolean isChangingModule() {
            return changingModule;
        }

        public ModuleSource getDelegate() {
            return delegate;
        }
    }
}
