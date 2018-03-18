/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.api.internal.plugins;

import org.gradle.api.Plugin;
import org.gradle.configuration.ConfigurationTargetIdentifier;
import org.gradle.model.RuleSource;
import org.gradle.model.internal.inspect.ExtractedRuleSource;
import org.gradle.model.internal.inspect.ModelRuleExtractor;
import org.gradle.model.internal.inspect.ModelRuleSourceDetector;
import org.gradle.model.internal.registry.ModelRegistry;
import org.gradle.model.internal.registry.ModelRegistryScope;

import javax.annotation.Nullable;

public class RuleBasedPluginTarget<T extends ModelRegistryScope & PluginAwareInternal> implements PluginTarget {

    private final T target;
    private final PluginTarget imperativeTarget;
    private final ModelRuleExtractor ruleInspector;
    private final ModelRuleSourceDetector ruleDetector;

    public RuleBasedPluginTarget(T target, ModelRuleExtractor ruleInspector, ModelRuleSourceDetector ruleDetector) {
        this.target = target;
        this.ruleInspector = ruleInspector;
        this.ruleDetector = ruleDetector;
        this.imperativeTarget = new ImperativeOnlyPluginTarget<T>(target);
    }

    @Override
    public ConfigurationTargetIdentifier getConfigurationTargetIdentifier() {
        return imperativeTarget.getConfigurationTargetIdentifier();
    }

    public void applyImperative(@Nullable String pluginId, Plugin<?> plugin) {
        imperativeTarget.applyImperative(pluginId, plugin);
    }

    public void applyRules(@Nullable String pluginId, Class<?> clazz) {
        ModelRegistry modelRegistry = target.getModelRegistry();
        Iterable<Class<? extends RuleSource>> declaredSources = ruleDetector.getDeclaredSources(clazz);
        for (Class<? extends RuleSource> ruleSource : declaredSources) {
            ExtractedRuleSource<?> rules = ruleInspector.extract(ruleSource);
            for (Class<?> dependency : rules.getRequiredPlugins()) {
                target.getPluginManager().apply(dependency);
            }
            modelRegistry.getRoot().applyToSelf(rules);
        }
    }

    public void applyImperativeRulesHybrid(@Nullable String pluginId, Plugin<?> plugin) {
        applyImperative(pluginId, plugin);
        applyRules(pluginId, plugin.getClass());
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
