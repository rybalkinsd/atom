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

package org.gradle.api.internal.changedetection.state;

import org.gradle.api.internal.changedetection.state.isolation.Isolatable;
import org.gradle.internal.Cast;

import javax.annotation.Nullable;

/**
 * A isolated immutable scalar value. Should only be used for immutable JVM provided or core Gradle types.
 *
 * @param <T>
 */
public abstract class AbstractIsolatableScalarValue<T> extends AbstractScalarValueSnapshot<T> implements Isolatable<T> {
    public AbstractIsolatableScalarValue(T value) {
        super(value);
    }

    @Override
    public T isolate() {
        return getValue();
    }

    @Nullable
    @Override
    public <S> Isolatable<S> coerce(Class<S> type) {
        if (type.isInstance(getValue())) {
            return Cast.uncheckedCast(this);
        }
        return null;
    }
}
