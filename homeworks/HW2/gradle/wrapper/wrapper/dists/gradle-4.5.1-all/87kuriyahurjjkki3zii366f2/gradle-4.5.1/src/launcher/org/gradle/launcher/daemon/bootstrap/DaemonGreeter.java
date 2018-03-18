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

package org.gradle.launcher.daemon.bootstrap;

import org.gradle.api.GradleException;
import org.gradle.api.internal.DocumentationRegistry;
import org.gradle.launcher.daemon.diagnostics.DaemonStartupInfo;
import org.gradle.launcher.daemon.logging.DaemonMessages;

public class DaemonGreeter {
    private final DocumentationRegistry documentationRegistry;

    public DaemonGreeter(DocumentationRegistry documentationRegistry) {
        this.documentationRegistry = documentationRegistry;
    }

    public DaemonStartupInfo parseDaemonOutput(String output) {
        DaemonStartupCommunication startupCommunication = new DaemonStartupCommunication();
        if (!startupCommunication.containsGreeting(output)) {
            throw new GradleException(prepareMessage(output));
        }
        String[] lines = output.split("\n");
        //Assuming that the diagnostics were printed out to the last line. It's not bullet-proof but seems to be doing fine.
        String lastLine = lines[lines.length-1];
        return startupCommunication.readDiagnostics(lastLine);
    }

    private String prepareMessage(String output) {
        StringBuilder sb = new StringBuilder();
        sb.append(DaemonMessages.UNABLE_TO_START_DAEMON);
        sb.append("\nThis problem might be caused by incorrect configuration of the daemon.");
        sb.append("\nFor example, an unrecognized jvm option is used.");
        sb.append("\nPlease refer to the user guide chapter on the daemon at ");
        sb.append(documentationRegistry.getDocumentationFor("gradle_daemon"));
        sb.append("\nPlease read the following process output to find out more:");
        sb.append("\n-----------------------\n");
        sb.append(output);
        return sb.toString();
    }
}