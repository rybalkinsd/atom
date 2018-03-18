/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.jvm.internal.resolve;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.component.LibraryBinaryIdentifier;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.ResolveContext;
import org.gradle.api.internal.artifacts.configurations.OutgoingVariant;
import org.gradle.api.internal.artifacts.configurations.ResolutionStrategyInternal;
import org.gradle.api.internal.artifacts.ivyservice.dependencysubstitution.DependencySubstitutionRules;
import org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy.DefaultResolutionStrategy;
import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.vcs.internal.VcsMappingsStore;
import org.gradle.internal.Describables;
import org.gradle.internal.DisplayName;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.language.base.internal.model.DefaultLibraryLocalComponentMetadata;
import org.gradle.platform.base.DependencySpec;

import java.util.Set;

import static org.gradle.language.base.internal.model.DefaultLibraryLocalComponentMetadata.newResolvingLocalComponentMetadata;

public class JvmLibraryResolveContext implements ResolveContext {
    private final LibraryBinaryIdentifier libraryBinaryIdentifier;
    private final String displayName;
    private final UsageKind usage;
    private final ResolutionStrategyInternal resolutionStrategy;
    private final VariantsMetaData variants;
    private final Iterable<DependencySpec> dependencies;

    public JvmLibraryResolveContext(
        LibraryBinaryIdentifier libraryBinaryIdentifier,
        VariantsMetaData variants,
        Iterable<DependencySpec> dependencies,
        UsageKind usage,
        String displayName,
        ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.libraryBinaryIdentifier = libraryBinaryIdentifier;
        this.usage = usage;
        this.displayName = displayName;
        this.variants = variants;
        this.dependencies = dependencies;
        this.resolutionStrategy = new DefaultResolutionStrategy(DependencySubstitutionRules.NO_OP, VcsMappingsStore.NO_OP, null, moduleIdentifierFactory, null);
    }

    @Override
    public String getName() {
        return usage.getConfigurationName();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public VariantsMetaData getVariants() {
        return variants;
    }

    @Override
    public ResolutionStrategyInternal getResolutionStrategy() {
        return resolutionStrategy;
    }

    @Override
    public ComponentResolveMetadata toRootComponentMetaData() {
        final DefaultLibraryLocalComponentMetadata componentMetadata = newResolvingLocalComponentMetadata(libraryBinaryIdentifier, usage.getConfigurationName(), dependencies);
        for (UsageKind usageKind : UsageKind.values()) {
            componentMetadata.addVariant(usageKind.getConfigurationName(), new OutgoingVariant() {
                @Override
                public DisplayName asDescribable() {
                    return Describables.of(componentMetadata.getComponentId());
                }

                @Override
                public AttributeContainerInternal getAttributes() {
                    return ImmutableAttributes.EMPTY;
                }

                @Override
                public Set<? extends PublishArtifact> getArtifacts() {
                    return ImmutableSet.of();
                }

                @Override
                public Set<? extends OutgoingVariant> getChildren() {
                    return ImmutableSet.of();
                }
            });
        }
        return componentMetadata;
    }

}
