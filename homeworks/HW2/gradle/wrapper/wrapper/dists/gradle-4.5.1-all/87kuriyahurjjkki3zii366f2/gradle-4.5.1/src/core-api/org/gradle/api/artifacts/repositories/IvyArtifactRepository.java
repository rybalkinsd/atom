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
package org.gradle.api.artifacts.repositories;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.ActionConfiguration;
import org.gradle.api.Incubating;
import org.gradle.api.artifacts.ComponentMetadataSupplier;

import java.net.URI;

/**
 * An artifact repository which uses an Ivy format to store artifacts and meta-data.
 * <p>
 * When used to resolve metadata and artifact files, all available patterns will be searched.
 * <p>
 * When used to upload metadata and artifact files, only a single, primary pattern will be used:
 * <ol>
 * <li>If a URL is specified via {@link #setUrl(Object)} then that URL will be used for upload, combined with the applied {@link #layout(String)}.</li>
 * <li>If no URL has been specified but additional patterns have been added via {@link #artifactPattern} or {@link #ivyPattern}, then the first defined pattern will be used.</li>
 * </ol>
 * <p>
 * Repositories of this type are created by the {@link org.gradle.api.artifacts.dsl.RepositoryHandler#ivy(org.gradle.api.Action)} group of methods.
 */
public interface IvyArtifactRepository extends ArtifactRepository, AuthenticationSupported {

    String IVY_ARTIFACT_PATTERN = "[organisation]/[module]/[revision]/[type]s/[artifact](.[ext])";

    String GRADLE_ARTIFACT_PATTERN = "[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier])(.[ext])";
    String GRADLE_IVY_PATTERN = "[organisation]/[module]/[revision]/ivy-[revision].xml";

    String MAVEN_ARTIFACT_PATTERN = "[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier])(.[ext])";
    String MAVEN_IVY_PATTERN = "[organisation]/[module]/[revision]/ivy-[revision].xml";

    /**
     * The base URL of this repository.
     *
     * @return The URL.
     */
    URI getUrl();

    /**
     * Sets the base URL of this repository.
     *
     * @param url The base URL.
     * @since 4.0
     */
    void setUrl(URI url);

    /**
     * Sets the base URL of this repository. The provided value is evaluated as per {@link org.gradle.api.Project#uri(Object)}. This means,
     * for example, you can pass in a File object or a relative path which is evaluated relative to the project directory.
     *
     * File are resolved based on the supplied URL and the configured {@link #layout(String, Closure)} for this repository.
     *
     * @param url The base URL.
     */
    void setUrl(Object url);

    /**
     * Adds an independent pattern that will be used to locate artifact files in this repository. This pattern will be used to locate ivy files as well, unless a specific
     * ivy pattern is supplied via {@link #ivyPattern(String)}.
     *
     * If this pattern is not a fully-qualified URL, it will be interpreted as a file relative to the project directory.
     * It is not interpreted relative the URL specified in {@link #setUrl(Object)}.
     *
     * Patterns added in this way will be in addition to any layout-based patterns added via {@link #setUrl(Object)}.
     *
     * @param pattern The artifact pattern.
     */
    void artifactPattern(String pattern);

    /**
     * Adds an independent pattern that will be used to locate ivy files in this repository.
     *
     * If this pattern is not a fully-qualified URL, it will be interpreted as a file relative to the project directory.
     * It is not interpreted relative the URL specified in {@link #setUrl(Object)}.
     *
     * Patterns added in this way will be in addition to any layout-based patterns added via {@link #setUrl(Object)}.
     *
     * @param pattern The ivy pattern.
     */
    void ivyPattern(String pattern);

    /**
     * Specifies the layout to use with this repository, based on the root url.
     * See {@link #layout(String, Closure)}.
     *
     * @param layoutName The name of the layout to use.
     */
    void layout(String layoutName);

