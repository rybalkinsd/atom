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

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.artifacts.cache.ArtifactResolutionControl;
import org.gradle.api.artifacts.cache.DependencyResolutionControl;
import org.gradle.api.artifacts.cache.ModuleResolutionControl;
import org.gradle.api.artifacts.cache.ResolutionRules;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy.ExternalResourceCachePolicy;
import org.gradle.api.internal.artifacts.repositories.resolver.MetadataFetchingCost;
import org.gradle.api.internal.component.ArtifactType;
import org.gradle.api.resources.ResourceException;
import org.gradle.internal.component.external.model.ModuleDependencyMetadata;
import org.gradle.internal.component.model.ComponentArtifactMetadata;
import org.gradle.internal.component.model.ComponentOverrideMetadata;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.internal.component.model.ModuleSource;
import org.gradle.internal.resolve.ArtifactResolveException;
import org.gradle.internal.resolve.ModuleVersionResolveException;
import org.gradle.internal.resolve.result.BuildableArtifactResolveResult;
import org.gradle.internal.resolve.result.BuildableArtifactSetResolveResult;
import org.gradle.internal.resolve.result.BuildableComponentArtifactsResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleComponentMetaDataResolveResult;
import org.gradle.internal.resolve.result.BuildableModuleVersionListingResolveResult;
import org.gradle.internal.resource.ReadableContent;
import org.gradle.internal.resource.metadata.ExternalResourceMetaData;
import org.gradle.internal.resource.transfer.ExternalResourceConnector;
import org.gradle.internal.resource.transfer.ExternalResourceReadResponse;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StartParameterResolutionOverride {
    private final StartParameter startParameter;

    public StartParameterResolutionOverride(StartParameter startParameter) {
        this.startParameter = startParameter;
    }

    public void addResolutionRules(ResolutionRules resolutionRules) {
        if (startParameter.isOffline()) {
            resolutionRules.eachDependency(new Action<DependencyResolutionControl>() {
                public void execute(DependencyResolutionControl dependencyResolutionControl) {
                    dependencyResolutionControl.useCachedResult();
                }
            });
            resolutionRules.eachModule(new Action<ModuleResolutionControl>() {
                public void execute(ModuleResolutionControl moduleResolutionControl) {
                    moduleResolutionControl.useCachedResult();
                }
            });
            resolutionRules.eachArtifact(new Action<ArtifactResolutionControl>() {
                public void execute(ArtifactResolutionControl artifactResolutionControl) {
                    artifactResolutionControl.useCachedResult();
                }
            });
        } else if (startParameter.isRefreshDependencies()) {
            resolutionRules.eachDependency(new Action<DependencyResolutionControl>() {
                public void execute(DependencyResolutionControl dependencyResolutionControl) {
                    dependencyResolutionControl.cacheFor(0, TimeUnit.SECONDS);
                }
            });
            resolutionRules.eachModule(new Action<ModuleResolutionControl>() {
                public void execute(ModuleResolutionControl moduleResolutionControl) {
                    moduleResolutionControl.cacheFor(0, TimeUnit.SECONDS);
                }
            });
            resolutionRules.eachArtifact(new Action<ArtifactResolutionControl>() {
                public void execute(ArtifactResolutionControl artifactResolutionControl) {
                    artifactResolutionControl.cacheFor(0, TimeUnit.SECONDS);
                }
            });
        }
    }

    public ModuleComponentRepository overrideModuleVersionRepository(ModuleComponentRepository original) {
        if (startParameter.isOffline()) {
            return new OfflineModuleComponentRepository(original);
        }
        return original;
    }

    private static class OfflineModuleComponentRepository extends BaseModuleComponentRepository {

        private final FailedRemoteAccess failedRemoteAccess = new FailedRemoteAccess();

        public OfflineModuleComponentRepository(ModuleComponentRepository original) {
            super(original);
        }

        @Override
        public ModuleComponentRepositoryAccess getRemoteAccess() {
            return failedRemoteAccess;
        }
    }

    private static class FailedRemoteAccess implements ModuleComponentRepositoryAccess {
        @Override
        public String toString() {
            return "offline remote";
        }

        @Override
        public void listModuleVersions(ModuleDependencyMetadata dependency, BuildableModuleVersionListingResolveResult result) {
            result.failed(new ModuleVersionResolveException(dependency.getSelector(), String.format("No cached version listing for %s available for offline mode.", dependency.getSelector())));
        }

        @Override
        public void resolveComponentMetaData(ModuleComponentIdentifier moduleComponentIdentifier, ComponentOverrideMetadata requestMetaData, BuildableModuleComponentMetaDataResolveResult result) {
            result.failed(new ModuleVersionResolveException(moduleComponentIdentifier, String.format("No cached version of %s available for offline mode.", moduleComponentIdentifier.getDisplayName())));
        }

        @Override
        public void resolveArtifactsWithType(ComponentResolveMetadata component, ArtifactType artifactType, BuildableArtifactSetResolveResult result) {
            result.failed(new ArtifactResolveException(component.getComponentId(), "No cached version available for offline mode"));
        }

        @Override
        public void resolveArtifacts(ComponentResolveMetadata component, BuildableComponentArtifactsResolveResult result) {
            result.failed(new ArtifactResolveException(component.getComponentId(), "No cached version available for offline mode"));
        }

        @Override
        public void resolveArtifact(ComponentArtifactMetadata artifact, ModuleSource moduleSource, BuildableArtifactResolveResult result) {
            result.failed(new ArtifactResolveException(artifact.getId(), "No cached version available for offline mode"));
        }

        @Override
        public MetadataFetchingCost estimateMetadataFetchingCost(ModuleComponentIdentifier moduleComponentIdentifier) {
            return MetadataFetchingCost.CHEAP;
        }
    }

    public ExternalResourceCachePolicy overrideExternalResourceCachePolicy(ExternalResourceCachePolicy original) {
        if (startParameter.isOffline()) {
            return new ExternalResourceCachePolicy() {
                @Override
                public boolean mustRefreshExternalResource(long ageMillis) {
                    return false;
                }
            };
        }
        return original;
    }

    public ExternalResourceConnector overrideExternalResourceConnnector(ExternalResourceConnector original) {
        if (startParameter.isOffline()) {
            return new OfflineExternalResourceConnector();
        }
        return original;
    }

    private static class OfflineExternalResourceConnector implements ExternalResourceConnector {
        @Nullable
        @Override
        public ExternalResourceReadResponse openResource(URI location, boolean revalidate) throws ResourceException {
            throw offlineResource(location);
        }

        @Nullable
        @Override
        public ExternalResourceMetaData getMetaData(URI location, boolean revalidate) throws ResourceException {
            throw offlineResource(location);
        }

        @Nullable
        @Override
        public List<String> list(URI parent) throws ResourceException {
            throw offlineResource(parent);
        }

        @Override
        public void upload(ReadableContent resource, URI destination) throws IOException {
            throw new ResourceException(destination, String.format("Cannot upload to '%s' in offline mode.", destination));
        }

        private ResourceException offlineResource(URI source) {
            return new ResourceException(source, String.format("No cached resource '%s' available for offline mode.", source));
        }
    }
}
