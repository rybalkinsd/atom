/*
 * Copyright 2016 the original author or authors.
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
package org.gradle.internal.buildevents;

import org.gradle.BuildAdapter;
import org.gradle.BuildResult;
import org.gradle.api.Action;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.configuration.LoggingConfiguration;
import org.gradle.api.logging.configuration.ShowStacktrace;
import org.gradle.execution.MultipleBuildFailures;
import org.gradle.initialization.BuildClientMetaData;
import org.gradle.initialization.StartParameterBuildOptions;
import org.gradle.internal.exceptions.FailureResolutionAware;
import org.gradle.internal.exceptions.LocationAwareException;
import org.gradle.internal.logging.LoggingConfigurationBuildOptions;
import org.gradle.internal.logging.text.BufferingStyledTextOutput;
import org.gradle.internal.logging.text.LinePrefixingStyledTextOutput;
import org.gradle.internal.logging.text.StyledTextOutput;
import org.gradle.internal.logging.text.StyledTextOutputFactory;
import org.gradle.util.GUtil;
import org.gradle.util.TreeVisitor;

import java.util.List;

import static org.gradle.internal.logging.text.StyledTextOutput.Style.*;

/**
 * A {@link org.gradle.BuildListener} which reports the build exception, if any.
 */
public class BuildExceptionReporter extends BuildAdapter implements Action<Throwable> {
    private enum ExceptionStyle {
        NONE, FULL
    }

    private final StyledTextOutputFactory textOutputFactory;
    private final LoggingConfiguration loggingConfiguration;
    private final BuildClientMetaData clientMetaData;

    public BuildExceptionReporter(StyledTextOutputFactory textOutputFactory, LoggingConfiguration loggingConfiguration, BuildClientMetaData clientMetaData) {
        this.textOutputFactory = textOutputFactory;
        this.loggingConfiguration = loggingConfiguration;
        this.clientMetaData = clientMetaData;
    }

    public void buildFinished(BuildResult result) {
        Throwable failure = result.getFailure();
        if (failure == null) {
            return;
        }

        execute(failure);
    }

    public void execute(Throwable failure) {
        if (failure instanceof MultipleBuildFailures) {
            renderMultipleBuildExceptions((MultipleBuildFailures) failure);
            return;
        }

        renderSingleBuildException(failure);
    }

    private void renderMultipleBuildExceptions(MultipleBuildFailures multipleFailures) {
        List<? extends Throwable> causes = multipleFailures.getCauses();

        StyledTextOutput output = textOutputFactory.create(BuildExceptionReporter.class, LogLevel.ERROR);
        output.println();
        output.withStyle(Failure).format("FAILURE: Build completed with %s failures.", causes.size());
        output.println();

        for (int i = 0; i < causes.size(); i++) {
            Throwable cause = causes.get(i);
            FailureDetails details = constructFailureDetails("Task", cause);

            output.println();
            output.withStyle(Failure).format("%s: ", i + 1);
            details.summary.writeTo(output.withStyle(Failure));
            output.println();
            output.text("-----------");

            writeFailureDetails(output, details);

            output.println("==============================================================================");
        }
        writeGeneralTips(output);
    }

    private void renderSingleBuildException(Throwable failure) {
        StyledTextOutput output = textOutputFactory.create(BuildExceptionReporter.class, LogLevel.ERROR);
        FailureDetails details = constructFailureDetails("Build", failure);

        output.println();
        output.withStyle(Failure).text("FAILURE: ");
        details.summary.writeTo(output.withStyle(Failure));
        output.println();

        writeFailureDetails(output, details);

        writeGeneralTips(output);
    }

    private FailureDetails constructFailureDetails(String granularity, Throwable failure) {
        FailureDetails details = new FailureDetails(failure);
        reportBuildFailure(granularity, failure, details);
        return details;
    }

    private void reportBuildFailure(String granularity, Throwable failure, FailureDetails details) {
        if (loggingConfiguration.getShowStacktrace() != ShowStacktrace.INTERNAL_EXCEPTIONS) {
            details.exceptionStyle = ExceptionStyle.FULL;
        }

        formatGenericFailure(granularity, failure, details);
    }

