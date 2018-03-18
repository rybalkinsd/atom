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

package org.gradle.api.internal.tasks;

import com.google.common.collect.ImmutableSortedSet;
import groovy.lang.Closure;
import org.gradle.api.Describable;
import org.gradle.api.NonNullApi;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.FilePropertyContainer;
import org.gradle.api.internal.OverlappingOutputs;
import org.gradle.api.internal.TaskExecutionHistory;
import org.gradle.api.internal.TaskInternal;
import org.gradle.api.internal.TaskOutputCachingState;
import org.gradle.api.internal.TaskOutputsInternal;
import org.gradle.api.internal.file.CompositeFileCollection;
import org.gradle.api.internal.file.collections.FileCollectionResolveContext;
import org.gradle.api.internal.tasks.execution.SelfDescribingSpec;
import org.gradle.api.internal.tasks.execution.TaskProperties;
import org.gradle.api.internal.tasks.properties.GetOutputFilesVisitor;
import org.gradle.api.internal.tasks.properties.PropertyVisitor;
import org.gradle.api.internal.tasks.properties.PropertyWalker;
import org.gradle.api.specs.AndSpec;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskOutputFilePropertyBuilder;

import javax.annotation.Nullable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.gradle.api.internal.tasks.TaskOutputCachingDisabledReasonCategory.*;

@NonNullApi
public class DefaultTaskOutputs implements TaskOutputsInternal {
    private static final TaskOutputCachingState ENABLED = DefaultTaskOutputCachingState.enabled();
    public static final TaskOutputCachingState DISABLED = DefaultTaskOutputCachingState.disabled(BUILD_CACHE_DISABLED, "Task output caching is disabled");
    private static final TaskOutputCachingState CACHING_NOT_ENABLED = DefaultTaskOutputCachingState.disabled(TaskOutputCachingDisabledReasonCategory.NOT_ENABLED_FOR_TASK, "Caching has not been enabled for the task");
    private static final TaskOutputCachingState NO_OUTPUTS_DECLARED = DefaultTaskOutputCachingState.disabled(TaskOutputCachingDisabledReasonCategory.NO_OUTPUTS_DECLARED, "No outputs declared");

    private final FileCollection allOutputFiles;
    private final PropertyWalker propertyWalker;
    private final PropertySpecFactory specFactory;
    private AndSpec<TaskInternal> upToDateSpec = AndSpec.empty();
    private List<SelfDescribingSpec<TaskInternal>> cacheIfSpecs = new LinkedList<SelfDescribingSpec<TaskInternal>>();
    private List<SelfDescribingSpec<TaskInternal>> doNotCacheIfSpecs = new LinkedList<SelfDescribingSpec<TaskInternal>>();
    private TaskExecutionHistory history;
    private final FilePropertyContainer<DeclaredTaskOutputFileProperty> registeredFileProperties = FilePropertyContainer.create();
    private final TaskInternal task;
    private final TaskMutator taskMutator;

    public DefaultTaskOutputs(final TaskInternal task, TaskMutator taskMutator, PropertyWalker propertyWalker, PropertySpecFactory specFactory) {
        this.task = task;
        this.taskMutator = taskMutator;
        this.allOutputFiles = new TaskOutputUnionFileCollection(task);
        this.propertyWalker = propertyWalker;
        this.specFactory = specFactory;
    }

    @Override
    public void visitRegisteredProperties(PropertyVisitor visitor) {
        for (DeclaredTaskOutputFileProperty fileProperty : registeredFileProperties) {
            visitor.visitOutputFileProperty(fileProperty);
        }
    }

    @Override
    public AndSpec<? super TaskInternal> getUpToDateSpec() {
        return upToDateSpec;
    }

    @Override
    public void upToDateWhen(final Closure upToDateClosure) {
        taskMutator.mutate("TaskOutputs.upToDateWhen(Closure)", new Runnable() {
            public void run() {
                upToDateSpec = upToDateSpec.and(upToDateClosure);
            }
        });
    }

