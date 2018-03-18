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
package org.gradle.api.internal.artifacts.ivyservice.modulecache;

import com.google.common.base.Objects;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.ivyservice.ArtifactCacheMetaData;
import org.gradle.api.internal.artifacts.ivyservice.CacheLockingManager;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ModuleComponentRepository;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.ComponentIdentifierSerializer;
import org.gradle.api.internal.artifacts.repositories.metadata.IvyMutableModuleMetadataFactory;
import org.gradle.api.internal.artifacts.repositories.metadata.MavenMutableModuleMetadataFactory;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.model.NamedObjectInstantiator;
import org.gradle.cache.PersistentIndexedCache;
import org.gradle.internal.Factory;
import org.gradle.internal.component.external.model.ModuleComponentResolveMetadata;
import org.gradle.internal.component.external.model.MutableModuleComponentResolveMetadata;
import org.gradle.internal.resource.local.DefaultPathKeyFileStore;
import org.gradle.internal.serialize.AbstractSerializer;
import org.gradle.internal.serialize.Decoder;
import org.gradle.internal.serialize.Encoder;
import org.gradle.util.BuildCommencedTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultModuleMetaDataCache implements ModuleMetaDataCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultModuleMetaDataCache.class);

    private final BuildCommencedTimeProvider timeProvider;
    private final CacheLockingManager cacheLockingManager;

    private final ModuleMetadataStore moduleMetadataStore;

    private PersistentIndexedCache<ModuleComponentAtRepositoryKey, ModuleMetadataCacheEntry> cache;

    public DefaultModuleMetaDataCache(BuildCommencedTimeProvider timeProvider,
                                      CacheLockingManager cacheLockingManager,
                                      ArtifactCacheMetaData artifactCacheMetaData,
                                      ImmutableModuleIdentifierFactory moduleIdentifierFactory,
                                      ImmutableAttributesFactory attributesFactory,
                                      NamedObjectInstantiator instantiator,
                                      MavenMutableModuleMetadataFactory mavenMetadataFactory,
                                      IvyMutableModuleMetadataFactory ivyMetadataFactory) {
        this.timeProvider = timeProvider;
        this.cacheLockingManager = cacheLockingManager;
        moduleMetadataStore = new ModuleMetadataStore(new DefaultPathKeyFileStore(artifactCacheMetaData.getMetaDataStoreDirectory()), new ModuleMetadataSerializer(attributesFactory, instantiator, mavenMetadataFactory, ivyMetadataFactory), moduleIdentifierFactory);
    }

    private PersistentIndexedCache<ModuleComponentAtRepositoryKey, ModuleMetadataCacheEntry> getCache() {
        if (cache == null) {
            cache = initCache();
        }
        return cache;
    }

    private PersistentIndexedCache<ModuleComponentAtRepositoryKey, ModuleMetadataCacheEntry> initCache() {
        return cacheLockingManager.createCache("module-metadata", new RevisionKeySerializer(), new ModuleMetadataCacheEntrySerializer());
    }

    public CachedMetaData getCachedModuleDescriptor(ModuleComponentRepository repository, ModuleComponentIdentifier componentId) {
        final ModuleComponentAtRepositoryKey key = createKey(repository, componentId);
        final PersistentIndexedCache<ModuleComponentAtRepositoryKey, ModuleMetadataCacheEntry> cache = getCache();
        return cacheLockingManager.useCache(new Factory<CachedMetaData>() {
            @Override
            public CachedMetaData create() {
                ModuleMetadataCacheEntry entry = cache.get(key);
                if (entry == null) {
                    return null;
                }
                if (entry.isMissing()) {
                    return new DefaultCachedMetaData(entry, null, timeProvider);
                }
                MutableModuleComponentResolveMetadata metadata = moduleMetadataStore.getModuleDescriptor(key);
                if (metadata == null) {
                    // Descriptor file has been deleted - ignore the entry
                    cache.remove(key);
                    return null;
                }
                return new DefaultCachedMetaData(entry, entry.configure(metadata), timeProvider);
            }
        });
    }

    public CachedMetaData cacheMissing(ModuleComponentRepository repository, ModuleComponentIdentifier id) {
        LOGGER.debug("Recording absence of module descriptor in cache: {} [changing = {}]", id, false);
        ModuleMetadataCacheEntry entry = ModuleMetadataCacheEntry.forMissingModule(timeProvider.getCurrentTime());
        getCache().put(createKey(repository, id), entry);
        return new DefaultCachedMetaData(entry, null, timeProvider);
    }

    public CachedMetaData cacheMetaData(ModuleComponentRepository repository, final ModuleComponentResolveMetadata metadata) {
        LOGGER.debug("Recording module descriptor in cache: {} [changing = {}]", metadata.getComponentId(), metadata.isChanging());
        final ModuleComponentAtRepositoryKey key = createKey(repository, metadata.getComponentId());
        return cacheLockingManager.useCache(new Factory<CachedMetaData>() {
            @Override
            public CachedMetaData create() {
                moduleMetadataStore.putModuleDescriptor(key, metadata);
                ModuleMetadataCacheEntry entry = createEntry(metadata);
                getCache().put(key, entry);
                return new DefaultCachedMetaData(entry, metadata, timeProvider);
            }
        });
    }

    private ModuleComponentAtRepositoryKey createKey(ModuleComponentRepository repository, ModuleComponentIdentifier id) {
        return new ModuleComponentAtRepositoryKey(repository.getId(), id);
    }

    private ModuleMetadataCacheEntry createEntry(ModuleComponentResolveMetadata metaData) {
        return ModuleMetadataCacheEntry.forMetaData(metaData, timeProvider.getCurrentTime());
    }

    private static class RevisionKeySerializer extends AbstractSerializer<ModuleComponentAtRepositoryKey> {
        private final ComponentIdentifierSerializer componentIdSerializer = new ComponentIdentifierSerializer();

        public void write(Encoder encoder, ModuleComponentAtRepositoryKey value) throws Exception {
            encoder.writeString(value.getRepositoryId());
            componentIdSerializer.write(encoder, value.getComponentId());
        }

        public ModuleComponentAtRepositoryKey read(Decoder decoder) throws Exception {
            String resolverId = decoder.readString();
            ModuleComponentIdentifier identifier = (ModuleComponentIdentifier) componentIdSerializer.read(decoder);
            return new ModuleComponentAtRepositoryKey(resolverId, identifier);
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }

            RevisionKeySerializer rhs = (RevisionKeySerializer) obj;
            return Objects.equal(componentIdSerializer, rhs.componentIdSerializer);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), componentIdSerializer);
        }
    }
}
