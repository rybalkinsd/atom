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

package org.gradle.initialization;

import org.gradle.api.initialization.ProjectDescriptor;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.SettingsInternal;
import org.gradle.api.internal.initialization.ClassLoaderScope;
import org.gradle.api.internal.project.IProjectFactory;
import org.gradle.api.internal.project.ProjectInternal;

public class InstantiatingBuildLoader implements BuildLoader {
    private final IProjectFactory projectFactory;

    public InstantiatingBuildLoader(IProjectFactory projectFactory) {
        this.projectFactory = projectFactory;
    }

    /**
     * Creates the {@link org.gradle.api.internal.GradleInternal} and {@link ProjectInternal} instances for the given root project, ready for the projects to be configured.
     */
    @Override
    public void load(SettingsInternal settings, GradleInternal gradle) {
        attachSettings(settings, gradle);
        load(settings.getRootProject(), settings.getDefaultProject(), gradle, settings.getRootClassLoaderScope());
    }

    private void attachSettings(SettingsInternal settings, GradleInternal gradle) {
        gradle.setSettings(settings);
    }

    private void load(ProjectDescriptor rootProjectDescriptor, ProjectDescriptor defaultProject, GradleInternal gradle, ClassLoaderScope buildRootClassLoaderScope) {
        createProjects(rootProjectDescriptor, gradle, buildRootClassLoaderScope);
        attachDefaultProject(defaultProject, gradle);
    }

    private void attachDefaultProject(ProjectDescriptor defaultProject, GradleInternal gradle) {
        gradle.setDefaultProject(gradle.getRootProject().getProjectRegistry().getProject(defaultProject.getPath()));
    }

    private void createProjects(ProjectDescriptor rootProjectDescriptor, GradleInternal gradle, ClassLoaderScope buildRootClassLoaderScope) {
        ProjectInternal rootProject = projectFactory.createProject(rootProjectDescriptor, null, gradle, buildRootClassLoaderScope.createChild("root-project"), buildRootClassLoaderScope);
        gradle.setRootProject(rootProject);
        addProjects(rootProject, rootProjectDescriptor, gradle, buildRootClassLoaderScope);
    }

    private void addProjects(ProjectInternal parent, ProjectDescriptor parentProjectDescriptor, GradleInternal gradle, ClassLoaderScope buildRootClassLoaderScope) {
        for (ProjectDescriptor childProjectDescriptor : parentProjectDescriptor.getChildren()) {
            ProjectInternal childProject = projectFactory.createProject(childProjectDescriptor, parent, gradle, parent.getClassLoaderScope().createChild("project-" + childProjectDescriptor.getName()), buildRootClassLoaderScope);
            addProjects(childProject, childProjectDescriptor, gradle, buildRootClassLoaderScope);
        }
    }
}
