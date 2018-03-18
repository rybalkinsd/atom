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

package org.gradle.internal.reflect;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Distinguishes "get" getters, "is" getters and setters from non-property methods.
 *
 * Generally follows the JavaBean conventions, with 2 exceptions: is methods can return `Boolean` (in addition to `boolean`) and setter methods can return non-void values.
 *
 * This is essentially a superset of the conventions supported by Java, Groovy and Kotlin.
 */
public enum PropertyAccessorType {
    IS_GETTER(2) {
        @Override
        public Type propertyTypeFor(Method method) {
            return method.getGenericReturnType();
        }
    },

    GET_GETTER(3) {
        @Override
        public Type propertyTypeFor(Method method) {
            return method.getGenericReturnType();
        }
    },

    SETTER(3) {
        @Override
        public Type propertyTypeFor(Method method) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("Setter method should take one parameter: " + method);
            }
            return parameterTypes[0];
        }
    };

    private final int prefixLength;

    PropertyAccessorType(int prefixLength) {
        this.prefixLength = prefixLength;
    }

    public String propertyNameFor(Method method) {
        return propertyNameFor(method.getName());
    }

    public String propertyNameFor(String methodName) {
        String methodNamePrefixRemoved = methodName.substring(prefixLength);
        return Introspector.decapitalize(methodNamePrefixRemoved);
    }

    public abstract Type propertyTypeFor(Method method);

    public static PropertyAccessorType of(Method method) {
        if (isStatic(method)) {
            return null;
        }
        String methodName = method.getName();
        if (!hasVoidReturnType(method) && takesNoParameter(method)) {
            if (isGetGetterName(methodName)) {
                return GET_GETTER;
            }
            // is method that returns Boolean is not a getter according to JavaBeans, but include it for compatibility with Groovy
            if (isIsGetterName(methodName) && (method.getReturnType().equals(Boolean.TYPE) || method.getReturnType().equals(Boolean.class))) {
                return IS_GETTER;
            }
        }
        if (takesSingleParameter(method) && isSetterName(methodName)) {
            return SETTER;
        }
        return null;
    }

    public static PropertyAccessorType fromName(String methodName) {
        if (isGetGetterName(methodName)) {
            return GET_GETTER;
        }
        if (isIsGetterName(methodName)) {
            return IS_GETTER;
        }
        if (isSetterName(methodName)) {
            return SETTER;
        }
        return null;
    }

    private static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean hasVoidReturnType(Method method) {
        return void.class.equals(method.getReturnType());
    }

    public static boolean takesNoParameter(Method method) {
        return method.getParameterTypes().length == 0;
    }

    public static boolean takesSingleParameter(Method method) {
        return method.getParameterTypes().length == 1;
    }

    private static boolean isGetGetterName(String methodName) {
        return methodName.startsWith("get") && methodName.length() > 3;
    }

    private static boolean isIsGetterName(String methodName) {
        return methodName.startsWith("is") && methodName.length() > 2;
    }

    private static boolean isSetterName(String methodName) {
        return methodName.startsWith("set") && methodName.length() > 3;
    }

    public static boolean hasGetter(Collection<PropertyAccessorType> accessorTypes) {
        return accessorTypes.contains(GET_GETTER) || accessorTypes.contains(IS_GETTER);
    }

    public static boolean hasSetter(Collection<PropertyAccessorType> accessorTypes) {
        return accessorTypes.contains(SETTER);
    }
}
