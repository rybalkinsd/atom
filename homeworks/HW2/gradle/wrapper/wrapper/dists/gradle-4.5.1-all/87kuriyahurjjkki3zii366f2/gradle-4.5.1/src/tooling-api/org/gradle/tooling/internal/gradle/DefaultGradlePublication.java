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

package org.gradle.tooling.internal.gradle;

import com.google.common.base.Objects;
import org.gradle.tooling.model.GradleModuleVersion;

import java.io.File;
import java.io.Serializable;

public class DefaultGradlePublication implements Serializable, GradleProjectIdentity {
    private GradleModuleVersion id;
    private DefaultProjectIdentifier projectIdentifier;

    public GradleModuleVersion getId() {
        return id;
    }

    public DefaultGradlePublication setId(GradleModuleVersion id) {
        this.id = id;
        return this;
    }

    public DefaultProjectIdentifier getProjectIdentifier() {
        return projectIdentifier;
    }

    @Override
    public String getProjectPath() {
        return projectIdentifier.getProjectPath();
    }

    @Override
    public File getRootDir() {
        return projectIdentifier.getBuildIdentifier().getRootDir();
    }

    public DefaultGradlePublication setProjectIdentifier(DefaultProjectIdentifier projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
        return this;
    }

    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
