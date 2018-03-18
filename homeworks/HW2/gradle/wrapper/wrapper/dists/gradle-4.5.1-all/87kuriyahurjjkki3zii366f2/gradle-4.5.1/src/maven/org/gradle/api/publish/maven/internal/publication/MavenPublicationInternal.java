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

package org.gradle.api.publish.maven.internal.publication;

import org.gradle.api.file.FileCollection;
import org.gradle.api.publish.internal.PublicationInternal;
import org.gradle.api.publish.maven.MavenDependency;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.internal.dependencies.MavenDependencyInternal;
import org.gradle.api.publish.maven.internal.publisher.MavenNormalizedPublication;
import org.gradle.api.publish.maven.internal.publisher.MavenProjectIdentity;

import java.util.Set;

public interface MavenPublicationInternal extends MavenPublication, PublicationInternal {

    MavenPomInternal getPom();

    void setPomFile(FileCollection pomFile);

    void setGradleModuleMetadataFile(FileCollection metadatafile);

    FileCollection getPublishableFiles();

    MavenProjectIdentity getMavenProjectIdentity();

    Set<MavenDependency> getApiDependencyConstraints();

    Set<MavenDependency> getRuntimeDependencyConstraints();

    Set<MavenDependencyInternal> getApiDependencies();

    Set<MavenDependencyInternal> getRuntimeDependencies();

    MavenNormalizedPublication asNormalisedPublication();

    // TODO Remove this attempt to guess packaging from artifacts. Packaging should come from component, or be explicitly set.
    String determinePackagingFromArtifacts();

    /**
     * Some components (e.g. Native) are published such that the module metadata references the original file name,
     * rather than the Maven-standard {artifactId}-{version}-{classifier}.{extension}.
     * This method enables this behaviour for the current publication.
     */
    void publishWithOriginalFileName();

    boolean canPublishModuleMetadata();
}

