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
package org.gradle.api.distribution.internal;

import org.gradle.api.Action;
import org.gradle.api.distribution.Distribution;
import org.gradle.api.file.CopySpec;

/**
 * Allow user to declare a distribution.
 */
public class DefaultDistribution implements Distribution {
    private final String name;
    private String baseName;
    private final CopySpec contents;

    public DefaultDistribution(String name, CopySpec contents) {
        this.name = name;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public CopySpec getContents() {
        return contents;
    }

    public CopySpec contents(Action<? super CopySpec> action) {
        action.execute(contents);
        return contents;
    }
}
