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

package org.gradle.internal.component.external.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentSelector;
import org.gradle.internal.component.external.descriptor.Artifact;
import org.gradle.internal.component.model.ComponentResolveMetadata;
import org.gradle.internal.component.model.ConfigurationMetadata;
import org.gradle.internal.component.model.ConfigurationNotFoundException;
import org.gradle.internal.component.model.Exclude;
import org.gradle.internal.component.model.ExcludeMetadata;
import org.gradle.internal.component.model.IvyArtifactName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents a dependency as represented in an Ivy module descriptor file.
 */
public class IvyDependencyDescriptor extends ExternalDependencyDescriptor {
    private final ModuleComponentSelector selector;
    private final String dynamicConstraintVersion;
    private final boolean changing;
    private final boolean transitive;
    private final boolean optional;
    private final SetMultimap<String, String> confs;
    private final List<Exclude> excludes;
    private final List<Artifact> dependencyArtifacts;

    public IvyDependencyDescriptor(ModuleComponentSelector selector, String dynamicConstraintVersion, boolean changing, boolean transitive, boolean optional, Multimap<String, String> confMappings, List<Artifact> artifacts, List<Exclude> excludes) {
        this.selector = selector;
        this.dynamicConstraintVersion = dynamicConstraintVersion;
        this.changing = changing;
        this.transitive = transitive;
        this.optional = optional;
        this.confs = ImmutableSetMultimap.copyOf(confMappings);
        dependencyArtifacts = ImmutableList.copyOf(artifacts);
        this.excludes = ImmutableList.copyOf(excludes);
    }

    public IvyDependencyDescriptor(ModuleComponentSelector requested, ListMultimap<String, String> confMappings) {
        this(requested, requested.getVersionConstraint().getPreferredVersion(), false, true, false, confMappings, Collections.<Artifact>emptyList(), Collections.<Exclude>emptyList());
    }

    @Override
    public String toString() {
        return "dependency: " + getSelector() + ", confs: " + confs;
    }

