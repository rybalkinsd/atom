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

package org.gradle.api.internal.changedetection.state.isolation;

import org.gradle.api.internal.changedetection.state.EnumValueSnapshot;
import org.gradle.internal.Cast;

import javax.annotation.Nullable;

/**
 * Isolates an Enum value and is a snapshot for that value.
 */
public class IsolatableEnumValueSnapshot extends EnumValueSnapshot implements Isolatable<Enum> {
    private final Enum<?> value;

    public IsolatableEnumValueSnapshot(Enum<?> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Enum isolate() {
        return value;
    }

    @Nullable
    @Override
    public <S> Isolatable<S> coerce(Class<S> type) {
        if (type.isAssignableFrom(value.getClass())) {
            return Cast.uncheckedCast(this);
        }
        if (type.isEnum() && type.getName().equals(value.getClass().getName())) {
            return Cast.uncheckedCast(new IsolatableEnumValueSnapshot(Enum.valueOf(type.asSubclass(Enum.class), value.name())));
        }
        return null;
    }
}
