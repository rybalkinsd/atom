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

package org.gradle.internal.logging;

import org.apache.commons.lang.StringUtils;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.configuration.ConsoleOutput;
import org.gradle.api.logging.configuration.LoggingConfiguration;
import org.gradle.api.logging.configuration.ShowStacktrace;
import org.gradle.api.logging.configuration.WarningMode;
import org.gradle.cli.CommandLineParser;
import org.gradle.cli.ParsedCommandLine;
import org.gradle.internal.buildoption.AbstractBuildOption;
import org.gradle.internal.buildoption.BuildOption;
import org.gradle.internal.buildoption.CommandLineOptionConfiguration;
import org.gradle.internal.buildoption.Origin;
import org.gradle.internal.buildoption.StringBuildOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LoggingConfigurationBuildOptions {

    private static List<BuildOption<LoggingConfiguration>> options;

    static {
        List<BuildOption<LoggingConfiguration>> options = new ArrayList<BuildOption<LoggingConfiguration>>();
        options.add(new LogLevelOption());
        options.add(new StacktraceOption());
        options.add(new ConsoleOption());
        options.add(new WarningsOption());
        LoggingConfigurationBuildOptions.options = Collections.unmodifiableList(options);
    }

    public static List<BuildOption<LoggingConfiguration>> get() {
        return options;
    }

    private LoggingConfigurationBuildOptions() {
    }

    public static class LogLevelOption extends AbstractBuildOption<LoggingConfiguration, CommandLineOptionConfiguration> {
        public static final String GRADLE_PROPERTY = "org.gradle.logging.level";
        public static final String QUIET_LONG_OPTION = "quiet";
        public static final String QUIET_SHORT_OPTION = "q";
        public static final String WARN_LONG_OPTION = "warn";
        public static final String WARN_SHORT_OPTION = "w";
        public static final String INFO_LONG_OPTION = "info";
        public static final String INFO_SHORT_OPTION = "i";
        public static final String DEBUG_LONG_OPTION = "debug";
        public static final String DEBUG_SHORT_OPTION = "d";
        private static final String[] ALL_SHORT_OPTIONS = new String[]{QUIET_SHORT_OPTION, WARN_SHORT_OPTION, INFO_SHORT_OPTION, DEBUG_SHORT_OPTION};

        public LogLevelOption() {
            super(GRADLE_PROPERTY, CommandLineOptionConfiguration.create(QUIET_LONG_OPTION, QUIET_SHORT_OPTION, "Log errors only."), CommandLineOptionConfiguration.create(WARN_LONG_OPTION, WARN_SHORT_OPTION, "Set log level to warn."), CommandLineOptionConfiguration.create(INFO_LONG_OPTION, INFO_SHORT_OPTION, "Set log level to info."), CommandLineOptionConfiguration.create(DEBUG_LONG_OPTION, DEBUG_SHORT_OPTION, "Log in debug mode (includes normal stacktrace)."));
        }

        @Override
        public void applyFromProperty(Map<String, String> properties, LoggingConfiguration settings) {
            String value = properties.get(gradleProperty);

            if (value != null) {
                LogLevel level = parseLogLevel(value);
                settings.setLogLevel(level);
            }
        }

        @Override
        public void configure(CommandLineParser parser) {
            for (CommandLineOptionConfiguration config : commandLineOptionConfigurations) {
                configureCommandLineOption(parser, config.getAllOptions(), config.getDescription(), config.isDeprecated(), config.isIncubating());
            }

            parser.allowOneOf(ALL_SHORT_OPTIONS);
        }

        @Override
        public void applyFromCommandLine(ParsedCommandLine options, LoggingConfiguration settings) {
            if (options.hasOption(QUIET_LONG_OPTION)) {
                settings.setLogLevel(LogLevel.QUIET);
            } else if (options.hasOption(WARN_LONG_OPTION)) {
                settings.setLogLevel(LogLevel.WARN);
            } else if (options.hasOption(INFO_LONG_OPTION)) {
                settings.setLogLevel(LogLevel.INFO);
            } else if (options.hasOption(DEBUG_LONG_OPTION)) {
                settings.setLogLevel(LogLevel.DEBUG);
            }
        }

        private LogLevel parseLogLevel(String value) {
            LogLevel logLevel = null;
            try {
                logLevel = LogLevel.valueOf(value.toUpperCase());
                if (logLevel == LogLevel.ERROR) {
                    throw new IllegalArgumentException("Log level cannot be set to 'ERROR'.");
                }
            } catch (IllegalArgumentException e) {
                Origin.forGradleProperty(GRADLE_PROPERTY).handleInvalidValue(value, "must be one of quiet, warn, lifecycle, info, or debug)");
            }
            return logLevel;
        }
    }

    public static class StacktraceOption extends AbstractBuildOption<LoggingConfiguration, CommandLineOptionConfiguration> {
        public static final String STACKTRACE_LONG_OPTION = "stacktrace";
        public static final String STACKTRACE_SHORT_OPTION = "s";
        public static final String FULL_STACKTRACE_LONG_OPTION = "full-stacktrace";
        public static final String FULL_STACKTRACE_SHORT_OPTION = "S";
        private static final String[] ALL_SHORT_OPTIONS = new String[]{STACKTRACE_SHORT_OPTION, FULL_STACKTRACE_SHORT_OPTION};

        public StacktraceOption() {
            super(null, CommandLineOptionConfiguration.create(STACKTRACE_LONG_OPTION, STACKTRACE_SHORT_OPTION, "Print out the stacktrace for all exceptions."), CommandLineOptionConfiguration.create(FULL_STACKTRACE_LONG_OPTION, FULL_STACKTRACE_SHORT_OPTION, "Print out the full (very verbose) stacktrace for all exceptions."));
        }

        @Override
        public void applyFromProperty(Map<String, String> properties, LoggingConfiguration settings) {
            // not supported
        }

        @Override
        public void configure(CommandLineParser parser) {
            for (CommandLineOptionConfiguration config : commandLineOptionConfigurations) {
                configureCommandLineOption(parser, config.getAllOptions(), config.getDescription(), config.isDeprecated(), config.isIncubating());
            }

            parser.allowOneOf(ALL_SHORT_OPTIONS);
        }

        @Override
        public void applyFromCommandLine(ParsedCommandLine options, LoggingConfiguration settings) {
            if (options.hasOption(STACKTRACE_LONG_OPTION)) {
                settings.setShowStacktrace(ShowStacktrace.ALWAYS);
            } else if (options.hasOption(FULL_STACKTRACE_LONG_OPTION)) {
                settings.setShowStacktrace(ShowStacktrace.ALWAYS_FULL);
            }
        }
    }

    public static class ConsoleOption extends StringBuildOption<LoggingConfiguration> {
        public static final String LONG_OPTION = "console";
        public static final String GRADLE_PROPERTY = "org.gradle.console";

        public ConsoleOption() {
            super(GRADLE_PROPERTY, CommandLineOptionConfiguration.create(LONG_OPTION, "Specifies which type of console output to generate. Values are 'plain', 'auto' (default), 'rich' or 'verbose'."));
        }

        @Override
        public void applyTo(String value, LoggingConfiguration settings, Origin origin) {
            String consoleValue = StringUtils.capitalize(value.toLowerCase(Locale.ENGLISH));
            try {
                ConsoleOutput consoleOutput = ConsoleOutput.valueOf(consoleValue);
                settings.setConsoleOutput(consoleOutput);
            } catch (IllegalArgumentException e) {
                origin.handleInvalidValue(value);
            }
        }
    }

    public static class WarningsOption extends StringBuildOption<LoggingConfiguration> {
        public static final String LONG_OPTION = "warning-mode";
        public static final String GRADLE_PROPERTY = "org.gradle.warning.mode";

        public WarningsOption() {
            super(GRADLE_PROPERTY, CommandLineOptionConfiguration.create(LONG_OPTION, "Specifies which mode of warnings to generate. Values are 'all', 'summary'(default) or 'no'"));
        }

        @Override
        public void applyTo(String value, LoggingConfiguration settings, final Origin origin) {
            try {
                settings.setWarningMode(WarningMode.valueOf(StringUtils.capitalize(value.toLowerCase(Locale.ENGLISH))));
            } catch (IllegalArgumentException e) {
                origin.handleInvalidValue(value);
            }
        }
    }
}
