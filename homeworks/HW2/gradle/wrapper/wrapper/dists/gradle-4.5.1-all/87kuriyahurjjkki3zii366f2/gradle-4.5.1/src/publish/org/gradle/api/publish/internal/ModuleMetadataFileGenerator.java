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

package org.gradle.api.publish.internal;

import com.google.common.base.Strings;
import com.google.gson.stream.JsonWriter;
import org.gradle.api.Named;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencyConstraint;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.ExternalDependency;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.VersionConstraint;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.component.ComponentWithVariants;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.internal.artifacts.DefaultExcludeRule;
import org.gradle.api.internal.artifacts.dependencies.DefaultImmutableVersionConstraint;
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.ModuleMetadataParser;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.internal.component.UsageContext;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.scopeids.id.BuildInvocationScopeId;
import org.gradle.util.GUtil;
import org.gradle.util.GradleVersion;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>The Gradle module metadata file generator is responsible for generating a JSON file
 * describing module metadata. In particular, this file format is capable of handling different
 * variants with different dependency sets.</p>
 *
 * <p>Whenever you change this class, make sure you also:</p>
 *
 * <ul>
 *     <li>Update the corresponding {@link ModuleMetadataParser module metadata parser}</li>
 *     <li>Update the module metadata specification (subprojects/docs/src/docs/design/gradle-module-metadata-specification.md)</li>
 *     <li>Update {@link org.gradle.api.internal.artifacts.ivyservice.modulecache.ModuleMetadataSerializer the module metadata serializer} </li>
 *     <li>Add a sample for the module metadata serializer test, to make sure that serialized metadata is idempotent</li>
 * </ul>
 */
public class ModuleMetadataFileGenerator {
    private final BuildInvocationScopeId buildInvocationScopeId;
    private final ProjectDependencyPublicationResolver projectDependencyResolver;

    public ModuleMetadataFileGenerator(BuildInvocationScopeId buildInvocationScopeId, ProjectDependencyPublicationResolver projectDependencyResolver) {
        this.buildInvocationScopeId = buildInvocationScopeId;
        this.projectDependencyResolver = projectDependencyResolver;
    }

    public void generateTo(PublicationInternal publication, Collection<? extends PublicationInternal> publications, Writer writer) throws IOException {
        // Collect a map from component to coordinates. This might be better to move to the component or some publications model
        Map<SoftwareComponent, ComponentData> coordinates = new HashMap<SoftwareComponent, ComponentData>();
        collectCoordinates(publications, coordinates);

        // Collect a map from component to its owning component. This might be better to move to the component or some publications model
        Map<SoftwareComponent, SoftwareComponent> owners = new HashMap<SoftwareComponent, SoftwareComponent>();
        collectOwners(publications, owners);

        // Write the output
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setHtmlSafe(false);
        jsonWriter.setIndent("  ");
        writeComponentWithVariants(publication, publication.getComponent(), coordinates, owners, jsonWriter);
        jsonWriter.flush();
        writer.append('\n');
    }

    private void collectOwners(Collection<? extends PublicationInternal> publications, Map<SoftwareComponent, SoftwareComponent> owners) {
        for (PublicationInternal publication : publications) {
            if (publication.getComponent() instanceof ComponentWithVariants) {
                ComponentWithVariants componentWithVariants = (ComponentWithVariants) publication.getComponent();
                for (SoftwareComponent child : componentWithVariants.getVariants()) {
                    owners.put(child, publication.getComponent());
                }
            }
        }
    }

    private void collectCoordinates(Collection<? extends PublicationInternal> publications, Map<SoftwareComponent, ComponentData> coordinates) {
        for (PublicationInternal publication : publications) {
            if (publication.getComponent() != null) {
                ModuleVersionIdentifier moduleVersionIdentifier = publication.getCoordinates();
                ImmutableAttributes attributes = publication.getAttributes();
                coordinates.put(publication.getComponent(), new ComponentData(moduleVersionIdentifier, attributes));
            }
        }
    }

