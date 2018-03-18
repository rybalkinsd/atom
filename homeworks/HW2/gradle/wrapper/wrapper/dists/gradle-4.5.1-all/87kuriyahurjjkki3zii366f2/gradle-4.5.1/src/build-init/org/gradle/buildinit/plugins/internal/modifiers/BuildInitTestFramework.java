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

package org.gradle.buildinit.plugins.internal.modifiers;

import org.gradle.api.GradleException;

import java.util.ArrayList;
import java.util.List;

public enum BuildInitTestFramework {

    NONE,
    SPOCK,
    TESTNG;

    public static BuildInitTestFramework fromName(String name) {
        if (name == null) {
            return NONE;
        }
        for (BuildInitTestFramework testFramework : values()) {
            if (testFramework.getId().equals(name)) {
                return testFramework;
            }
        }
        throw new GradleException("The requested test framework '" + name + "' is not supported.");
    }

    public static List<String> listSupported() {
        List<String> result = new ArrayList<String>();
        for (BuildInitTestFramework testFramework : values()) {
            if (testFramework != NONE) {
                result.add(testFramework.getId());
            }
        }
        return result;
    }

    public String getId() {
        return name().toLowerCase();
    }
}