    @Override
    public void upToDateWhen(final Spec<? super Task> spec) {
        taskMutator.mutate("TaskOutputs.upToDateWhen(Spec)", new Runnable() {
            public void run() {
                upToDateSpec = upToDateSpec.and(spec);
            }
        });
    }

    @Override
    public TaskOutputCachingState getCachingState(TaskProperties taskProperties) {
        if (cacheIfSpecs.isEmpty()) {
            return CACHING_NOT_ENABLED;
        }

        if (!taskProperties.hasDeclaredOutputs()) {
            return NO_OUTPUTS_DECLARED;
        }

        OverlappingOutputs overlappingOutputs = getOverlappingOutputs();
        if (overlappingOutputs != null) {
            String relativePath = task.getProject().relativePath(overlappingOutputs.getOverlappedFilePath());
            return DefaultTaskOutputCachingState.disabled(TaskOutputCachingDisabledReasonCategory.OVERLAPPING_OUTPUTS,
                String.format("Gradle does not know how file '%s' was created (output property '%s'). Task output caching requires exclusive access to output paths to guarantee correctness.",
                    relativePath, overlappingOutputs.getPropertyName()));
        }

        for (TaskPropertySpec spec : taskProperties.getOutputFileProperties()) {
            if (spec instanceof NonCacheableTaskOutputPropertySpec) {
                return DefaultTaskOutputCachingState.disabled(
                    PLURAL_OUTPUTS,
                    "Declares multiple output files for the single output property '"
                        + ((NonCacheableTaskOutputPropertySpec) spec).getOriginalPropertyName()
                        + "' via `@OutputFiles`, `@OutputDirectories` or `TaskOutputs.files()`"
                );
            }
        }

        for (SelfDescribingSpec<TaskInternal> selfDescribingSpec : cacheIfSpecs) {
            if (!selfDescribingSpec.isSatisfiedBy(task)) {
                return DefaultTaskOutputCachingState.disabled(
                    CACHE_IF_SPEC_NOT_SATISFIED,
                    "'" + selfDescribingSpec.getDisplayName() + "' not satisfied"
                );
            }
        }

        for (SelfDescribingSpec<TaskInternal> selfDescribingSpec : doNotCacheIfSpecs) {
            if (selfDescribingSpec.isSatisfiedBy(task)) {
                return DefaultTaskOutputCachingState.disabled(
                    DO_NOT_CACHE_IF_SPEC_SATISFIED,
                    "'" + selfDescribingSpec.getDisplayName() + "' satisfied"
                );
            }
        }
        return ENABLED;
    }

    @Nullable
    private OverlappingOutputs getOverlappingOutputs() {
        return history != null ? history.getOverlappingOutputs() : null;
    }

    @Override
    public void cacheIf(final Spec<? super Task> spec) {
        cacheIf("Task outputs cacheable", spec);
    }

    @Override
    public void cacheIf(final String cachingEnabledReason, final Spec<? super Task> spec) {
        taskMutator.mutate("TaskOutputs.cacheIf(Spec)", new Runnable() {
            public void run() {
                cacheIfSpecs.add(new SelfDescribingSpec<TaskInternal>(spec, cachingEnabledReason));
            }
        });
    }

    @Override
    public void doNotCacheIf(final String cachingDisabledReason, final Spec<? super Task> spec) {
        taskMutator.mutate("TaskOutputs.doNotCacheIf(Spec)", new Runnable() {
            public void run() {
                doNotCacheIfSpecs.add(new SelfDescribingSpec<TaskInternal>(spec, cachingDisabledReason));
            }
        });
    }

    @Override
    public boolean getHasOutput() {
        if (!upToDateSpec.isEmpty()) {
            return true;
        }
        HasDeclaredOutputsVisitor visitor = new HasDeclaredOutputsVisitor();
        TaskPropertyUtils.visitProperties(propertyWalker, task, visitor);
        return visitor.hasDeclaredOutputs();
    }

    @Override
    public FileCollection getFiles() {
        return allOutputFiles;
    }

