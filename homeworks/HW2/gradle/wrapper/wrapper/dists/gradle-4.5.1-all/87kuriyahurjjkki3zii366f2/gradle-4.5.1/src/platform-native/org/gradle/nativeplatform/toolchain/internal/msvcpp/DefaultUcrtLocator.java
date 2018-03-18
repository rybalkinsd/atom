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
package org.gradle.nativeplatform.toolchain.internal.msvcpp;

import java.io.File;

import org.gradle.util.VersionNumber;

import net.rubygrapefruit.platform.WindowsRegistry;

public class DefaultUcrtLocator extends AbstractWindowsKitComponentLocator<Ucrt> implements UcrtLocator {
    private static final String DISPLAY_NAME = "Universal C Runtime";
    private static final String COMPONENT_NAME = "ucrt";

    public DefaultUcrtLocator(WindowsRegistry windowsRegistry) {
        super(windowsRegistry);
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    boolean isValidComponentBaseDir(File baseDir) {
        // Nothing special to check for UCRT
        return true;
    }

    @Override
    boolean isValidComponentIncludeDir(File includeDir) {
        return new File(includeDir, "io.h").exists();
    }

    @Override
    boolean isValidComponentLibDir(File libDir) {
        for (String platform : PLATFORMS) {
            if (!new File(libDir, platform + "/libucrt.lib").exists()) {
                return false;
            }
        }
        return true;
    }

    public Ucrt newComponent(File baseDir, VersionNumber version, DiscoveryType discoveryType) {
        return new Ucrt(baseDir, version, getVersionedDisplayName(version, discoveryType));
    }
}
