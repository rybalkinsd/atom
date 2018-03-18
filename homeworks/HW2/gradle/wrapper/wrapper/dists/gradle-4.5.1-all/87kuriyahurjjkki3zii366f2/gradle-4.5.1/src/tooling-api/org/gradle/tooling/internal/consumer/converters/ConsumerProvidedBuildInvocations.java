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

package org.gradle.tooling.internal.consumer.converters;

import org.gradle.tooling.internal.gradle.ConsumerProvidedTask;
import org.gradle.tooling.internal.gradle.ConsumerProvidedTaskSelector;
import org.gradle.tooling.model.ProjectIdentifier;

import java.io.Serializable;
import java.util.List;

/**
 * A consumer-side implementation of {@link org.gradle.tooling.model.gradle.BuildInvocations}
 */
public class ConsumerProvidedBuildInvocations implements Serializable {
    private final ProjectIdentifier projectIdentifier;
    private final List<? extends ConsumerProvidedTaskSelector> selectors;
    private final List<? extends ConsumerProvidedTask> tasks;

    public ConsumerProvidedBuildInvocations(ProjectIdentifier projectIdentifier, List<? extends ConsumerProvidedTaskSelector> selectors, List<? extends ConsumerProvidedTask> tasks) {
        this.projectIdentifier = projectIdentifier;
        this.selectors = selectors;
        this.tasks = tasks;
    }

    public ProjectIdentifier getProjectIdentifier() {
        return projectIdentifier;
    }

    public List<? extends ConsumerProvidedTaskSelector> getTaskSelectors() {
        return selectors;
    }

    public List<? extends ConsumerProvidedTask> getTasks() {
        return tasks;
    }
}
