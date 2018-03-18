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

package org.gradle.language.internal;

import com.google.common.collect.ImmutableSet;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.internal.provider.AbstractProvider;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.specs.Spec;
import org.gradle.internal.ImmutableActionSet;
import org.gradle.language.BinaryCollection;
import org.gradle.language.BinaryProvider;
import org.gradle.util.ConfigureUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO - error messages
// TODO - display names for this container and the Provider implementations
public class DefaultBinaryCollection<T extends SoftwareComponent> implements BinaryCollection<T> {
    private enum State {
        Collecting, Realizing, Finalized
    }

    private final Class<T> elementType;
    private final ProviderFactory providerFactory;
    private final Set<T> elements = new LinkedHashSet<T>();
    private List<SingleElementProvider<?>> pending = new LinkedList<SingleElementProvider<?>>();
    private State state = State.Collecting;
    private ImmutableActionSet<T> knownActions = ImmutableActionSet.empty();
    private ImmutableActionSet<T> configureActions = ImmutableActionSet.empty();
    private ImmutableActionSet<T> finalizeActions = ImmutableActionSet.empty();

    @Inject
    public DefaultBinaryCollection(Class<T> elementType, ProviderFactory providerFactory) {
        this.elementType = elementType;
        this.providerFactory = providerFactory;
    }

    @Override
    public <S> BinaryProvider<S> get(final Class<S> type, final Spec<? super S> spec) {
        SingleElementProvider<S> provider = new SingleElementProvider<S>(type, spec);
        if (state == State.Collecting) {
            pending.add(provider);
        } else {
            provider.selectNow();
        }
        return provider;
    }

    @Override
    public BinaryProvider<T> getByName(final String name) {
        return get(elementType, new Spec<T>() {
            @Override
            public boolean isSatisfiedBy(T element) {
                return element.getName().equals(name);
            }
        });
    }

    @Override
    public BinaryProvider<T> get(Spec<? super T> spec) {
        return get(elementType, spec);
    }

    @Override
    public void whenElementKnown(Action<? super T> action) {
        if (state != State.Collecting) {
            throw new IllegalStateException("Cannot add actions to this collection as it has already been realized.");
        }
        knownActions = knownActions.add(action);
        for (T element : elements) {
            action.execute(element);
        }
    }

    @Override
    public <S> void whenElementKnown(final Class<S> type, final Action<? super S> action) {
        whenElementKnown(new TypeFilteringAction<T, S>(type, action));
    }

    @Override
    public void whenElementFinalized(Action<? super T> action) {
        if (state == State.Finalized) {
            for (T element : elements) {
                action.execute(element);
            }
        } else {
            finalizeActions = finalizeActions.add(action);
        }
    }

    @Override
    public <S> void whenElementFinalized(Class<S> type, Action<? super S> action) {
        whenElementFinalized(new TypeFilteringAction<T, S>(type, action));
    }

    @Override
    public void configureEach(Action<? super T> action) {
        if (state != State.Collecting) {
            throw new IllegalStateException("Cannot add actions to this collection as it has already been realized.");
        }
        configureActions = configureActions.add(action);
    }

    @Override
    public <S> void configureEach(Class<S> type, Action<? super S> action) {
        configureEach(new TypeFilteringAction<T, S>(type, action));
    }

    public void add(T element) {
        if (state != State.Collecting) {
            throw new IllegalStateException("Cannot add an element to this collection as it has already been realized.");
        }
        elements.add(element);
        knownActions.execute(element);
    }

    public void realizeNow() {
        if (state != State.Collecting) {
            throw new IllegalStateException("Cannot realize this collection as it has already been realized.");
        }
        state = State.Realizing;
        knownActions = ImmutableActionSet.empty();

        for (SingleElementProvider<?> provider : pending) {
            provider.selectNow();
        }
        pending = null;

        for (T element : elements) {
            configureActions.execute(element);
        }
        configureActions = ImmutableActionSet.empty();
        state = State.Finalized;
        for (T element : elements) {
            finalizeActions.execute(element);
        }
        finalizeActions = ImmutableActionSet.empty();

    }

    @Override
    public Set<T> get() {
        if (state != State.Finalized) {
            throw new IllegalStateException("Cannot query the elements of this container as the elements have not been created yet.");
        }
        return ImmutableSet.copyOf(elements);
    }

    private class SingleElementProvider<S> extends AbstractProvider<S> implements BinaryProvider<S> {
        private final Class<S> type;
        private Spec<? super S> spec;
        private S match;
        private boolean ambiguous;

        SingleElementProvider(Class<S> type, Spec<? super S> spec) {
            this.type = type;
            this.spec = spec;
        }

        void selectNow() {
            for (T element : elements) {
                if (type.isInstance(element) && spec.isSatisfiedBy(type.cast(element))) {
                    if (match != null) {
                        ambiguous = true;
                        match = null;
                        break;
                    }
                    match = type.cast(element);
                }
            }
            spec = null;
        }

        @Nullable
        @Override
        public Class<S> getType() {
            return type;
        }

        // Mix in some Groovy DSL support. Should decorate instead
        public void configure(Closure<?> closure) {
            configure(ConfigureUtil.<S>configureUsing(closure));
        }

        @Override
        public void configure(final Action<? super S> action) {
            configureEach(new Action<T>() {
                @Override
                public void execute(T t) {
                    if (match == t) {
                        action.execute(match);
                    }
                }
            });
        }

        @Nullable
        @Override
        public S getOrNull() {
            if (ambiguous) {
                throw new IllegalStateException("Found multiple elements");
            }
            return match;
        }
    }

    private static class TypeFilteringAction<T extends SoftwareComponent, S> implements Action<T> {
        private final Class<S> type;
        private final Action<? super S> action;

        TypeFilteringAction(Class<S> type, Action<? super S> action) {
            this.type = type;
            this.action = action;
        }

        @Override
        public void execute(T t) {
            if (type.isInstance(t)) {
                action.execute(type.cast(t));
            }
        }
    }
}
