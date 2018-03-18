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

package org.gradle.plugin.management.internal;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

public class DefaultPluginRequests implements PluginRequests {

    public static final PluginRequests EMPTY = new EmptyPluginRequests();

    private final List<PluginRequestInternal> requests;

    public DefaultPluginRequests(List<PluginRequestInternal> requests) {
        this.requests = requests;
    }

    @Override
    public boolean isEmpty() {
        return requests.isEmpty();
    }

    @Override
    public int size() {
        return requests.size();
    }

    @Override
    public Iterator<PluginRequestInternal> iterator() {
        return requests.iterator();
    }

    private static class EmptyPluginRequests implements PluginRequests {

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterator<PluginRequestInternal> iterator() {
            return Iterators.emptyIterator();
        }
    }
}
