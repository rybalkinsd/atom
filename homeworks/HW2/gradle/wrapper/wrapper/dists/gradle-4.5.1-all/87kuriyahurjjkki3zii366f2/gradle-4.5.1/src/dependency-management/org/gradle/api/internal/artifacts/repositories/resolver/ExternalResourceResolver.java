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

package org.gradle.api.internal.artifacts.repositories.resolver;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.Transformer;
import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.artifacts.component.ComponentArtifactIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.ModuleVersionPublisher;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ComponentResolvers;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ConfiguredModuleComponentRepository;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ModuleComponentRepositoryAccess;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ResolvableArtifact;
import org.gradle.api.internal.artifacts.repositories.metadata.ImmutableMetadataSources;
import org.gradle.api.internal.artifacts.repositories.metadata.MetadataArtifactProvider;
import org.gradle.api.internal.artifacts.repositories.metadata.MetadataSource;
import org.gradle.api.internal.component.ArtifactType;
import org.gradle.api.specs.Spec;
import org.gradle.caching.internal.BuildCacheHasher;
import org.gradle.caching.internal.DefaultBuildCacheHasher;
import org.gradle.internal.UncheckedException;
import org.gradle.internal.component.external.ivypublish.IvyModuleArtifactPublishMetadata;
import org.gradle.internal.component.external.ivypublish.IvyModulePublishMetadata;
import org.gradle.internal.component.external.model.DefaultModuleComponentArtifactMetadata;
import org.gradle.internal.component.external.model.ModuleComponentArtifactIdentifier;
import org.gradle.internal.component.external.model.ModuleComponentArtifactMetadata;
import org.gradle.internal.component.external.model.ModuleComponentResolveMetadata;
import org.gradle.internal.component.external.model.ModuleDependencyMetadata;
import org.gradle.internal.component.external.model.MutableModuleComponentResolveMetadata;
import org.gradle.internal.component.model.ComponentArtifactMetadata;
import org.gradle.internal.component.model.ComponentOverrideMetadata;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.internal.component.model.DefaultModuleDescriptorArtifactMetadata;
import org.gradle.internal.component.model.IvyArtifactName;
import org.gradle.internal.component.model.ModuleDescriptorArtifactMetadata;
import org.gradle.internal.component.model.ModuleSource;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.hash.HashValue;
import org.gradle.internal.resolve.ArtifactResolveException;
import org.gradle.internal.resolve.result.BuildableArtifactResolveResult;
import org.gradle.internal.resolve.result.BuildableArtifactSetResolveResult;
import org.gradle.internal.resolve.result.BuildableComponentArtifactsResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleComponentMetaDataResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleVersionListingResolveResult;
import org.gradle.internal.resolve.result.BuildableTypedResolveResult;
import org.gradle.internal.resolve.result.DefaultResourceAwareResolveResult;
import org.gradle.internal.resolve.result.ResourceAwareResolveResult;
import org.gradle.internal.resource.ExternalResourceName;
import org.gradle.internal.resource.ExternalResourceRepository;
import org.gradle.internal.resource.local.ByteArrayReadableContent;
import org.gradle.internal.resource.local.FileReadableContent;
import org.gradle.internal.resource.local.FileStore;
import org.gradle.internal.resource.local.LocallyAvailableExternalResource;
import org.gradle.internal.resource.local.LocallyAvailableResourceFinder;
import org.gradle.internal.resource.transfer.CacheAwareExternalResourceAccessor;
import org.gradle.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ExternalResourceResolver<T extends ModuleComponentResolveMetadata> implements ModuleVersionPublisher, ConfiguredModuleComponentRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalResourceResolver.class);

    private final String name;
    private final List<ResourcePattern> ivyPatterns = new ArrayList<ResourcePattern>();
    private final List<ResourcePattern> artifactPatterns = new ArrayList<ResourcePattern>();
    private ComponentResolvers componentResolvers;

    private final ExternalResourceRepository repository;
    private final boolean local;
    private final CacheAwareExternalResourceAccessor cachingResourceAccessor;
    private final LocallyAvailableResourceFinder<ModuleComponentArtifactMetadata> locallyAvailableResourceFinder;
    private final FileStore<ModuleComponentArtifactIdentifier> artifactFileStore;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;

    private final ImmutableMetadataSources metadataSources;
    private final MetadataArtifactProvider metadataArtifactProvider;

    private String id;
    private ExternalResourceArtifactResolver cachedArtifactResolver;

    protected ExternalResourceResolver(String name,
                                       boolean local,
                                       ExternalResourceRepository repository,
                                       CacheAwareExternalResourceAccessor cachingResourceAccessor,
                                       LocallyAvailableResourceFinder<ModuleComponentArtifactMetadata> locallyAvailableResourceFinder,
                                       FileStore<ModuleComponentArtifactIdentifier> artifactFileStore,
                                       ImmutableModuleIdentifierFactory moduleIdentifierFactory,
                                       ImmutableMetadataSources metadataSources,
                                       MetadataArtifactProvider metadataArtifactProvider) {
        this.name = name;
        this.local = local;
        this.cachingResourceAccessor = cachingResourceAccessor;
        this.repository = repository;
        this.locallyAvailableResourceFinder = locallyAvailableResourceFinder;
        this.artifactFileStore = artifactFileStore;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
        this.metadataSources = metadataSources;
        this.metadataArtifactProvider = metadataArtifactProvider;
    }

    public String getId() {
        if (id != null) {
            return id;
        }
        id = generateId(this);
        return id;
    }

    public ImmutableMetadataSources getMetadataSources() {
        return metadataSources;
    }

    public String getName() {
        return name;
    }

    protected abstract Class<T> getSupportedMetadataType();

    public boolean isDynamicResolveMode() {
        return false;
    }

    public void setComponentResolvers(ComponentResolvers resolver) {
        this.componentResolvers = resolver;
    }

    protected ExternalResourceRepository getRepository() {
        return repository;
    }

    public boolean isLocal() {
        return local;
    }

    @Override
    public Map<ComponentArtifactIdentifier, ResolvableArtifact> getArtifactCache() {
        throw new UnsupportedOperationException();
    }

    private void doListModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
        ModuleIdentifier module = moduleIdentifierFactory.module(dependency.getSelector().getGroup(), dependency.getSelector().getModule());

        // TODO:DAZ Provide an abstraction for accessing resources within the same module (maven-metadata, directory listing, etc)
        // That way we can avoid passing `ivyPatterns` and `artifactPatterns` around everywhere
        ResourceVersionLister versionLister = new ResourceVersionLister(repository);
        List<ResourcePattern> completeIvyPatterns = filterComplete(this.ivyPatterns, module);
        List<ResourcePattern> completeArtifactPatterns = filterComplete(this.artifactPatterns, module);

        // Iterate over the metadata sources to see if they can provide the version list
        for (MetadataSource<?> metadataSource : metadataSources.sources()) {
            metadataSource.listModuleVersions(dependency, module, completeIvyPatterns, completeArtifactPatterns, versionLister, result);
            if (result.hasResult() && result.isAuthoritative()) {
                return;
            }
        }

        result.listed(ImmutableSet.<String>of());
    }

    private List<ResourcePattern> filterComplete(List<ResourcePattern> ivyPatterns, final ModuleIdentifier module) {
        return CollectionUtils.filter(ivyPatterns, new Spec<ResourcePattern>() {
            @Override
            public boolean isSatisfiedBy(ResourcePattern element) {
                return element.isComplete(module);
            }
        });
    }

    protected void doResolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata prescribedMetaData, BuildableModuleComponentMetaDataResolveResult result) {
        resolveStaticDependency(moduleComponentIdentifier, prescribedMetaData, result, createArtifactResolver());
    }

    protected final void resolveStaticDependency(ModuleComponentIdentifier moduleVersionIdentifier, ComponentOverrideMetadata prescribedMetaData, BuildableModuleComponentMetaDataResolveResult result, ExternalResourceArtifactResolver artifactResolver) {
        for (MetadataSource<?> source : metadataSources.sources()) {
            MutableModuleComponentResolveMetadata value = source.create(name, componentResolvers, moduleVersionIdentifier, prescribedMetaData, artifactResolver, result);
            if (value != null) {
                value.setSource(artifactResolver.getSource());
                result.resolved(value.asImmutable());
                return;
            }
        }

        LOGGER.debug("No meta-data file or artifact found for module '{}' in repository '{}'.", moduleVersionIdentifier, getName());
        result.missing();
    }

    protected abstract boolean isMetaDataArtifact(ArtifactType artifactType);

    protected Set<ModuleComponentArtifactMetadata> findOptionalArtifacts(ModuleComponentResolveMetadata module, String type, String classifier) {
        ModuleComponentArtifactMetadata artifact = module.artifact(type, "jar", classifier);
        if (createArtifactResolver(module.getSource()).artifactExists(artifact, new DefaultResourceAwareResolveResult())) {
            return ImmutableSet.of(artifact);
        }
        return Collections.emptySet();
    }

    private ModuleDescriptorArtifactMetadata getMetaDataArtifactFor(ModuleComponentIdentifier moduleComponentIdentifier) {
        IvyArtifactName ivyArtifactName = metadataArtifactProvider.getMetaDataArtifactName(moduleComponentIdentifier.getModule());
        return new DefaultModuleDescriptorArtifactMetadata(moduleComponentIdentifier, ivyArtifactName);
    }

    protected ExternalResourceArtifactResolver createArtifactResolver() {
        if (cachedArtifactResolver != null) {
            return cachedArtifactResolver;
        }
        ExternalResourceArtifactResolver artifactResolver = createArtifactResolver(ivyPatterns, artifactPatterns);
        cachedArtifactResolver = artifactResolver;
        return artifactResolver;
    }

    private ExternalResourceArtifactResolver createArtifactResolver(List<ResourcePattern> ivyPatterns, List<ResourcePattern> artifactPatterns) {
        return new DefaultExternalResourceArtifactResolver(repository, locallyAvailableResourceFinder, ivyPatterns, artifactPatterns, artifactFileStore, cachingResourceAccessor);
    }

    protected ExternalResourceArtifactResolver createArtifactResolver(ModuleSource moduleSource) {
        return createArtifactResolver();
    }

    public void publish(IvyModulePublishMetadata moduleVersion) {
        for (IvyModuleArtifactPublishMetadata artifact : moduleVersion.getArtifacts()) {
            publish(new DefaultModuleComponentArtifactMetadata(artifact.getId()), artifact.getFile());
        }
    }

    private void publish(ModuleComponentArtifactMetadata artifact, File src) {
        ResourcePattern destinationPattern;
        if ("ivy".equals(artifact.getName().getType()) && !ivyPatterns.isEmpty()) {
            destinationPattern = ivyPatterns.get(0);
        } else if (!artifactPatterns.isEmpty()) {
            destinationPattern = artifactPatterns.get(0);
        } else {
            throw new IllegalStateException("impossible to publish " + artifact + " using " + this + ": no artifact pattern defined");
        }
        ExternalResourceName destination = destinationPattern.getLocation(artifact);

        put(src, destination);
        LOGGER.info("Published {} to {}", artifact, destination);
    }

    private void put(File src, ExternalResourceName destination) {
        repository.withProgressLogging().resource(destination).put(new FileReadableContent(src));
        putChecksum(src, destination);
    }

    private void putChecksum(File source, ExternalResourceName destination) {
        byte[] checksumFile = createChecksumFile(source, "SHA1", 40);
        ExternalResourceName checksumDestination = destination.append(".sha1");
        repository.resource(checksumDestination).put(new ByteArrayReadableContent(checksumFile));
    }

    private byte[] createChecksumFile(File src, String algorithm, int checksumLength) {
        HashValue hash = HashUtil.createHash(src, algorithm);
        String formattedHashString = hash.asZeroPaddedHexString(checksumLength);
        try {
            return formattedHashString.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw UncheckedException.throwAsUncheckedException(e);
        }
    }

    protected void addIvyPattern(ResourcePattern pattern) {
        invalidateCaches();
        ivyPatterns.add(pattern);
    }

    private void invalidateCaches() {
        id = null;
        cachedArtifactResolver = null;
    }

    protected void addArtifactPattern(ResourcePattern pattern) {
        invalidateCaches();
        artifactPatterns.add(pattern);
    }

    public List<String> getIvyPatterns() {
        return CollectionUtils.collect(ivyPatterns, new Transformer<String, ResourcePattern>() {
            public String transform(ResourcePattern original) {
                return original.getPattern();
            }
        });
    }

    public List<String> getArtifactPatterns() {
        return CollectionUtils.collect(artifactPatterns, new Transformer<String, ResourcePattern>() {
            public String transform(ResourcePattern original) {
                return original.getPattern();
            }
        });
    }

    protected void setIvyPatterns(Iterable<? extends ResourcePattern> patterns) {
        invalidateCaches();
        ivyPatterns.clear();
        CollectionUtils.addAll(ivyPatterns, patterns);
    }

    protected void setArtifactPatterns(List<ResourcePattern> patterns) {
        invalidateCaches();
        artifactPatterns.clear();
        CollectionUtils.addAll(artifactPatterns, patterns);
    }

    protected abstract class AbstractRepositoryAccess implements ModuleComponentRepositoryAccess {
        @Override
        public void resolveArtifactsWithType(ComponentResolveMetadata component, ArtifactType artifactType, BuildableArtifactSetResolveResult result) {
            T moduleMetaData = getSupportedMetadataType().cast(component);
            if (artifactType == ArtifactType.JAVADOC) {
                resolveJavadocArtifacts(moduleMetaData, result);
            } else if (artifactType == ArtifactType.SOURCES) {
                resolveSourceArtifacts(moduleMetaData, result);
            } else if (isMetaDataArtifact(artifactType)) {
                resolveMetaDataArtifacts(moduleMetaData, result);
            }
        }

        @Override
        public void resolveArtifacts(ComponentResolveMetadata component, BuildableComponentArtifactsResolveResult result) {
            T moduleMetaData = getSupportedMetadataType().cast(component);
            resolveModuleArtifacts(moduleMetaData, result);
        }

        protected abstract void resolveModuleArtifacts(T module, BuildableComponentArtifactsResolveResult result);

        protected abstract void resolveMetaDataArtifacts(T module, BuildableArtifactSetResolveResult result);

        protected abstract void resolveJavadocArtifacts(T module, BuildableArtifactSetResolveResult result);

        protected abstract void resolveSourceArtifacts(T module, BuildableArtifactSetResolveResult result);
    }

    protected abstract class LocalRepositoryAccess extends AbstractRepositoryAccess {
        @Override
        public String toString() {
            return "local > " + ExternalResourceResolver.this.toString();
        }

        @Override
        public final void listModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
        }

        @Override
        public final void resolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
        }

        @Override
        protected final void resolveMetaDataArtifacts(T module, BuildableArtifactSetResolveResult result) {
            ModuleDescriptorArtifactMetadata artifact = getMetaDataArtifactFor(module.getComponentId());
            result.resolved(Collections.singleton(artifact));
        }

        @Override
        public void resolveArtifact(ComponentArtifactMetadata artifact, ModuleSource moduleSource, BuildableArtifactResolveResult result) {

        }

        @Override
        public MetadataFetchingCost estimateMetadataFetchingCost(ModuleComponentIdentifier moduleComponentIdentifier) {
            return MetadataFetchingCost.CHEAP;
        }
    }

    protected abstract class RemoteRepositoryAccess extends AbstractRepositoryAccess {
        @Override
        public String toString() {
            return "remote > " + ExternalResourceResolver.this.toString();
        }

        @Override
        public final void listModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
            doListModuleVersions(dependency, result);
        }

        @Override
        public final void resolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
            doResolveComponentMetaData(moduleComponentIdentifier, requestMetaData, result);
        }

        @Override
        public void resolveArtifactsWithType(ComponentResolveMetadata component, ArtifactType artifactType, BuildableArtifactSetResolveResult result) {
            super.resolveArtifactsWithType(component, artifactType, result);
            checkArtifactsResolved(component, artifactType, result);
        }

        @Override
        public void resolveArtifacts(ComponentResolveMetadata component, BuildableComponentArtifactsResolveResult result) {
            super.resolveArtifacts(component, result);
            checkArtifactsResolved(component, "artifacts", result);
        }

        private void checkArtifactsResolved(ComponentResolveMetadata component, Object context, BuildableTypedResolveResult<?, ? super ArtifactResolveException> result) {
            if (!result.hasResult()) {
                result.failed(new ArtifactResolveException(component.getComponentId(),
                    String.format("Cannot locate %s for '%s' in repository '%s'", context, component, name)));
            }
        }

        @Override
        protected final void resolveMetaDataArtifacts(T module, BuildableArtifactSetResolveResult result) {
            // Meta data artifacts are determined locally
        }

        @Override
        public void resolveArtifact(ComponentArtifactMetadata artifact, ModuleSource moduleSource, BuildableArtifactResolveResult result) {
            try {
                ExternalResourceArtifactResolver resolver = createArtifactResolver(moduleSource);
                ModuleComponentArtifactMetadata moduleArtifact = (ModuleComponentArtifactMetadata) artifact;
                LocallyAvailableExternalResource artifactResource = resolver.resolveArtifact(moduleArtifact, result);
                if (artifactResource == null) {
                    result.notFound(artifact.getId());
                } else {
                    result.resolved(artifactResource.getFile());
                }
            } catch (Throwable e) {
                result.failed(new ArtifactResolveException(artifact.getId(), e));
            }
        }

        @Override
        public MetadataFetchingCost estimateMetadataFetchingCost(ModuleComponentIdentifier moduleComponentIdentifier) {
            if (ExternalResourceResolver.this.local) {
                ModuleComponentArtifactMetadata artifact = getMetaDataArtifactFor(moduleComponentIdentifier);
                if (createArtifactResolver().artifactExists(artifact, NoOpResourceAwareResolveResult.INSTANCE)) {
                    return MetadataFetchingCost.FAST;
                }
                return MetadataFetchingCost.CHEAP;
            }
            return MetadataFetchingCost.EXPENSIVE;
        }
    }

    private String generateId(ExternalResourceResolver resolver) {
        DefaultBuildCacheHasher cacheHasher = new DefaultBuildCacheHasher();
        cacheHasher.putString(getClass().getName());
        cacheHasher.putInt(resolver.ivyPatterns.size());
        for (ResourcePattern ivyPattern : ivyPatterns) {
            cacheHasher.putString(ivyPattern.getPattern());
        }
        cacheHasher.putInt(artifactPatterns.size());
        for (ResourcePattern artifactPattern : artifactPatterns) {
            cacheHasher.putString(artifactPattern.getPattern());
        }
        appendId(cacheHasher);
        return cacheHasher.hash().toString();
    }

    protected void appendId(BuildCacheHasher hasher) {
        getMetadataSources().appendId(hasher);
    }

    private static class NoOpResourceAwareResolveResult implements ResourceAwareResolveResult {

        private static final NoOpResourceAwareResolveResult INSTANCE = new NoOpResourceAwareResolveResult();

        @Override
        public List<String> getAttempted() {
            return Collections.emptyList();
        }

        @Override
        public void attempted(String locationDescription) {

        }

        @Override
        public void attempted(ExternalResourceName location) {

        }

        @Override
        public void applyTo(ResourceAwareResolveResult target) {
            throw new UnsupportedOperationException();
        }
    }
}