    public ImmutableSortedSet<TaskOutputFilePropertySpec> getFileProperties() {
        GetOutputFilesVisitor visitor = new GetOutputFilesVisitor();
        TaskPropertyUtils.visitProperties(propertyWalker, task, visitor);
        return visitor.getFileProperties();
    }

    @Override
    public TaskOutputFilePropertyBuilder file(final Object path) {
        return taskMutator.mutate("TaskOutputs.file(Object)", new Callable<TaskOutputFilePropertyBuilder>() {
            @Override
            public TaskOutputFilePropertyBuilder call() {
                StaticValue value = new StaticValue(path);
                DeclaredTaskOutputFileProperty outputFileSpec = specFactory.createOutputFileSpec(value);
                registeredFileProperties.add(outputFileSpec);
                return outputFileSpec;
            }
        });
    }

    @Override
    public TaskOutputFilePropertyBuilder dir(final Object path) {
        return taskMutator.mutate("TaskOutputs.dir(Object)", new Callable<TaskOutputFilePropertyBuilder>() {
            @Override
            public TaskOutputFilePropertyBuilder call() {
                StaticValue value = new StaticValue(path);
                DeclaredTaskOutputFileProperty outputDirSpec = specFactory.createOutputDirSpec(value);
                registeredFileProperties.add(outputDirSpec);
                return outputDirSpec;
            }
        });
    }

    @Override
    public TaskOutputFilePropertyBuilder files(final @Nullable Object... paths) {
        return taskMutator.mutate("TaskOutputs.files(Object...)", new Callable<TaskOutputFilePropertyBuilder>() {
            @Override
            public TaskOutputFilePropertyBuilder call() {
                StaticValue value = new StaticValue(resolveSingleArray(paths));
                DeclaredTaskOutputFileProperty outputFilesSpec = specFactory.createOutputFilesSpec(value);
                registeredFileProperties.add(outputFilesSpec);
                return outputFilesSpec;
            }
        });
    }

    @Override
    public TaskOutputFilePropertyBuilder dirs(final Object... paths) {
        return taskMutator.mutate("TaskOutputs.dirs(Object...)", new Callable<TaskOutputFilePropertyBuilder>() {
            @Override
            public TaskOutputFilePropertyBuilder call() {
                StaticValue value = new StaticValue(resolveSingleArray(paths));
                DeclaredTaskOutputFileProperty outputDirsSpec = specFactory.createOutputDirsSpec(value);
                registeredFileProperties.add(outputDirsSpec);
                return outputDirsSpec;
            }
        });
    }

    @Nullable
    private static Object resolveSingleArray(@Nullable Object[] paths) {
        return (paths != null && paths.length == 1) ? paths[0] : paths;
    }

    @Override
    public Set<File> getPreviousOutputFiles() {
        if (history == null) {
            throw new IllegalStateException("Task history is currently not available for this task.");
        }
        return history.getOutputFiles();
    }

    @Override
    public void setHistory(@Nullable TaskExecutionHistory history) {
        this.history = history;
    }

    private static class HasDeclaredOutputsVisitor extends PropertyVisitor.Adapter {
        boolean hasDeclaredOutputs;

        @Override
        public void visitOutputFileProperty(TaskOutputFilePropertySpec outputFileProperty) {
            hasDeclaredOutputs = true;
        }

        public boolean hasDeclaredOutputs() {
            return hasDeclaredOutputs;
        }
    }

    private class TaskOutputUnionFileCollection extends CompositeFileCollection implements Describable {
        private final TaskInternal buildDependencies;

        public TaskOutputUnionFileCollection(TaskInternal buildDependencies) {
            this.buildDependencies = buildDependencies;
        }

        @Override
        public String getDisplayName() {
            return "task '" + task.getName() + "' output files";
        }

        @Override
        public void visitContents(FileCollectionResolveContext context) {
            for (TaskFilePropertySpec propertySpec : getFileProperties()) {
                context.add(propertySpec.getPropertyFiles());
            }
        }

        @Override
        public void visitDependencies(TaskDependencyResolveContext context) {
            context.add(buildDependencies);
            super.visitDependencies(context);
        }
    }

}
