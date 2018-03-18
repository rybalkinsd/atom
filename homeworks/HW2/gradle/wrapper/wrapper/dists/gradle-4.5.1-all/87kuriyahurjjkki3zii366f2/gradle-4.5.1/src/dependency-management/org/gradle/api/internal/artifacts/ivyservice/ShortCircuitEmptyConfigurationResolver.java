/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.api.internal.artifacts.ivyservice;

import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.LenientConfiguration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolveException;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.artifacts.UnresolvedDependency;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.result.ResolutionResult;
import org.gradle.api.internal.artifacts.ConfigurationResolver;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.api.internal.artifacts.Module;
import org.gradle.api.internal.artifacts.ResolverResults;
import org.gradle.api.internal.artifacts.component.ComponentIdentifierFactory;
import org.gradle.api.internal.artifacts.configurations.ConfigurationInternal;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.ArtifactVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.BuildDependenciesVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.SelectedArtifactSet;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.artifact.VisitedArtifactSet;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.projectresult.ResolvedLocalComponentsResult;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.projectresult.ResolvedLocalComponentsResultGraphVisitor;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.DefaultResolutionResultBuilder;
import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.api.specs.Spec;

import java.io.File;
import java.util.Collections;
import java.util.Set;

public class ShortCircuitEmptyConfigurationResolver implements ConfigurationResolver {
    private final ConfigurationResolver delegate;
    private final ComponentIdentifierFactory componentIdentifierFactory;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;

    public ShortCircuitEmptyConfigurationResolver(ConfigurationResolver delegate, ComponentIdentifierFactory componentIdentifierFactory, ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.delegate = delegate;
        this.componentIdentifierFactory = componentIdentifierFactory;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
    }

    @Override
    public void resolveBuildDependencies(ConfigurationInternal configuration, ResolverResults result) {
        if (configuration.getAllDependencies().isEmpty()) {
            emptyGraph(configuration, result);
        } else {
            delegate.resolveBuildDependencies(configuration, result);
        }
    }

    @Override
    public void resolveGraph(ConfigurationInternal configuration, ResolverResults results) throws ResolveException {
        if (configuration.getAllDependencies().isEmpty()) {
            emptyGraph(configuration, results);
        } else {
            delegate.resolveGraph(configuration, results);
        }
    }

    private void emptyGraph(ConfigurationInternal configuration, ResolverResults results) {
        Module module = configuration.getModule();
        ModuleVersionIdentifier id = moduleIdentifierFactory.moduleWithVersion(module);
        ComponentIdentifier componentIdentifier = componentIdentifierFactory.createComponentIdentifier(module);
        ResolutionResult emptyResult = DefaultResolutionResultBuilder.empty(id, componentIdentifier);
        ResolvedLocalComponentsResult emptyProjectResult = new ResolvedLocalComponentsResultGraphVisitor();
        results.graphResolved(emptyResult, emptyProjectResult, new EmptyResults());
    }

    @Override
    public void resolveArtifacts(ConfigurationInternal configuration, ResolverResults results) throws ResolveException {
        if (configuration.getAllDependencies().isEmpty()) {
            results.artifactsResolved(new EmptyResolvedConfiguration(), new EmptyResults());
        } else {
            delegate.resolveArtifacts(configuration, results);
        }
    }

    private static class EmptyResults implements VisitedArtifactSet, SelectedArtifactSet {
        @Override
        public SelectedArtifactSet select(Spec<? super Dependency> dependencySpec, AttributeContainerInternal requestedAttributes, Spec<? super ComponentIdentifier> componentSpec, boolean allowNoMatchingVariant) {
            return this;
        }

        @Override
        public void collectBuildDependencies(BuildDependenciesVisitor visitor) {
        }

        @Override
        public void visitArtifacts(ArtifactVisitor visitor, boolean continueOnSelectionFailure) {
        }
    }

    private static class EmptyResolvedConfiguration implements ResolvedConfiguration {

        @Override
        public boolean hasError() {
            return false;
        }

        @Override
        public LenientConfiguration getLenientConfiguration() {
            return new LenientConfiguration() {
                @Override
                public Set<ResolvedDependency> getFirstLevelModuleDependencies() {
                    return Collections.emptySet();
                }

                @Override
                public Set<ResolvedDependency> getFirstLevelModuleDependencies(Spec<? super Dependency> dependencySpec) {
                    return Collections.emptySet();
                }

                @Override
                public Set<ResolvedDependency> getAllModuleDependencies() {
                    return Collections.emptySet();
                }

                @Override
                public Set<UnresolvedDependency> getUnresolvedModuleDependencies() {
                    return Collections.emptySet();
                }

                @Override
                public Set<File> getFiles() {
                    return Collections.emptySet();
                }

                @Override
                public Set<File> getFiles(Spec<? super Dependency> dependencySpec) {
                    return Collections.emptySet();
                }

                @Override
                public Set<ResolvedArtifact> getArtifacts() {
                    return Collections.emptySet();
                }

                @Override
                public Set<ResolvedArtifact> getArtifacts(Spec<? super Dependency> dependencySpec) {
                    return Collections.emptySet();
                }
            };
        }

        @Override
        public void rethrowFailure() throws ResolveException {
        }

        @Override
        public Set<File> getFiles() throws ResolveException {
            return Collections.emptySet();
        }

        @Override
        public Set<File> getFiles(Spec<? super Dependency> dependencySpec) {
            return Collections.emptySet();
        }

        @Override
        public Set<ResolvedDependency> getFirstLevelModuleDependencies() {
            return Collections.emptySet();
        }

        @Override
        public Set<ResolvedDependency> getFirstLevelModuleDependencies(Spec<? super Dependency> dependencySpec) throws ResolveException {
            return Collections.emptySet();
        }

        @Override
        public Set<ResolvedArtifact> getResolvedArtifacts() {
            return Collections.emptySet();
        }
    }
}
