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

package org.gradle.plugins.ide.idea.model.internal;

import org.gradle.api.artifacts.component.ProjectComponentIdentifier;
import org.gradle.api.internal.artifacts.ivyservice.projectmodule.LocalComponentRegistry;
import org.gradle.internal.component.model.ComponentArtifactMetadata;
import org.gradle.plugins.ide.idea.model.ModuleDependency;

class ModuleDependencyBuilder {
    private final LocalComponentRegistry localComponentRegistry;

    public ModuleDependencyBuilder(LocalComponentRegistry localComponentRegistry) {
        this.localComponentRegistry = localComponentRegistry;
    }

    public ModuleDependency create(ProjectComponentIdentifier id, String scope) {
        return new ModuleDependency(determineProjectName(id), scope);
    }

    private String determineProjectName(ProjectComponentIdentifier id) {
        ComponentArtifactMetadata imlArtifact = localComponentRegistry.findAdditionalArtifact(id, "iml");
        return imlArtifact == null ? id.getProjectName() : imlArtifact.getName().getName();
    }
}
