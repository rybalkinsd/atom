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

package org.gradle.internal.metaobject;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.GeneratedClosure;

/**
 * Exposes methods for those properties whose value is a closure.
 *
 * TODO: use composition instead of inheritance
 */
public abstract class MixInClosurePropertiesAsMethodsDynamicObject extends CompositeDynamicObject {
    @Override
    public DynamicInvokeResult tryInvokeMethod(String name, Object... arguments) {
        DynamicInvokeResult result = super.tryInvokeMethod(name, arguments);
        if (result.isFound()) {
            return result;
        }

        DynamicInvokeResult propertyResult = tryGetProperty(name);
        if (propertyResult.isFound()) {
            Object property = propertyResult.getValue();
            if (property instanceof Closure) {
                Closure closure = (Closure) property;
                closure.setResolveStrategy(Closure.DELEGATE_FIRST);
                BeanDynamicObject dynamicObject = new BeanDynamicObject(closure);
                result = dynamicObject.tryInvokeMethod("doCall", arguments);
                if (!result.isFound() && !(closure instanceof GeneratedClosure)) {
                    return DynamicInvokeResult.found(closure.call(arguments));
                }
                return result;
            }
        }
        return DynamicInvokeResult.notFound();
    }
}
