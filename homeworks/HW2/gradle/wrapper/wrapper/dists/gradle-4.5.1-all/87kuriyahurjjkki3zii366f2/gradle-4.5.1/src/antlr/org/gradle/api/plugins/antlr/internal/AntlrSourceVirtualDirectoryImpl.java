/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.api.plugins.antlr.internal;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.SourceDirectorySetFactory;
import org.gradle.api.plugins.antlr.AntlrSourceVirtualDirectory;
import org.gradle.util.ConfigureUtil;

/**
 * The implementation of the {@link org.gradle.api.plugins.antlr.AntlrSourceVirtualDirectory} contract.
 */
public class AntlrSourceVirtualDirectoryImpl implements AntlrSourceVirtualDirectory {
    private final SourceDirectorySet antlr;

    public AntlrSourceVirtualDirectoryImpl(String parentDisplayName, SourceDirectorySetFactory sourceDirectorySetFactory) {
        final String displayName = parentDisplayName + " Antlr source";
        antlr = sourceDirectorySetFactory.create(displayName);
        antlr.getFilter().include("**/*.g");
        antlr.getFilter().include("**/*.g4");
    }

    @Override
    public SourceDirectorySet getAntlr() {
        return antlr;
    }

    @Override
    public AntlrSourceVirtualDirectory antlr(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getAntlr());
        return this;
    }

    @Override
    public AntlrSourceVirtualDirectory antlr(Action<? super SourceDirectorySet> configureAction) {
        configureAction.execute(getAntlr());
        return this;
    }
}
