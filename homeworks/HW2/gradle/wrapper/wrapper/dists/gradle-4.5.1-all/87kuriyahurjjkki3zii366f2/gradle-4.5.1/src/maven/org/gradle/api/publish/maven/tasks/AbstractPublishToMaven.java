/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.api.publish.maven.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.Incubating;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.artifacts.mvnsettings.LocalMavenRepositoryLocator;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal;
import org.gradle.api.tasks.Internal;
import org.gradle.internal.Factory;
import org.gradle.internal.logging.LoggingManagerInternal;

import javax.inject.Inject;
import java.util.concurrent.Callable;

/**
 * Base class for tasks that publish a {@link org.gradle.api.publish.maven.MavenPublication}.
 *
 * @since 2.4
 */
@Incubating
public abstract class AbstractPublishToMaven extends DefaultTask {

    private MavenPublicationInternal publication;

    public AbstractPublishToMaven() {
        // Allow the publication to participate in incremental build
        getInputs().files(new Callable<FileCollection>() {
            public FileCollection call() throws Exception {
                MavenPublicationInternal publicationInternal = getPublicationInternal();
                return publicationInternal == null ? null : publicationInternal.getPublishableFiles();
            }
        }).withPropertyName("publication.publishableFiles");

        // Should repositories be able to participate in incremental?
        // At the least, they may be able to express themselves as output files
        // They *might* have input files and other dependencies as well though
        // Inputs: The credentials they need may be expressed in a file
        // Dependencies: Can't think of a case here
    }


    /**
     * The publication to be published.
     *
     * @return The publication to be published
     */
    @Internal
    public MavenPublication getPublication() {
        return publication;
    }

    /**
     * Sets the publication to be published.
     *
     * @param publication The publication to be published
     */
    public void setPublication(MavenPublication publication) {
        this.publication = toPublicationInternal(publication);
    }

    @Internal
    protected MavenPublicationInternal getPublicationInternal() {
        return toPublicationInternal(getPublication());
    }

    private static MavenPublicationInternal toPublicationInternal(MavenPublication publication) {
        if (publication == null) {
            return null;
        } else if (publication instanceof MavenPublicationInternal) {
            return (MavenPublicationInternal) publication;
        } else {
            throw new InvalidUserDataException(
                    String.format(
                            "publication objects must implement the '%s' interface, implementation '%s' does not",
                            MavenPublicationInternal.class.getName(),
                            publication.getClass().getName()
                    )
            );
        }
    }

    @Inject
    protected Factory<LoggingManagerInternal> getLoggingManagerFactory() {
        throw new UnsupportedOperationException();
    }

    @Inject
    protected LocalMavenRepositoryLocator getMavenRepositoryLocator() {
        throw new UnsupportedOperationException();
    }

}