    private void formatGenericFailure(String granularity, Throwable failure, final FailureDetails details) {
        details.summary.format("%s failed with an exception.", granularity);

        fillInFailureResolution(details);

        if (failure instanceof LocationAwareException) {
            final LocationAwareException scriptException = (LocationAwareException) failure;
            details.failure = scriptException.getCause();
            if (scriptException.getLocation() != null) {
                details.location.text(scriptException.getLocation());
            }
            scriptException.visitReportableCauses(new TreeVisitor<Throwable>() {
                int depth;

                @Override
                public void node(final Throwable node) {
                    if (node == scriptException) {
                        details.details.text(getMessage(scriptException.getCause()));
                    } else {
                        final LinePrefixingStyledTextOutput output = getLinePrefixingStyledTextOutput();
                        output.text(getMessage(node));
                    }
                }

                @Override
                public void startChildren() {
                    depth++;
                }

                @Override
                public void endChildren() {
                    depth--;
                }

                private LinePrefixingStyledTextOutput getLinePrefixingStyledTextOutput() {
                    details.details.format("%n");
                    StringBuilder prefix = new StringBuilder();
                    for (int i = 1; i < depth; i++) {
                        prefix.append("   ");
                    }
                    details.details.text(prefix);
                    prefix.append("  ");
                    details.details.style(Info).text("> ").style(Normal);

                    return new LinePrefixingStyledTextOutput(details.details, prefix, false);
                }
            });
        } else {
            details.details.text(getMessage(failure));
        }
    }

    private void fillInFailureResolution(FailureDetails details) {
        BufferingStyledTextOutput resolution = details.resolution;
        if (details.failure instanceof FailureResolutionAware) {
            ((FailureResolutionAware) details.failure).appendResolution(resolution, clientMetaData);
            if (resolution.getHasContent()) {
                resolution.append(' ');
            }
        }
        if (details.exceptionStyle == ExceptionStyle.NONE) {
            resolution.text("Run with ");
            resolution.withStyle(UserInput).format("--%s", LoggingConfigurationBuildOptions.StacktraceOption.STACKTRACE_LONG_OPTION);
            resolution.text(" option to get the stack trace. ");
        }
        if (loggingConfiguration.getLogLevel() != LogLevel.DEBUG) {
            resolution.text("Run with ");
            if (loggingConfiguration.getLogLevel() != LogLevel.INFO) {
                resolution.withStyle(UserInput).format("--%s", LoggingConfigurationBuildOptions.LogLevelOption.INFO_LONG_OPTION);
                resolution.text(" or ");
            }
            resolution.withStyle(UserInput).format("--%s", LoggingConfigurationBuildOptions.LogLevelOption.DEBUG_LONG_OPTION);
            resolution.text(" option to get more log output.");
        }

        addBuildScanMessage(resolution);
    }

    private void addBuildScanMessage(BufferingStyledTextOutput resolution) {
        resolution.text(" Run with ");
        resolution.withStyle(UserInput).format("--%s", StartParameterBuildOptions.BuildScanOption.LONG_OPTION);
        resolution.text(" to get full insights.");
    }

    private void writeGeneralTips(StyledTextOutput resolution) {
        resolution.println();
        resolution.text("* Get more help at https://help.gradle.org");
        resolution.println();
    }

    private String getMessage(Throwable throwable) {
        String message = throwable.getMessage();
        if (GUtil.isTrue(message)) {
            return message;
        }
        return String.format("%s (no error message)", throwable.getClass().getName());
    }

    private void writeFailureDetails(StyledTextOutput output, FailureDetails details) {
        if (details.location.getHasContent()) {
            output.println();
            output.println("* Where:");
            details.location.writeTo(output);
            output.println();
        }

        if (details.details.getHasContent()) {
            output.println();
            output.println("* What went wrong:");
            details.details.writeTo(output);
            output.println();
        }

        if (details.resolution.getHasContent()) {
            output.println();
            output.println("* Try:");
            details.resolution.writeTo(output);
            output.println();
        }

        Throwable exception = null;
        switch (details.exceptionStyle) {
            case NONE:
                break;
            case FULL:
                exception = details.failure;
                break;
        }

        if (exception != null) {
            output.println();
            output.println("* Exception is:");
            output.exception(exception);
            output.println();
        }
    }

    private static class FailureDetails {
        Throwable failure;
        final BufferingStyledTextOutput summary = new BufferingStyledTextOutput();
        final BufferingStyledTextOutput details = new BufferingStyledTextOutput();
        final BufferingStyledTextOutput location = new BufferingStyledTextOutput();
        final BufferingStyledTextOutput resolution = new BufferingStyledTextOutput();

        ExceptionStyle exceptionStyle = ExceptionStyle.NONE;

        public FailureDetails(Throwable failure) {
            this.failure = failure;
        }
    }
}
