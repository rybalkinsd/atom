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

package org.gradle.launcher.exec;

import org.gradle.StartParameter;
import org.gradle.api.internal.StartParameterInternal;
import org.gradle.initialization.BuildRequestContext;
import org.gradle.initialization.GradleLauncher;
import org.gradle.initialization.GradleLauncherFactory;
import org.gradle.initialization.RootBuildLifecycleListener;
import org.gradle.internal.event.ListenerManager;
import org.gradle.internal.invocation.BuildAction;
import org.gradle.internal.invocation.BuildActionRunner;
import org.gradle.internal.invocation.GradleBuildController;
import org.gradle.internal.jvm.UnsupportedJavaRuntimeException;
import org.gradle.internal.service.ServiceRegistry;

public class InProcessBuildActionExecuter implements BuildActionExecuter<BuildActionParameters> {
    private final GradleLauncherFactory gradleLauncherFactory;
    private final BuildActionRunner buildActionRunner;

    public InProcessBuildActionExecuter(BuildActionRunner buildActionRunner, GradleLauncherFactory gradleLauncherFactory) {
        this.gradleLauncherFactory = gradleLauncherFactory;
        this.buildActionRunner = buildActionRunner;
    }

    public Object execute(BuildAction action, BuildRequestContext buildRequestContext, BuildActionParameters actionParameters, ServiceRegistry contextServices) {
        GradleLauncher gradleLauncher = gradleLauncherFactory.newInstance(action.getStartParameter(), buildRequestContext, contextServices);
        GradleBuildController buildController = new GradleBuildController(gradleLauncher);
        checkDeprecations(action.getStartParameter());
        try {
            RootBuildLifecycleListener buildLifecycleListener = contextServices.get(ListenerManager.class).getBroadcaster(RootBuildLifecycleListener.class);
            buildLifecycleListener.afterStart();
            try {
                buildActionRunner.run(action, buildController);
                return buildController.getResult();
            } finally {
                buildLifecycleListener.beforeComplete();
            }
        } finally {
            buildController.stop();
        }
    }

    private void checkDeprecations(StartParameter startParameter) {
        UnsupportedJavaRuntimeException.javaDeprecationWarning();

        // This must be done here because DeprecationLogger needs to be initialized properly
        if (startParameter instanceof StartParameterInternal) {
            StartParameterInternal.class.cast(startParameter).checkDeprecation();
        }
    }
}
