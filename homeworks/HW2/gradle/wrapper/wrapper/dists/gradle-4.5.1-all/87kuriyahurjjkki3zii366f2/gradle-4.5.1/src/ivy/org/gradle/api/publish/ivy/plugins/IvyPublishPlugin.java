/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.publish.ivy.plugins;

import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.NamedDomainObjectSet;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.IvyArtifactRepository;
import org.gradle.api.internal.ExperimentalFeatures;
import org.gradle.api.internal.artifacts.Module;
import org.gradle.api.internal.artifacts.configurations.DependencyMetaDataProvider;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.file.FileCollectionFactory;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.publish.Publication;
import org.gradle.api.publish.PublicationContainer;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.internal.ProjectDependencyPublicationResolver;
import org.gradle.api.publish.ivy.IvyArtifact;
import org.gradle.api.publish.ivy.IvyPublication;
import org.gradle.api.publish.ivy.internal.artifact.IvyArtifactNotationParserFactory;
import org.gradle.api.publish.ivy.internal.publication.DefaultIvyPublication;
import org.gradle.api.publish.ivy.internal.publication.DefaultIvyPublicationIdentity;
import org.gradle.api.publish.ivy.internal.publication.IvyPublicationInternal;
import org.gradle.api.publish.ivy.internal.publisher.IvyPublicationIdentity;
import org.gradle.api.publish.ivy.tasks.GenerateIvyDescriptor;
import org.gradle.api.publish.ivy.tasks.PublishToIvyRepository;
import org.gradle.api.publish.plugins.PublishingPlugin;
import org.gradle.api.publish.tasks.GenerateModuleMetadata;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.internal.typeconversion.NotationParser;
import org.gradle.model.ModelMap;
import org.gradle.model.Mutate;
import org.gradle.model.Path;
import org.gradle.model.RuleSource;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.capitalize;

/**
 * Adds the ability to publish in the Ivy format to Ivy repositories.
 *
 * @since 1.3
 */
@Incubating
public class IvyPublishPlugin implements Plugin<Project> {

    private final Instantiator instantiator;
    private final DependencyMetaDataProvider dependencyMetaDataProvider;
    private final FileResolver fileResolver;
    private final ProjectDependencyPublicationResolver projectDependencyResolver;
    private final FileCollectionFactory fileCollectionFactory;
    private final ImmutableAttributesFactory immutableAttributesFactory;
    private final ExperimentalFeatures experimentalFeatures;

    @Inject
    public IvyPublishPlugin(Instantiator instantiator, DependencyMetaDataProvider dependencyMetaDataProvider, FileResolver fileResolver,
                            ProjectDependencyPublicationResolver projectDependencyResolver, FileCollectionFactory fileCollectionFactory,
                            ImmutableAttributesFactory immutableAttributesFactory, ExperimentalFeatures experimentalFeatures) {
        this.instantiator = instantiator;
        this.dependencyMetaDataProvider = dependencyMetaDataProvider;
        this.fileResolver = fileResolver;
        this.projectDependencyResolver = projectDependencyResolver;
        this.fileCollectionFactory = fileCollectionFactory;
        this.immutableAttributesFactory = immutableAttributesFactory;
        this.experimentalFeatures = experimentalFeatures;
    }

    public void apply(final Project project) {
        project.getPluginManager().apply(PublishingPlugin.class);

        // Can't move this to rules yet, because it has to happen before user deferred configurable actions
        project.getExtensions().configure(PublishingExtension.class, new Action<PublishingExtension>() {
            public void execute(PublishingExtension extension) {
                // Register factory for MavenPublication
                extension.getPublications().registerFactory(IvyPublication.class, new IvyPublicationFactory(dependencyMetaDataProvider, instantiator, fileResolver));
            }
        });
    }

    static class Rules extends RuleSource {
        @Mutate
        @SuppressWarnings("UnusedDeclaration")
        public void createTasks(ModelMap<Task> tasks, PublishingExtension publishingExtension, @Path("buildDir") final File buildDir) {
            PublicationContainer publications = publishingExtension.getPublications();
            RepositoryHandler repositories = publishingExtension.getRepositories();
            NamedDomainObjectSet<IvyPublicationInternal> mavenPublications = publications.withType(IvyPublicationInternal.class);
            List<Publication> asPublication = new ArrayList<Publication>(publications);

            for (final IvyPublicationInternal publication : publications.withType(IvyPublicationInternal.class)) {

                final String publicationName = publication.getName();
                createGenerateIvyDescriptorTask(tasks, publicationName, publication, buildDir);
                createGenerateMetadataTask(tasks, publication, asPublication, buildDir);

                for (final IvyArtifactRepository repository : repositories.withType(IvyArtifactRepository.class)) {
                    final String repositoryName = repository.getName();
                    final String publishTaskName = "publish" + capitalize(publicationName) + "PublicationTo" + capitalize(repositoryName) + "Repository";

                    createPublishToRepositoryTask(tasks, publication, publicationName, repository, repositoryName, publishTaskName);
                }
            }
        }