    /**
     * Specifies how the items of the repository are organized.
     * <p>
     * The layout is configured with the supplied closure.
     * <p>
     * Recognised values are as follows:
     * </p>
     * <h4>'gradle'</h4>
     * <p>
     * A Repository Layout that applies the following patterns:
     * </p>
     * <ul>
     *     <li>Artifacts: <code>$baseUri/{@value #GRADLE_ARTIFACT_PATTERN}</code></li>
     *     <li>Ivy: <code>$baseUri/{@value #GRADLE_IVY_PATTERN}</code></li>
     * </ul>
     * <h4>'maven'</h4>
     * <p>
     * A Repository Layout that applies the following patterns:
     * </p>
     * <ul>
     *     <li>Artifacts: <code>$baseUri/{@value #MAVEN_ARTIFACT_PATTERN}</code></li>
     *     <li>Ivy: <code>$baseUri/{@value #MAVEN_IVY_PATTERN}</code></li>
     * </ul>
     * <p>
     * Following the Maven convention, the 'organisation' value is further processed by replacing '.' with '/'.
     * </p>
     * <h4>'ivy'</h4>
     * <p>
     * A Repository Layout that applies the following patterns:
     * </p>
     * <ul>
     *     <li>Artifacts: <code>$baseUri/{@value #IVY_ARTIFACT_PATTERN}</code></li>
     *     <li>Ivy: <code>$baseUri/{@value #IVY_ARTIFACT_PATTERN}</code></li>
     * </ul>
     * <p><b>Note:</b> this pattern is currently {@link org.gradle.api.Incubating incubating}.</p>
     * <h4>'pattern'</h4>
     * <p>
     * A repository layout that allows custom patterns to be defined. eg:
     * </p>
     * <pre class='autoTested'>
     * repositories {
     *     ivy {
     *         layout 'pattern' , {
     *             artifact '[module]/[revision]/[artifact](.[ext])'
     *             ivy '[module]/[revision]/ivy.xml'
     *         }
     *     }
     * }
     * </pre>
     * <p>The available pattern tokens are listed as part of <a href="http://ant.apache.org/ivy/history/latest-milestone/concept.html#patterns">Ivy's Main Concepts documentation</a>.</p>
     *
     * @param layoutName The name of the layout to use.
     * @param config The action used to configure the layout.
     * @since 2.3 (feature was already present in Groovy DSL, this particular method introduced in 2.3)
     */
    void layout(String layoutName, Action<? extends RepositoryLayout> config);

    /**
     * Specifies how the items of the repository are organized. See {@link #layout(String, org.gradle.api.Action)}
     *
     * @param layoutName The name of the layout to use.
     * @param config The closure used to configure the layout.
     * An instance of {@link RepositoryLayout} is passed as a parameter to the closure.
     */
    void layout(String layoutName, Closure config);

    /**
     * Returns the meta-data provider used when resolving artifacts from this repository. The provider is responsible for locating and interpreting the meta-data
     * for the modules and artifacts contained in this repository. Using this provider, you can fine tune how this resolution happens.
     *
     * @return The meta-data provider for this repository.
     */
    @Incubating
    IvyArtifactRepositoryMetaDataProvider getResolve();

    /**
     * Sets a custom metadata rule, which is capable of supplying the metadata of a component (status, status scheme, changing flag)
     * whenever a dynamic version is requested. It can be used to provide metadata directly, instead of having to parse the Ivy
     * descriptor.
     *
     * @param rule the class of the rule. Gradle will instantiate a new rule for each dependency which requires metadata.
     *
     * @since 4.0
     */
    @Incubating
    void setMetadataSupplier(Class<? extends ComponentMetadataSupplier> rule);

    /**
     * Sets a custom metadata rule, possibly configuring the rule.
     *
     * @param rule the class of the rule. Gradle will instantiate a new rule for each dependency which requires metadata.
     * @param configureAction the action to use to configure the rule.
     *
     * @since 4.0
     */
    @Incubating
    void setMetadataSupplier(Class<? extends ComponentMetadataSupplier> rule, Action<? super ActionConfiguration> configureAction);

    /**
     * Configures the metadata sources for this repository. This method will replace any previously configured sources
     * of metadata.
     *
     * @param configureAction the configuration of metadata sources.
     *
     * @since 4.5
     */
    @Incubating
    void metadataSources(Action<? super MetadataSources> configureAction);

    /**
     * Allows configuring the sources of metadata for a specific repository.
     *
     * @since 4.5
     *
     */
    @Incubating
    interface MetadataSources {
        /**
         * Indicates that this repository will contain Gradle metadata.
         */
        void gradleMetadata();

        /**
         * Indicates that this repository will contain Ivy descriptors.
         */
        void ivyDescriptor();

        /**
         * Indicates that this repository may not contain metadata files,
         * but we can infer it from the presence of an artifact file.
         */
        void artifact();
    }

}
