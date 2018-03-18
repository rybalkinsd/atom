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

package org.gradle.play.internal.routes;

import java.util.Arrays;

abstract class DefaultVersionedRoutesCompilerAdapter implements VersionedRoutesCompilerAdapter {
    private static final Iterable<String> SHARED_PACKAGES = Arrays.asList("play.router", "scala.collection", "scala.collection.mutable", "scala.util.matching", "play.routes.compiler");
    private final String playVersion;
    private final String scalaVersion;

    public DefaultVersionedRoutesCompilerAdapter(String playVersion, String scalaVersion) {
        this.playVersion = playVersion;
        this.scalaVersion = scalaVersion;
    }

    protected boolean isGenerateRefReverseRouter() {
        return false;
    }

    @Override
    public String getDependencyNotation() {
        return "com.typesafe.play:routes-compiler_" + scalaVersion + ":" + playVersion;
    }

    @Override
    public Iterable<String> getClassLoaderPackages() {
        return SHARED_PACKAGES;
    }
}