        private void createPublishToRepositoryTask(ModelMap<Task> tasks, final IvyPublicationInternal publication, final String publicationName, final IvyArtifactRepository repository, final String repositoryName, String publishTaskName) {
            tasks.create(publishTaskName, PublishToIvyRepository.class, new Action<PublishToIvyRepository>() {
                public void execute(PublishToIvyRepository publishTask) {
                    publishTask.setPublication(publication);
                    publishTask.setRepository(repository);
                    publishTask.setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);
                    publishTask.setDescription("Publishes Ivy publication '" + publicationName + "' to Ivy repository '" + repositoryName + "'.");
                }
            });
            tasks.get(PublishingPlugin.PUBLISH_LIFECYCLE_TASK_NAME).dependsOn(publishTaskName);
        }

        private void createGenerateIvyDescriptorTask(ModelMap<Task> tasks, final String publicationName, final IvyPublicationInternal publication, @Path("buildDir") final File buildDir) {
            final String descriptorTaskName = "generateDescriptorFileFor" + capitalize(publicationName) + "Publication";

            tasks.create(descriptorTaskName, GenerateIvyDescriptor.class, new Action<GenerateIvyDescriptor>() {
                public void execute(final GenerateIvyDescriptor descriptorTask) {
                    descriptorTask.setDescription("Generates the Ivy Module Descriptor XML file for publication '" + publicationName + "'.");
                    descriptorTask.setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);
                    descriptorTask.setDescriptor(publication.getDescriptor());
                    descriptorTask.setDestination(new File(buildDir, "publications/" + publicationName + "/ivy.xml"));
                }
            });
            publication.setIvyDescriptorFile(tasks.get(descriptorTaskName).getOutputs().getFiles());
        }

        private void createGenerateMetadataTask(ModelMap<Task> tasks, final IvyPublicationInternal publication, final List<Publication> publications, final File buildDir) {
            if (!publication.canPublishModuleMetadata()) {
                return;
            }

            final String publicationName = publication.getName();
            String descriptorTaskName = "generateMetadataFileFor" + capitalize(publicationName) + "Publication";
            tasks.create(descriptorTaskName, GenerateModuleMetadata.class, new Action<GenerateModuleMetadata>() {
                public void execute(final GenerateModuleMetadata generateTask) {
                    generateTask.setDescription("Generates the Gradle metadata file for publication '" + publicationName + "'.");
                    generateTask.setGroup(PublishingPlugin.PUBLISH_TASK_GROUP);
                    generateTask.getPublication().set(publication);
                    generateTask.getPublications().set(publications);
                    // TODO - should deal with build dir changes
                    generateTask.getOutputFile().set(new File(buildDir, "publications/" + publicationName + "/module.json"));
                }
            });
            publication.setGradleModuleDescriptorFile(tasks.get(descriptorTaskName).getOutputs().getFiles());
        }
    }

    private class IvyPublicationFactory implements NamedDomainObjectFactory<IvyPublication> {
        private final Instantiator instantiator;
        private final DependencyMetaDataProvider dependencyMetaDataProvider;
        private final FileResolver fileResolver;

        private IvyPublicationFactory(DependencyMetaDataProvider dependencyMetaDataProvider, Instantiator instantiator, FileResolver fileResolver) {
            this.dependencyMetaDataProvider = dependencyMetaDataProvider;
            this.instantiator = instantiator;
            this.fileResolver = fileResolver;
        }

        public IvyPublication create(String name) {
            Module module = dependencyMetaDataProvider.getModule();
            IvyPublicationIdentity publicationIdentity = new DefaultIvyPublicationIdentity(module.getGroup(), module.getName(), module.getVersion());
            NotationParser<Object, IvyArtifact> notationParser = new IvyArtifactNotationParserFactory(instantiator, fileResolver, publicationIdentity).create();
            return instantiator.newInstance(
                DefaultIvyPublication.class,
                name, instantiator, publicationIdentity, notationParser, projectDependencyResolver, fileCollectionFactory, immutableAttributesFactory, experimentalFeatures
            );
        }
    }

}