    private void writeComponentWithVariants(PublicationInternal publication, SoftwareComponent component, Map<SoftwareComponent, ComponentData> componentCoordinates, Map<SoftwareComponent, SoftwareComponent> owners, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        writeFormat(jsonWriter);
        writeIdentity(publication.getCoordinates(), publication.getAttributes(), component, componentCoordinates, owners, jsonWriter);
        writeCreator(jsonWriter);
        writeVariants(publication, component, componentCoordinates, jsonWriter);
        jsonWriter.endObject();
    }

    private void writeVersionConstraint(VersionConstraint versionConstraint, JsonWriter jsonWriter) throws IOException {
        if (DefaultImmutableVersionConstraint.of().equals(versionConstraint)) {
            return;
        }

        jsonWriter.name("version");
        jsonWriter.beginObject();
        if (!versionConstraint.getPreferredVersion().isEmpty()) {
            jsonWriter.name("prefers");
            jsonWriter.value(versionConstraint.getPreferredVersion());
        }
        List<String> rejectedVersions = versionConstraint.getRejectedVersions();
        if (!rejectedVersions.isEmpty()) {
            jsonWriter.name("rejects");
            jsonWriter.beginArray();
            for (String reject : rejectedVersions) {
                jsonWriter.value(reject);
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    private void writeIdentity(ModuleVersionIdentifier coordinates, ImmutableAttributes attributes, SoftwareComponent component, Map<SoftwareComponent, ComponentData> componentCoordinates, Map<SoftwareComponent, SoftwareComponent> owners, JsonWriter jsonWriter) throws IOException {
        SoftwareComponent owner = owners.get(component);
        if (owner == null) {
            jsonWriter.name("component");
            jsonWriter.beginObject();
            jsonWriter.name("group");
            jsonWriter.value(coordinates.getGroup());
            jsonWriter.name("module");
            jsonWriter.value(coordinates.getName());
            jsonWriter.name("version");
            jsonWriter.value(coordinates.getVersion());
            writeAttributes(attributes, jsonWriter);
            jsonWriter.endObject();
        } else {
            ComponentData componentData = componentCoordinates.get(owner);
            ModuleVersionIdentifier ownerCoordinates = componentData.coordinates;
            jsonWriter.name("component");
            jsonWriter.beginObject();
            jsonWriter.name("url");
            jsonWriter.value(relativeUrlTo(coordinates, ownerCoordinates));
            jsonWriter.name("group");
            jsonWriter.value(ownerCoordinates.getGroup());
            jsonWriter.name("module");
            jsonWriter.value(ownerCoordinates.getName());
            jsonWriter.name("version");
            jsonWriter.value(ownerCoordinates.getVersion());
            writeAttributes(componentData.attributes, jsonWriter);
            jsonWriter.endObject();
        }
    }

    private void writeVariants(PublicationInternal publication, SoftwareComponent component, Map<SoftwareComponent, ComponentData> componentCoordinates, JsonWriter jsonWriter) throws IOException {
        boolean started = false;
        for (UsageContext usageContext : ((SoftwareComponentInternal) component).getUsages()) {
            if (!started) {
                jsonWriter.name("variants");
                jsonWriter.beginArray();
                started = true;
            }
            writeVariantHostedInThisModule(publication, usageContext, jsonWriter);
        }
        if (component instanceof ComponentWithVariants) {
            for (SoftwareComponent childComponent : ((ComponentWithVariants) component).getVariants()) {
                ComponentData componentData = componentCoordinates.get(childComponent);
                ModuleVersionIdentifier childCoordinates = componentData == null ? null : componentData.coordinates;
                if (childCoordinates == null) {
                    continue;
                }
                if (childComponent instanceof SoftwareComponentInternal) {
                    for (UsageContext usageContext : ((SoftwareComponentInternal) childComponent).getUsages()) {
                        if (!started) {
                            jsonWriter.name("variants");
                            jsonWriter.beginArray();
                            started = true;
                        }
                        writeVariantHostedInAnotherModule(publication.getCoordinates(), childCoordinates, usageContext, jsonWriter);
                    }
                }
            }
        }
        if (started) {
            jsonWriter.endArray();
        }
    }

    private void writeCreator(JsonWriter jsonWriter) throws IOException {
        jsonWriter.name("createdBy");
        jsonWriter.beginObject();
        jsonWriter.name("gradle");
        jsonWriter.beginObject();
        jsonWriter.name("version");
        jsonWriter.value(GradleVersion.current().getVersion());
        jsonWriter.name("buildId");
        jsonWriter.value(buildInvocationScopeId.getId().asString());
        jsonWriter.endObject();
        jsonWriter.endObject();
    }

    private void writeFormat(JsonWriter jsonWriter) throws IOException {
        jsonWriter.name("formatVersion");
        jsonWriter.value(ModuleMetadataParser.FORMAT_VERSION);
    }

    private void writeVariantHostedInAnotherModule(ModuleVersionIdentifier coordinates, ModuleVersionIdentifier targetCoordinates, UsageContext variant, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(variant.getName());
        writeAttributes(variant.getAttributes(), jsonWriter);

        jsonWriter.name("available-at");
        jsonWriter.beginObject();

        jsonWriter.name("url");
        jsonWriter.value(relativeUrlTo(coordinates, targetCoordinates));

        jsonWriter.name("group");
        jsonWriter.value(targetCoordinates.getGroup());
        jsonWriter.name("module");
        jsonWriter.value(targetCoordinates.getName());
        jsonWriter.name("version");
        jsonWriter.value(targetCoordinates.getVersion());
        jsonWriter.endObject();

        jsonWriter.endObject();
    }

    private String relativeUrlTo(ModuleVersionIdentifier from, ModuleVersionIdentifier to) {
        // TODO - do not assume Maven layout
        StringBuilder path = new StringBuilder();
        path.append("../../");
        path.append(to.getName());
        path.append("/");
        path.append(to.getVersion());
        path.append("/");
        path.append(to.getName());
        path.append("-");
        path.append(to.getVersion());
        path.append(".module");
        return path.toString();
    }

    private void writeVariantHostedInThisModule(PublicationInternal publication, UsageContext variant, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(variant.getName());
        writeAttributes(variant.getAttributes(), jsonWriter);
        writeDependencies(variant, jsonWriter);
        writeDependencyConstraints(variant, jsonWriter);
        writeArtifacts(publication, variant, jsonWriter);

        jsonWriter.endObject();
    }

    private void writeAttributes(AttributeContainer attributes, JsonWriter jsonWriter) throws IOException {
        if (attributes.isEmpty()) {
            return;
        }
        jsonWriter.name("attributes");
        jsonWriter.beginObject();
        Map<String, Attribute<?>> sortedAttributes = new TreeMap<String, Attribute<?>>();
        for (Attribute<?> attribute : attributes.keySet()) {
            sortedAttributes.put(attribute.getName(), attribute);
        }
        for (Attribute<?> attribute : sortedAttributes.values()) {
            jsonWriter.name(attribute.getName());
            Object value = attributes.getAttribute(attribute);
            if (value instanceof Boolean) {
                Boolean b = (Boolean) value;
                jsonWriter.value(b);
            } else if (value instanceof String) {
                String s = (String) value;
                jsonWriter.value(s);
            } else if (value instanceof Named) {
                Named named = (Named) value;
                jsonWriter.value(named.getName());
            } else if (value instanceof Enum) {
                Enum<?> enumValue = (Enum<?>) value;
                jsonWriter.value(enumValue.name());
            } else {
                throw new IllegalArgumentException(String.format("Cannot write attribute %s with unsupported value %s of type %s.", attribute.getName(), value, value.getClass().getName()));
            }
        }
        jsonWriter.endObject();
    }

    private void writeArtifacts(PublicationInternal publication, UsageContext variant, JsonWriter jsonWriter) throws IOException {
        if (variant.getArtifacts().isEmpty()) {
            return;
        }
        jsonWriter.name("files");
        jsonWriter.beginArray();
        for (PublishArtifact artifact : variant.getArtifacts()) {
            writeArtifact(publication, artifact, jsonWriter);
        }
        jsonWriter.endArray();
    }

    private void writeArtifact(PublicationInternal publication, PublishArtifact artifact, JsonWriter jsonWriter) throws IOException {
        PublicationInternal.PublishedFile publishedFile = publication.getPublishedFile(artifact);

        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(publishedFile.getName());
        jsonWriter.name("url");
        jsonWriter.value(publishedFile.getUri());

        jsonWriter.name("size");
        jsonWriter.value(artifact.getFile().length());
        jsonWriter.name("sha1");
        jsonWriter.value(HashUtil.sha1(artifact.getFile()).asHexString());
        jsonWriter.name("md5");
        jsonWriter.value(HashUtil.createHash(artifact.getFile(), "md5").asHexString());

        jsonWriter.endObject();
    }

    private void writeDependencies(UsageContext variant, JsonWriter jsonWriter) throws IOException {
        if (variant.getDependencies().isEmpty()) {
            return;
        }
        jsonWriter.name("dependencies");
        jsonWriter.beginArray();
        for (ModuleDependency moduleDependency : variant.getDependencies()) {
            writeDependency(moduleDependency, jsonWriter);
        }
        jsonWriter.endArray();
    }

    private void writeDependencyConstraints(UsageContext variant, JsonWriter jsonWriter) throws IOException {
        if (variant.getDependencyConstraints().isEmpty()) {
            return;
        }
        jsonWriter.name("dependencyConstraints");
        jsonWriter.beginArray();
        for (DependencyConstraint dependencyConstraint : variant.getDependencyConstraints()) {
            writeDependency(dependencyConstraint, jsonWriter);
        }
        jsonWriter.endArray();
    }

    private void writeDependency(Dependency dependency, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        if (dependency instanceof ProjectDependency) {
            ProjectDependency projectDependency = (ProjectDependency) dependency;
            ModuleVersionIdentifier identifier = projectDependencyResolver.resolve(projectDependency);
            jsonWriter.name("group");
            jsonWriter.value(identifier.getGroup());
            jsonWriter.name("module");
            jsonWriter.value(identifier.getName());
            writeVersionConstraint(DefaultImmutableVersionConstraint.of(identifier.getVersion()), jsonWriter);
        } else {
            jsonWriter.name("group");
            jsonWriter.value(dependency.getGroup());
            jsonWriter.name("module");
            jsonWriter.value(dependency.getName());
            VersionConstraint vc;
            if (dependency instanceof ModuleVersionSelector) {
                vc = ((ExternalDependency) dependency).getVersionConstraint();
            } else if (dependency instanceof DependencyConstraint) {
                vc = ((DependencyConstraint) dependency).getVersionConstraint();
            } else {
                vc = DefaultImmutableVersionConstraint.of(Strings.nullToEmpty(dependency.getVersion()));
            }
            writeVersionConstraint(vc, jsonWriter);
        }
        if (dependency instanceof ModuleDependency) {
            writeExcludes((ModuleDependency) dependency, jsonWriter);
        }
        jsonWriter.endObject();
    }

    private void writeExcludes(ModuleDependency moduleDependency, JsonWriter jsonWriter) throws IOException {
        Set<ExcludeRule> excludeRules;
        if (!moduleDependency.isTransitive()) {
            excludeRules = Collections.<ExcludeRule>singleton(new DefaultExcludeRule(null, null));
        } else {
            excludeRules = moduleDependency.getExcludeRules();
        }
        if (excludeRules.isEmpty()) {
            return;
        }

        jsonWriter.name("excludes");
        jsonWriter.beginArray();
        for (ExcludeRule excludeRule : excludeRules) {
            jsonWriter.beginObject();
            jsonWriter.name("group");
            String group = GUtil.elvis(excludeRule.getGroup(), "*");
            jsonWriter.value(group);
            jsonWriter.name("module");
            String module = GUtil.elvis(excludeRule.getModule(), "*");
            jsonWriter.value(module);

            jsonWriter.endObject();
        }
        jsonWriter.endArray();
    }

    private static class ComponentData {
        private final ModuleVersionIdentifier coordinates;
        private final ImmutableAttributes attributes;

        private ComponentData(ModuleVersionIdentifier coordinates, ImmutableAttributes attributes) {
            this.coordinates = coordinates;
            this.attributes = attributes;
        }
    }
}
