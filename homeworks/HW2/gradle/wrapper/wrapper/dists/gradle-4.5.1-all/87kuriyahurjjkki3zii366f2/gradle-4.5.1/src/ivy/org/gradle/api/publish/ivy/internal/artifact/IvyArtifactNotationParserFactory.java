/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.api.publish.ivy.internal.artifact;

import org.apache.commons.lang.StringUtils;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.internal.tasks.TaskDependencyContainer;
import org.gradle.api.publish.ivy.IvyArtifact;
import org.gradle.api.publish.ivy.internal.publisher.IvyPublicationIdentity;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.internal.Factory;
import org.gradle.internal.exceptions.DiagnosticsVisitor;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.internal.typeconversion.*;

import java.io.File;
import java.util.concurrent.Callable;

public class IvyArtifactNotationParserFactory implements Factory<NotationParser<Object, IvyArtifact>> {
    private final Instantiator instantiator;
    private final FileResolver fileResolver;
    private final IvyPublicationIdentity publicationIdentity;

    public IvyArtifactNotationParserFactory(Instantiator instantiator, FileResolver fileResolver, IvyPublicationIdentity publicationIdentity) {
        this.instantiator = instantiator;
        this.fileResolver = fileResolver;
        this.publicationIdentity = publicationIdentity;
    }

    public NotationParser<Object, IvyArtifact> create() {
        FileNotationConverter fileNotationConverter = new FileNotationConverter(fileResolver);
        ArchiveTaskNotationConverter archiveTaskNotationConverter = new ArchiveTaskNotationConverter();
        PublishArtifactNotationConverter publishArtifactNotationConverter = new PublishArtifactNotationConverter();

        NotationParser<Object, IvyArtifact> sourceNotationParser = NotationParserBuilder
                .toType(IvyArtifact.class)
                .converter(archiveTaskNotationConverter)
                .converter(publishArtifactNotationConverter)
                .converter(fileNotationConverter)
                .toComposite();

        IvyArtifactMapNotationConverter ivyArtifactMapNotationConverter = new IvyArtifactMapNotationConverter(sourceNotationParser);

        NotationParserBuilder<IvyArtifact> parserBuilder = NotationParserBuilder
                .toType(IvyArtifact.class)
                .converter(archiveTaskNotationConverter)
                .converter(publishArtifactNotationConverter)
                .converter(ivyArtifactMapNotationConverter)
                .converter(fileNotationConverter);

        return parserBuilder.toComposite();
    }

    private DefaultIvyArtifact createDefaultIvyArtifact(File file, String extension, String type, String classifier) {
        DefaultIvyArtifact ivyArtifact = instantiator.newInstance(
                DefaultIvyArtifact.class,
                file, null, extension, type, classifier
        );
        new DslObject(ivyArtifact).getConventionMapping().map("name", new Callable<String>() {
            public String call() throws Exception {
                return publicationIdentity.getModule();
            }
        });
        return ivyArtifact;
    }

    private class ArchiveTaskNotationConverter extends TypedNotationConverter<AbstractArchiveTask, IvyArtifact> {
        private ArchiveTaskNotationConverter() {
            super(AbstractArchiveTask.class);
        }

        @Override
        protected IvyArtifact parseType(AbstractArchiveTask archiveTask) {
            DefaultIvyArtifact ivyArtifact = createDefaultIvyArtifact(
                    archiveTask.getArchivePath(), archiveTask.getExtension(), archiveTask.getExtension(), archiveTask.getClassifier());
            ivyArtifact.builtBy(archiveTask);
            return ivyArtifact;
        }
    }

    private class PublishArtifactNotationConverter extends TypedNotationConverter<PublishArtifact, IvyArtifact> {
        private PublishArtifactNotationConverter() {
            super(PublishArtifact.class);
        }

        @Override
        protected IvyArtifact parseType(PublishArtifact publishArtifact) {
            DefaultIvyArtifact ivyArtifact = createDefaultIvyArtifact(
                    publishArtifact.getFile(), publishArtifact.getExtension(), publishArtifact.getType(), publishArtifact.getClassifier());
            ivyArtifact.builtBy(publishArtifact.getBuildDependencies());
            return ivyArtifact;
        }
    }

    private class FileNotationConverter implements NotationConverter<Object, IvyArtifact> {
        private final NotationParser<Object, File> fileResolverNotationParser;

        private FileNotationConverter(FileResolver fileResolver) {
            this.fileResolverNotationParser = fileResolver.asNotationParser();
        }

        public void convert(Object notation, NotationConvertResult<? super IvyArtifact> result) throws TypeConversionException {
            File file = fileResolverNotationParser.parseNotation(notation);
            IvyArtifact ivyArtifact = parseFile(file);
            if (notation instanceof TaskDependencyContainer) {
                ivyArtifact.builtBy(notation);
            }
            result.converted(ivyArtifact);
        }

        protected IvyArtifact parseFile(File file) {
            String extension = StringUtils.substringAfterLast(file.getName(), ".");
            return createDefaultIvyArtifact(file, extension, extension, null);
        }

        @Override
        public void describe(DiagnosticsVisitor visitor) {
            fileResolverNotationParser.describe(visitor);
        }
    }

    private class IvyArtifactMapNotationConverter extends MapNotationConverter<IvyArtifact> {
        private final NotationParser<Object, IvyArtifact> sourceNotationParser;

        private IvyArtifactMapNotationConverter(NotationParser<Object, IvyArtifact> sourceNotationParser) {
            this.sourceNotationParser = sourceNotationParser;
        }

        protected IvyArtifact parseMap(@MapKey("source") Object source) {
            return sourceNotationParser.parseNotation(source);
        }

        @Override
        public void describe(DiagnosticsVisitor visitor) {
            visitor.candidate("Maps containing a 'source' entry").example("[source: '/path/to/file', extension: 'zip']");
        }
    }
}
