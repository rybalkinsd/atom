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

package org.gradle.launcher.cli;

import org.gradle.api.internal.StartParameterInternal;
import org.gradle.cli.AbstractCommandLineConverter;
import org.gradle.cli.CommandLineArgumentException;
import org.gradle.cli.CommandLineParser;
import org.gradle.cli.ParsedCommandLine;
import org.gradle.cli.SystemPropertiesCommandLineConverter;
import org.gradle.initialization.DefaultCommandLineConverter;
import org.gradle.initialization.LayoutCommandLineConverter;
import org.gradle.initialization.layout.BuildLayoutFactory;
import org.gradle.launcher.cli.converter.DaemonCommandLineConverter;
import org.gradle.launcher.cli.converter.LayoutToPropertiesConverter;
import org.gradle.launcher.cli.converter.PropertiesToDaemonParametersConverter;
import org.gradle.launcher.cli.converter.PropertiesToStartParameterConverter;
import org.gradle.launcher.daemon.configuration.DaemonParameters;

import java.util.HashMap;
import java.util.Map;

public class ParametersConverter extends AbstractCommandLineConverter<Parameters> {

    private final LayoutCommandLineConverter layoutConverter;

    private final SystemPropertiesCommandLineConverter propertiesConverter;
    private final LayoutToPropertiesConverter layoutToPropertiesConverter;

    private final PropertiesToStartParameterConverter propertiesToStartParameterConverter;
    private final DefaultCommandLineConverter commandLineConverter;

    private final DaemonCommandLineConverter daemonConverter;
    private final PropertiesToDaemonParametersConverter propertiesToDaemonParametersConverter;

    ParametersConverter(LayoutCommandLineConverter layoutConverter,
                        SystemPropertiesCommandLineConverter propertiesConverter,
                        LayoutToPropertiesConverter layoutToPropertiesConverter,
                        PropertiesToStartParameterConverter propertiesToStartParameterConverter,
                        DefaultCommandLineConverter commandLineConverter,
                        DaemonCommandLineConverter daemonConverter,
                        PropertiesToDaemonParametersConverter propertiesToDaemonParametersConverter) {
        this.layoutConverter = layoutConverter;
        this.propertiesConverter = propertiesConverter;
        this.layoutToPropertiesConverter = layoutToPropertiesConverter;
        this.propertiesToStartParameterConverter = propertiesToStartParameterConverter;
        this.commandLineConverter = commandLineConverter;
        this.daemonConverter = daemonConverter;
        this.propertiesToDaemonParametersConverter = propertiesToDaemonParametersConverter;
    }

    public ParametersConverter(BuildLayoutFactory buildLayoutFactory) {
        this(new LayoutCommandLineConverter(),
            new SystemPropertiesCommandLineConverter(),
            new LayoutToPropertiesConverter(buildLayoutFactory),
            new PropertiesToStartParameterConverter(),
            new DefaultCommandLineConverter(),
            new DaemonCommandLineConverter(),
            new PropertiesToDaemonParametersConverter());
    }

    @Override
    public Parameters convert(ParsedCommandLine args, Parameters target) throws CommandLineArgumentException {
        layoutConverter.convert(args, target.getLayout());

        Map<String, String> properties = new HashMap<String, String>();
        layoutToPropertiesConverter.convert(target.getLayout(), properties);
        propertiesConverter.convert(args, properties);

        propertiesToStartParameterConverter.convert(properties, (StartParameterInternal) target.getStartParameter());
        commandLineConverter.convert(args, (StartParameterInternal) target.getStartParameter());

        DaemonParameters daemonParameters = new DaemonParameters(target.getLayout(), target.getStartParameter().getSystemPropertiesArgs());
        propertiesToDaemonParametersConverter.convert(properties, daemonParameters);
        daemonConverter.convert(args, daemonParameters);
        target.setDaemonParameters(daemonParameters);

        return target;
    }

    @Override
    public void configure(CommandLineParser parser) {
        commandLineConverter.configure(parser);
        daemonConverter.configure(parser);
    }
}
