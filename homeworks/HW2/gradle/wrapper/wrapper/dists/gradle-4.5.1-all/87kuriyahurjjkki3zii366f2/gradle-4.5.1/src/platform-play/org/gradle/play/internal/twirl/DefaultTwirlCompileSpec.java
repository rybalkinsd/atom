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

package org.gradle.play.internal.twirl;

import org.gradle.api.internal.file.RelativeFile;
import org.gradle.api.tasks.compile.BaseForkOptions;
import org.gradle.language.twirl.TwirlImports;
import org.gradle.language.twirl.TwirlTemplateFormat;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class DefaultTwirlCompileSpec implements TwirlCompileSpec {
    private final Iterable<RelativeFile> sources;
    private final File destinationDir;
    private final Collection<TwirlTemplateFormat> userTemplateFormats;
    private final List<String> additionalImports;
    private BaseForkOptions forkOptions;
    private TwirlImports defaultImports;

    public DefaultTwirlCompileSpec(Iterable<RelativeFile> sources, File destinationDir, BaseForkOptions forkOptions, TwirlImports defaultImports, Collection<TwirlTemplateFormat> userTemplateFormats, List<String> additionalImports) {
        this.sources = sources;
        this.destinationDir = destinationDir;
        this.forkOptions = forkOptions;
        this.defaultImports = defaultImports;
        this.userTemplateFormats = userTemplateFormats;
        this.additionalImports = additionalImports;
    }

    @Override
    public TwirlImports getDefaultImports() {
        return defaultImports;
    }

    @Override
    public Collection<TwirlTemplateFormat> getUserTemplateFormats() {
        return userTemplateFormats;
    }

    @Override
    public File getDestinationDir() {
        return destinationDir;
    }

    @Override
    public Iterable<RelativeFile> getSources() {
        return sources;
    }

    @Override
    public BaseForkOptions getForkOptions() {
        return forkOptions;
    }

    @Override
    public List<String> getAdditionalImports() {
        return additionalImports;
    }
}
