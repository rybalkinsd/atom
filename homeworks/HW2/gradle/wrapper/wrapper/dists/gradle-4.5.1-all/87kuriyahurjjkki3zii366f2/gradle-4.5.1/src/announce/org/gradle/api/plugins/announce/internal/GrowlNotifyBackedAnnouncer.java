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

package org.gradle.api.plugins.announce.internal;

import org.gradle.api.Action;
import org.gradle.api.internal.ProcessOperations;
import org.gradle.internal.os.OperatingSystem;
import org.gradle.process.ExecSpec;

import java.io.File;

public class GrowlNotifyBackedAnnouncer extends Growl {
    private final ProcessOperations processOperations;
    private final IconProvider iconProvider;

    public GrowlNotifyBackedAnnouncer(ProcessOperations processOperations, IconProvider iconProvider) {
        this.processOperations = processOperations;
        this.iconProvider = iconProvider;
    }

    @Override
    public void send(final String title, final String message) {
        final File exe = OperatingSystem.current().findInPath("growlnotify");
        if (exe == null) {
            throw new AnnouncerUnavailableException("Could not find 'growlnotify' in path.");
        }

        processOperations.exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.executable(exe);
                execSpec.args("-m", message);
                File icon = iconProvider.getIcon(48, 48);
                if (icon != null) {
                    execSpec.args("--image", icon.getAbsolutePath());
                }
                execSpec.args("-t", title);
            }
        });
    }
}
