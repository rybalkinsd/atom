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

package org.gradle.api.internal.tasks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import groovy.lang.Closure;
import org.gradle.api.Buildable;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Task;
import org.gradle.api.internal.provider.ProviderInternal;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.TaskDependency;
import org.gradle.api.tasks.TaskReference;
import org.gradle.internal.typeconversion.UnsupportedNotationException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.concurrent.Callable;

import static com.google.common.collect.Iterables.toArray;
import static org.gradle.util.GUtil.uncheckedCall;

/**
 * A task dependency which can have both mutable and immutable dependency values.
 *
 * If dependencies are known up-front, it is much more efficient to pass
 * them as immutable values to the {@link DefaultTaskDependency#DefaultTaskDependency(TaskResolver, ImmutableSet)}
 * constructor than to use the {@link #add(Object...)} method, as the former will
 * require less memory to store them.
 */
public class DefaultTaskDependency extends AbstractTaskDependency {
    private final ImmutableSet<Object> immutableValues;
    private Set<Object> mutableValues;
    private final TaskResolver resolver;

    public DefaultTaskDependency() {
        this(null);
    }

    public DefaultTaskDependency(TaskResolver resolver) {
        this(resolver, ImmutableSet.of());
    }

    public DefaultTaskDependency(TaskResolver resolver, ImmutableSet<Object> immutableValues) {
        this.resolver = resolver;
        this.immutableValues = immutableValues;
    }

    @Override
    public void visitDependencies(TaskDependencyResolveContext context) {
        Set<Object> mutableValues = getMutableValues();
        if (mutableValues.isEmpty() && immutableValues.isEmpty()) {
            return;
        }
        Deque<Object> queue = new ArrayDeque<Object>(mutableValues.size() + immutableValues.size());
        queue.addAll(immutableValues);
        queue.addAll(mutableValues);
        while (!queue.isEmpty()) {
            Object dependency = queue.removeFirst();
            if (dependency instanceof Buildable) {
                context.add(dependency);
            } else if (dependency instanceof Task) {
                context.add(dependency);
            } else if (dependency instanceof TaskDependency) {
                context.add(dependency);
            } else if (dependency instanceof Closure) {
                Closure closure = (Closure) dependency;
                Object closureResult = closure.call(context.getTask());
                if (closureResult != null) {
                    queue.addFirst(closureResult);
                }
            } else if (dependency instanceof RealizableTaskCollection) {
                RealizableTaskCollection realizableTaskCollection = (RealizableTaskCollection) dependency;
                realizableTaskCollection.realizeRuleTaskTypes();
                addAllFirst(queue, realizableTaskCollection.toArray());
            } else if (dependency instanceof List) {
                List<?> list = (List) dependency;
                if (list instanceof RandomAccess) {
                    for (int i = list.size() - 1; i >= 0; i--) {
                        queue.addFirst(list.get(i));
                    }
                } else {
                    ListIterator<?> iterator = list.listIterator(list.size());
                    while (iterator.hasPrevious()) {
                        Object item = iterator.previous();
                        queue.addFirst(item);
                    }
                }
            } else if (dependency instanceof Iterable) {
                Iterable<?> iterable = (Iterable) dependency;
                addAllFirst(queue, toArray(iterable, Object.class));
            } else if (dependency instanceof Map) {
                Map<?, ?> map = (Map) dependency;
                addAllFirst(queue, map.values().toArray());
            } else if (dependency instanceof Object[]) {
                Object[] array = (Object[]) dependency;
                addAllFirst(queue, array);
            } else if (dependency instanceof Callable) {
                Callable callable = (Callable) dependency;
                Object callableResult = uncheckedCall(callable);
                if (callableResult != null) {
                    queue.addFirst(callableResult);
                }
            } else if (resolver != null && dependency instanceof TaskReference) {
                context.add(resolver.resolveTask((TaskReference) dependency));
            } else if (resolver != null && dependency instanceof CharSequence) {
                context.add(resolver.resolveTask(dependency.toString()));
            } else if (dependency instanceof ProviderInternal) {
                if (dependency instanceof TaskDependencyContainer) {
                    ((TaskDependencyContainer) dependency).visitDependencies(context);
                    continue;
                }
                ProviderInternal providerInternal = (ProviderInternal) dependency;
                if (providerInternal.getType() == null || providerInternal.getType().equals(Provider.class)) {
                    queue.addFirst(providerInternal.get());
                    continue;
                }
                List<String> formats = new ArrayList<String>();
                formats.add("A RegularFileProperty");
                formats.add("A DirectoryProperty");
                throw new UnsupportedNotationException(dependency, String.format("Cannot convert Provider %s to a task.", dependency), null, formats);
            } else {
                List<String> formats = new ArrayList<String>();
                if (resolver != null) {
                    formats.add("A String or CharSequence task name or path");
                    formats.add("A TaskReference instance");
                }
                formats.add("A Task instance");
                formats.add("A Buildable instance");
                formats.add("A TaskDependency instance");
                formats.add("A RegularFileProperty or DirectoryProperty instance");
                formats.add("A Provider instance that returns any of these types");
                formats.add("A Closure instance that returns any of these types");
                formats.add("A Callable instance that returns any of these types");
                formats.add("An Iterable, Collection, Map or array instance that contains any of these types");
                throw new UnsupportedNotationException(dependency, String.format("Cannot convert %s to a task.", dependency), null, formats);
            }
        }
    }

    private static void addAllFirst(Deque<Object> queue, Object[] items) {
        for (int i = items.length - 1; i >= 0; i--) {
            queue.addFirst(items[i]);
        }
    }

    public Set<Object> getMutableValues() {
        if (mutableValues == null) {
            mutableValues = Sets.newHashSet();
        }
        return mutableValues;
    }

    public void setValues(Iterable<?> values) {
        getMutableValues().clear();
        for (Object value : values) {
            addValue(value);
        }
    }

    public DefaultTaskDependency add(Object... values) {
        for (Object value : values) {
            addValue(value);
        }
        return this;
    }

    private void addValue(Object dependency) {
        if (dependency == null) {
            throw new InvalidUserDataException("A dependency must not be empty");
        }
        getMutableValues().add(dependency);
    }
}
