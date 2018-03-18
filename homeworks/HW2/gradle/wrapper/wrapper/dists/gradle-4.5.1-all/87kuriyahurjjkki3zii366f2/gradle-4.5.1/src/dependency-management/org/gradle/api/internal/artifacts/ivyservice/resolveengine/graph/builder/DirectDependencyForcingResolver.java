/*
 * Copyright 2017 the original author or authors.
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
package org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.builder;

import org.gradle.api.internal.artifacts.ivyservice.resolveengine.ComponentResolutionState;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.ConflictResolverDetails;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.ModuleConflictResolver;
import org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.VersionSelectionReasons;
import org.gradle.internal.Cast;
import org.gradle.internal.component.model.DependencyMetadata;
import org.gradle.internal.component.model.LocalOriginDependencyMetadata;

import java.util.Collection;

class DirectDependencyForcingResolver implements ModuleConflictResolver {
    private final ComponentState root;

    DirectDependencyForcingResolver(ComponentState root) {
        this.root = root;
    }

    @Override
    public <T extends ComponentResolutionState> void select(ConflictResolverDetails<T> details) {
        Collection<? extends T> candidates = details.getCandidates();
        for (NodeState configuration : root.getNodes()) {
            for (EdgeState outgoingEdge : configuration.getOutgoingEdges()) {
                ComponentState targetComponent = outgoingEdge.getTargetComponent();
                DependencyMetadata dependencyMetadata = outgoingEdge.getDependencyMetadata();
                assert dependencyMetadata instanceof LocalOriginDependencyMetadata; // Will always be true because we are looking at direct dependencies only
                if (((LocalOriginDependencyMetadata) dependencyMetadata).isForce() && candidates.contains(targetComponent)) {
                    targetComponent.setSelectionReason(VersionSelectionReasons.FORCED);
                    details.select(Cast.<T>uncheckedCast(targetComponent));
                    return;
                }
            }
        }
    }
}
