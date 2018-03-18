/*
 * Copyright 2009 the original author or authors.
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

package org.gradle.api.internal;

import org.gradle.internal.reflect.DirectInstantiator;
import org.gradle.internal.reflect.Instantiator;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @see org.gradle.api.internal.AsmBackedClassGenerator.MixInExtensibleDynamicObject#MixInExtensibleDynamicObject(Object, Class, org.gradle.internal.metaobject.DynamicObject)
 * @see ClassGeneratorBackedInstantiator#newInstance(Class, Object...)
 */
public abstract class ThreadGlobalInstantiator {

    private static final ThreadLocal<Deque<Instantiator>> STORAGE = new ThreadLocal<Deque<Instantiator>>() {
        @Override
        protected Deque<Instantiator> initialValue() {
            return new ArrayDeque<Instantiator>();
        }
    };

    private static Deque<Instantiator> getStack() {
        return STORAGE.get();
    }

    public static Instantiator get() {
        Deque<Instantiator> stack = getStack();
        return stack.isEmpty() ? null : stack.peek();
    }

    public static void set(Instantiator instantiator) {
        Deque<Instantiator> stack = getStack();
        if (instantiator != null) {
            stack.push(instantiator);
        } else if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    public static Instantiator getOrCreate() {
        Instantiator instantiator = get();
        if (instantiator != null) {
            return instantiator;
        } else {
            return new ClassGeneratorBackedInstantiator(new AsmBackedClassGenerator(), DirectInstantiator.INSTANCE);
        }
    }
}
