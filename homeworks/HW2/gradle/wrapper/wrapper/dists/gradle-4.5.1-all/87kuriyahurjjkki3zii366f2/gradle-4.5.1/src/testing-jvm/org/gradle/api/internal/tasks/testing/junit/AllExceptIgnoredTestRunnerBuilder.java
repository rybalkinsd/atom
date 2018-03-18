/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.internal.tasks.testing.junit;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.builders.IgnoredBuilder;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class AllExceptIgnoredTestRunnerBuilder extends AllDefaultPossibilitiesBuilder {

    public AllExceptIgnoredTestRunnerBuilder() {
        super(true);
    }

    @Override
    protected IgnoredBuilder ignoredBuilder() {
        return new IgnoredIgnoredBuilder();
    }

    @Override
    protected JUnit4Builder junit4Builder() {
        return new FallbackJUnit4Builder();
    }

    private class FallbackJUnit4Builder extends JUnit4Builder {
        @Override
        public Runner runnerForClass(Class<?> testClass) throws Throwable {
            try {
                return new BlockJUnit4ClassRunner(testClass);
            } catch (Throwable t) {
                //failed to instantiate BlockJUnitRunner. try deprecated JUnitRunner (for JUnit < 4.5)
                try {
                    Class<Runner> runnerClass = (Class<Runner>) Thread.currentThread().getContextClassLoader().loadClass("org.junit.internal.runners.JUnit4ClassRunner");
                    final Constructor<Runner> constructor = runnerClass.getConstructor(Class.class);
                    return constructor.newInstance(testClass);
                } catch (Throwable e) {
                    LoggerFactory.getLogger(getClass()).warn("Unable to load JUnit4 runner to calculate Ignored test cases", e);
                }
            }
            return null;
        }
    }

    private class IgnoredIgnoredBuilder extends IgnoredBuilder {
        @Override
        public Runner runnerForClass(Class<?> testClass) {
            return null;
        }
    }
}
