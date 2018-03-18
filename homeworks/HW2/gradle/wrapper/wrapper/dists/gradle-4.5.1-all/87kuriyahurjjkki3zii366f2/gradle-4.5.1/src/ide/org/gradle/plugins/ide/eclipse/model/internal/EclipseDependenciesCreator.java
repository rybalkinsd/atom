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

package org.gradle.plugins.ide.eclipse.model.internal;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.artifacts.component.ProjectComponentIdentifier;
import org.gradle.api.artifacts.result.ResolvedArtifactResult;
import org.gradle.api.artifacts.result.UnresolvedDependencyResult;
import org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier;
import org.gradle.api.internal.artifacts.ivyservice.projectmodule.LocalComponentRegistry;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.tasks.SourceSet;
import org.gradle.internal.component.local.model.DefaultProjectComponentIdentifier;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.plugins.ide.eclipse.internal.EclipsePluginConstants;
import org.gradle.plugins.ide.eclipse.model.AbstractClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.AbstractLibrary;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.FileReference;
import org.gradle.plugins.ide.eclipse.model.Library;
import org.gradle.plugins.ide.eclipse.model.Variable;
import org.gradle.plugins.ide.internal.resolver.IdeDependencySet;
import org.gradle.plugins.ide.internal.resolver.IdeDependencyVisitor;
import org.gradle.plugins.ide.internal.resolver.UnresolvedIdeDependencyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EclipseDependenciesCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EclipseDependenciesCreator.class);
    private final EclipseClasspath classpath;
    private final ProjectDependencyBuilder projectDependencyBuilder;

    public EclipseDependenciesCreator(EclipseClasspath classpath) {
        this.classpath = classpath;
        ServiceRegistry serviceRegistry = ((ProjectInternal) classpath.getProject()).getServices();
        this.projectDependencyBuilder = new ProjectDependencyBuilder(serviceRegistry.get(LocalComponentRegistry.class));
    }

    public List<AbstractClasspathEntry> createDependencyEntries() {
        EclipseDependenciesVisitor visitor = new EclipseDependenciesVisitor();
        new IdeDependencySet(classpath.getProject().getDependencies(), classpath.getPlusConfigurations(), classpath.getMinusConfigurations()).visit(visitor);
        return visitor.getDependencies();
    }

    private class EclipseDependenciesVisitor implements IdeDependencyVisitor {

        private final List<AbstractClasspathEntry> projects = Lists.newArrayList();
        private final List<AbstractClasspathEntry> modules = Lists.newArrayList();
        private final List<AbstractClasspathEntry> files = Lists.newArrayList();
        private final Multimap<String, String> pathToSourceSets = collectLibraryToSourceSetMapping();
        private final UnresolvedIdeDependencyHandler unresolvedIdeDependencyHandler = new UnresolvedIdeDependencyHandler();
        private final ProjectComponentIdentifier currentProjectId = DefaultProjectComponentIdentifier.newProjectId(classpath.getProject());


        @Override
        public boolean isOffline() {
            return classpath.isProjectDependenciesOnly();
        }

        @Override
        public boolean downloadSources() {
            return classpath.isDownloadSources();
        }

        @Override
        public boolean downloadJavaDoc() {
            return classpath.isDownloadJavadoc();
        }

        @Override
        public void visitProjectDependency(ResolvedArtifactResult artifact) {
            ProjectComponentIdentifier componentIdentifier = (ProjectComponentIdentifier) artifact.getId().getComponentIdentifier();
            if (!componentIdentifier.equals(currentProjectId)) {
                projects.add(projectDependencyBuilder.build(componentIdentifier));
            }
        }

        @Override
        public void visitModuleDependency(ResolvedArtifactResult artifact, Set<ResolvedArtifactResult> sources, Set<ResolvedArtifactResult> javaDoc) {
            File sourceFile = sources.isEmpty() ? null : sources.iterator().next().getFile();
            File javaDocFile = javaDoc.isEmpty() ? null : javaDoc.iterator().next().getFile();
            ModuleComponentIdentifier componentIdentifier = (ModuleComponentIdentifier) artifact.getId().getComponentIdentifier();
            DefaultModuleVersionIdentifier moduleVersionIdentifier = new DefaultModuleVersionIdentifier(componentIdentifier.getGroup(), componentIdentifier.getModule(), componentIdentifier.getVersion());
            modules.add(createLibraryEntry(artifact.getFile(), sourceFile, javaDocFile, classpath, moduleVersionIdentifier, pathToSourceSets));
        }

        @Override
        public void visitFileDependency(ResolvedArtifactResult artifact) {
            files.add(createLibraryEntry(artifact.getFile(), null, null, classpath, null, pathToSourceSets));
        }

        @Override
        public void visitUnresolvedDependency(UnresolvedDependencyResult unresolvedDependency) {
            File unresolvedFile = unresolvedIdeDependencyHandler.asFile(unresolvedDependency);
            files.add(createLibraryEntry(unresolvedFile, null, null, classpath, null, pathToSourceSets));
            unresolvedIdeDependencyHandler.log(unresolvedDependency);
        }

        /*
         * This method returns the dependencies in buckets (projects first, then modules, then files),
         * because that's what we used to do since 1.0. It would be better to return the dependencies
         * in the same order as they come from the resolver, but we'll need to change all the tests for
         * that, so defer that until later.
         */
        public List<AbstractClasspathEntry> getDependencies() {
            List<AbstractClasspathEntry> dependencies = Lists.newArrayListWithCapacity(projects.size() + modules.size() + files.size());
            dependencies.addAll(projects);
            dependencies.addAll(modules);
            dependencies.addAll(files);
            return dependencies;
        }

        private Multimap<String, String> collectLibraryToSourceSetMapping() {
            Multimap<String, String> pathToSourceSetNames = LinkedHashMultimap.create();
            Iterable<SourceSet> sourceSets = classpath.getSourceSets();

            // for non-java projects there are no source sets configured
            if (sourceSets == null) {
                return pathToSourceSetNames;
            }

            for (SourceSet sourceSet : sourceSets) {
                for (File f : collectClasspathFiles(sourceSet)) {
                    pathToSourceSetNames.put(f.getAbsolutePath(), sourceSet.getName().replace(",", ""));
                }
            }
            return pathToSourceSetNames;
        }

        /*
         * SourceSet has no access to configurations where we could ask for a lenient view. This
         * means we have to deal with possible dependency resolution issues here. We catch and
         * log the exceptions here so that the Eclipse model can be generated even if there are
         * unresolvable dependencies defined in the configuration.
         *
         * We can probably do better by inspecting the runtime classpath and finding out which
         * Configurations are part of it and only traversing any extra file collections manually.
         */
        private Collection<File> collectClasspathFiles(SourceSet sourceSet) {
            ImmutableList.Builder<File> result = ImmutableList.builder();
            try {
                result.addAll(sourceSet.getRuntimeClasspath());
            } catch (Exception e) {
                LOGGER.debug("Failed to collect source sets for Eclipse dependencies", e);
            }
            return result.build();
        }

        private AbstractLibrary createLibraryEntry(File binary, File source, File javadoc, EclipseClasspath classpath, ModuleVersionIdentifier id, Multimap<String, String> pathToSourceSets) {
            FileReferenceFactory referenceFactory = classpath.getFileReferenceFactory();

            FileReference binaryRef = referenceFactory.fromFile(binary);
            FileReference sourceRef = referenceFactory.fromFile(source);
            FileReference javadocRef = referenceFactory.fromFile(javadoc);

            final AbstractLibrary out = binaryRef.isRelativeToPathVariable() ? new Variable(binaryRef) : new Library(binaryRef);

            out.setJavadocPath(javadocRef);
            out.setSourcePath(sourceRef);
            out.setExported(false);
            out.setModuleVersion(id);

            Collection<String> sourceSets = pathToSourceSets.get(binary.getAbsolutePath());
            if (sourceSets != null) {
                out.getEntryAttributes().put(EclipsePluginConstants.GRADLE_USED_BY_SCOPE_ATTRIBUTE_NAME, Joiner.on(',').join(sourceSets));
            }
            return out;
        }
    }
}