    @Override
    public ModuleComponentSelector getSelector() {
        return selector;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public boolean isChanging() {
        return changing;
    }

    @Override
    public boolean isTransitive() {
        return transitive;
    }

    public String getDynamicConstraintVersion() {
        return dynamicConstraintVersion;
    }

    public SetMultimap<String, String> getConfMappings() {
        return confs;
    }

    @Override
    protected IvyDependencyDescriptor withRequested(ModuleComponentSelector newRequested) {
        return new IvyDependencyDescriptor(newRequested, dynamicConstraintVersion, changing, transitive, isOptional(), confs, getDependencyArtifacts(), excludes);
    }

    public List<ConfigurationMetadata> selectLegacyConfigurations(ComponentIdentifier fromComponent, ConfigurationMetadata fromConfiguration, ComponentResolveMetadata targetComponent) {
        // TODO - all this matching stuff is constant for a given DependencyMetadata instance
        // Use a set builder to ensure uniqueness
        ImmutableSet.Builder<ConfigurationMetadata> targets = ImmutableSet.builder();
        boolean matched = false;
        String fromConfigName = fromConfiguration.getName();
        for (String config : fromConfiguration.getHierarchy()) {
            if (confs.containsKey(config)) {
                Set<String> targetPatterns = confs.get(config);
                if (!targetPatterns.isEmpty()) {
                    matched = true;
                }
                for (String targetPattern : targetPatterns) {
                    findMatches(fromComponent, targetComponent, fromConfigName, config, targetPattern, targets);
                }
            }
        }
        if (!matched && confs.containsKey("%")) {
            for (String targetPattern : confs.get("%")) {
                findMatches(fromComponent, targetComponent, fromConfigName, fromConfigName, targetPattern, targets);
            }
        }

        // TODO - this is not quite right, eg given *,!A->A;*,!B->B the result should be B->A and A->B but will in fact be B-> and A->
        Set<String> wildcardPatterns = confs.get("*");
        if (!wildcardPatterns.isEmpty()) {
            boolean excludeWildcards = false;
            for (String confName : fromConfiguration.getHierarchy()) {
                if (confs.containsKey("!" + confName)) {
                    excludeWildcards = true;
                    break;
                }
            }
            if (!excludeWildcards) {
                for (String targetPattern : wildcardPatterns) {
                    findMatches(fromComponent, targetComponent, fromConfigName, fromConfigName, targetPattern, targets);
                }
            }
        }

        return targets.build().asList();
    }

    private void findMatches(ComponentIdentifier fromComponent, ComponentResolveMetadata targetComponent, String fromConfiguration, String patternConfiguration, String targetPattern, ImmutableSet.Builder<ConfigurationMetadata> targetConfigurations) {
        int startFallback = targetPattern.indexOf('(');
        if (startFallback >= 0) {
            if (targetPattern.endsWith(")")) {
                String preferred = targetPattern.substring(0, startFallback);
                ConfigurationMetadata configuration = targetComponent.getConfiguration(preferred);
                if (configuration != null) {
                    targetConfigurations.add(configuration);
                    return;
                }
                targetPattern = targetPattern.substring(startFallback + 1, targetPattern.length() - 1);
            }
        }

        if (targetPattern.equals("*")) {
            for (String targetName : targetComponent.getConfigurationNames()) {
                ConfigurationMetadata configuration = targetComponent.getConfiguration(targetName);
                if (configuration.isVisible()) {
                    targetConfigurations.add(configuration);
                }
            }
            return;
        }

        if (targetPattern.equals("@")) {
            targetPattern = patternConfiguration;
        } else if (targetPattern.equals("#")) {
            targetPattern = fromConfiguration;
        }

        ConfigurationMetadata configuration = targetComponent.getConfiguration(targetPattern);
        if (configuration == null) {
            throw new ConfigurationNotFoundException(fromComponent, fromConfiguration, targetPattern, targetComponent.getComponentId());
        }
        targetConfigurations.add(configuration);
    }

    public List<Exclude> getAllExcludes() {
        return excludes;
    }

    @Override
    public List<ExcludeMetadata> getConfigurationExcludes(Collection<String> configurations) {
        List<ExcludeMetadata> rules = Lists.newArrayList();
        for (Exclude exclude : excludes) {
            Set<String> ruleConfigurations = exclude.getConfigurations();
            if (include(ruleConfigurations, configurations)) {
                rules.add(exclude);
            }
        }
        return rules;
    }

    public List<Artifact> getDependencyArtifacts() {
        return dependencyArtifacts;
    }

    public ImmutableList<IvyArtifactName> getConfigurationArtifacts(ConfigurationMetadata fromConfiguration) {
        if (dependencyArtifacts.isEmpty()) {
            return ImmutableList.of();
        }

        Collection<String> includedConfigurations = fromConfiguration.getHierarchy();
        ImmutableList.Builder<IvyArtifactName> artifacts = ImmutableList.builder();
        for (Artifact depArtifact : dependencyArtifacts) {
            Set<String> artifactConfigurations = depArtifact.getConfigurations();
            if (include(artifactConfigurations, includedConfigurations)) {
                IvyArtifactName ivyArtifactName = depArtifact.getArtifactName();
                artifacts.add(ivyArtifactName);
            }
        }
        return artifacts.build();
    }

    protected static boolean include(Iterable<String> configurations, Collection<String> acceptedConfigurations) {
        for (String configuration : configurations) {
            if (configuration.equals("*")) {
                return true;
            }
            if (acceptedConfigurations.contains(configuration)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IvyDependencyDescriptor that = (IvyDependencyDescriptor) o;
        return changing == that.changing
            && transitive == that.transitive
            && optional == that.optional
            && Objects.equal(selector, that.selector)
            && Objects.equal(dynamicConstraintVersion, that.dynamicConstraintVersion)
            && Objects.equal(confs, that.confs)
            && Objects.equal(excludes, that.excludes)
            && Objects.equal(dependencyArtifacts, that.dependencyArtifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(selector,
            dynamicConstraintVersion,
            changing,
            transitive,
            optional,
            confs,
            excludes,
            dependencyArtifacts);
    }
}
