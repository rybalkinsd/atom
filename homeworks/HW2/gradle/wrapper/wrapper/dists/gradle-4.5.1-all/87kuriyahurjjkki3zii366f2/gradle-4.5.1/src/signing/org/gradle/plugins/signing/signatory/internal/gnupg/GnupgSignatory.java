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
package org.gradle.plugins.signing.signatory.internal.gnupg;

import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.plugins.signing.signatory.SignatorySupport;
import org.gradle.process.ExecSpec;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Signatory that uses the gnupg cli program.
 * @since 4.5
 */
@Incubating
public class GnupgSignatory extends SignatorySupport {

    private static final Logger LOG = Logging.getLogger(GnupgSignatory.class);

    private final Project project;
    private final String name;

    private final String executable;
    private final boolean useLegacyGpg;
    private final File homeDir;
    private final File optionsFile;
    private final String keyName;
    private final String passphrase;

    public GnupgSignatory(Project project, String name, GnupgSettings settings) {
        this.project = project;
        this.name = name;
        this.executable = settings.getExecutable();
        this.useLegacyGpg = settings.getUseLegacyGpg();
        this.homeDir = settings.getHomeDir();
        this.optionsFile = settings.getOptionsFile();
        this.keyName = settings.getKeyName();
        this.passphrase = settings.getPassphrase();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sign(final InputStream input, final OutputStream output) {
        final List<String> arguments = buildArgumentList();
        LOG.info("Invoking {} with arguments: {}", executable, arguments);
        project.exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec spec) {
                spec.setExecutable(executable);
                spec.setArgs(arguments);
                spec.setStandardInput(input);
                spec.setStandardOutput(output);
            }
        });
    }

    private List<String> buildArgumentList() {
        final List<String> args = new ArrayList<String>();
        if (homeDir != null) {
            args.add("--homedir");
            args.add(homeDir.getAbsolutePath());
        }
        if (optionsFile != null) {
            args.add("--options");
            args.add(optionsFile.getAbsolutePath());
        }
        if (keyName != null) {
            args.add("--local-user");
            args.add(keyName);
        }
        if (passphrase != null) {
            if (useLegacyGpg) {
                args.add("--no-use-agent");
            } else {
                args.add("--pinentry-mode=loopback");
            }
            args.add("--passphrase");
            args.add(passphrase);
        } else {
            if (useLegacyGpg) {
                args.add("--use-agent");
            }
        }
        args.add("--no-tty");
        args.add("--batch");
        args.add("--detach-sign");
        return args;
    }

    @Override
    public String getKeyId() {
        return keyName;
    }

}
