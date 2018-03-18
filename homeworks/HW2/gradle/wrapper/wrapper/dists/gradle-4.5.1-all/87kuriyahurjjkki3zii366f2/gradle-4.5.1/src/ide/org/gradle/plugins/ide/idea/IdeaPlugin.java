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
package org.gradle.plugins.ide.idea;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.gradle.api.Action;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.component.ProjectComponentIdentifier;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.internal.IConventionAware;
import org.gradle.api.internal.artifacts.ivyservice.projectmodule.LocalComponentRegistry;
import org.gradle.api.internal.artifacts.publish.DefaultPublishArtifact;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.scala.ScalaBasePlugin;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskDependency;
import org.gradle.initialization.ProjectPathRegistry;
import org.gradle.internal.component.local.model.LocalComponentArtifactMetadata;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.language.scala.plugins.ScalaLanguagePlugin;
import org.gradle.plugins.ide.api.XmlFileContentMerger;
import org.gradle.plugins.ide.idea.internal.IdeaScalaConfigurer;
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;
import org.gradle.plugins.ide.idea.model.IdeaModuleIml;
import org.gradle.plugins.ide.idea.model.IdeaProject;
import org.gradle.plugins.ide.idea.model.IdeaWorkspace;
import org.gradle.plugins.ide.idea.model.PathFactory;
import org.gradle.plugins.ide.idea.model.internal.GeneratedIdeaScope;
import org.gradle.plugins.ide.idea.model.internal.IdeaDependenciesProvider;
import org.gradle.plugins.ide.internal.IdePlugin;
import org.gradle.plugins.ide.internal.configurer.UniqueProjectNameProvider;
import org.gradle.util.Path;
import org.gradle.util.SingleMessageLogger;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Adds a GenerateIdeaModule task. When applied to a root project, also adds a GenerateIdeaProject task. For projects that have the Java plugin applied, the tasks receive additional Java-specific
 * configuration.
 */
public class IdeaPlugin extends IdePlugin {
    private static final Predicate<Project> HAS_IDEA_AND_JAVA_PLUGINS = new Predicate<Project>() {
        @Override
        public boolean apply(Project project) {
            return project.getPlugins().hasPlugin(IdeaPlugin.class) && project.getPlugins().hasPlugin(JavaBasePlugin.class);
        }
    };
    public static final Function<Project, JavaVersion> SOURCE_COMPATIBILITY = new Function<Project, JavaVersion>() {
        @Override
        public JavaVersion apply(Project p) {
            return p.getConvention().getPlugin(JavaPluginConvention.class).getSourceCompatibility();
        }
    };
    public static final Function<Project, JavaVersion> TARGET_COMPATIBILITY = new Function<Project, JavaVersion>() {
        @Override
        public JavaVersion apply(Project p) {
            return p.getConvention().getPlugin(JavaPluginConvention.class).getTargetCompatibility();
        }
    };

    private final Instantiator instantiator;
    private IdeaModel ideaModel;
    private List<Project> allJavaProjects;
    private final UniqueProjectNameProvider uniqueProjectNameProvider;

    @Inject
    public IdeaPlugin(Instantiator instantiator, UniqueProjectNameProvider uniqueProjectNameProvider) {
        this.instantiator = instantiator;
        this.uniqueProjectNameProvider = uniqueProjectNameProvider;
    }

    public IdeaModel getModel() {
        return ideaModel;
    }

    @Override
    protected String getLifecycleTaskName() {
        return "idea";
    }

    @Override
    protected void onApply(final Project project) {
        getLifecycleTask().setDescription("Generates IDEA project files (IML, IPR, IWS)");
        getCleanTask().setDescription("Cleans IDEA project files (IML, IPR)");

        ideaModel = project.getExtensions().create("idea", IdeaModel.class);

        configureIdeaWorkspace(project);
        configureIdeaProject(project);
        configureIdeaModule(project);
        configureForJavaPlugin(project);
        configureForWarPlugin(project);
        configureForScalaPlugin();
        registerIdeArtifact(createImlArtifact(project));
        linkCompositeBuildDependencies((ProjectInternal) project);
    }

    // No one should be calling this.
    @Deprecated
    public void performPostEvaluationActions() {
        SingleMessageLogger.nagUserOfDiscontinuedMethod("performPostEvaluationActions");
    }

    private static PublishArtifact createImlArtifact(Project project) {
        IdeaModule module = project.getExtensions().getByType(IdeaModel.class).getModule();
        Task byName = project.getTasks().getByName("ideaModule");
        return new ImlArtifact(module, byName);
    }

    private void configureIdeaWorkspace(final Project project) {
        if (isRoot()) {
            GenerateIdeaWorkspace task = project.getTasks().create("ideaWorkspace", GenerateIdeaWorkspace.class);
            task.setDescription("Generates an IDEA workspace file (IWS)");
            IdeaWorkspace workspace = new IdeaWorkspace();
            workspace.setIws(new XmlFileContentMerger(task.getXmlTransformer()));
            task.setWorkspace(workspace);
            ideaModel.setWorkspace(task.getWorkspace());
            task.setOutputFile(new File(project.getProjectDir(), project.getName() + ".iws"));
            addWorker(task, false);
        }
    }

    private void configureIdeaProject(final Project project) {
        if (isRoot()) {
            final GenerateIdeaProject task = project.getTasks().create("ideaProject", GenerateIdeaProject.class);
            task.setDescription("Generates IDEA project file (IPR)");
            XmlFileContentMerger ipr = new XmlFileContentMerger(task.getXmlTransformer());
            IdeaProject ideaProject = instantiator.newInstance(IdeaProject.class, project, ipr);
            task.setIdeaProject(ideaProject);
            ideaModel.setProject(ideaProject);

            ideaProject.setOutputFile(new File(project.getProjectDir(), project.getName() + ".ipr"));
            ConventionMapping conventionMapping = ((IConventionAware) ideaProject).getConventionMapping();
            conventionMapping.map("jdkName", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return JavaVersion.current().toString();
                }
            });
            conventionMapping.map("languageLevel", new Callable<IdeaLanguageLevel>() {
                @Override
                public IdeaLanguageLevel call() throws Exception {
                    JavaVersion maxSourceCompatibility = getMaxJavaModuleCompatibilityVersionFor(SOURCE_COMPATIBILITY);
                    return new IdeaLanguageLevel(maxSourceCompatibility);
                }

            });
            conventionMapping.map("targetBytecodeVersion", new Callable<JavaVersion>() {
                @Override
                public JavaVersion call() throws Exception {
                    return getMaxJavaModuleCompatibilityVersionFor(TARGET_COMPATIBILITY);
                }

            });

            ideaProject.getWildcards().addAll(Arrays.asList("!?*.class", "!?*.scala", "!?*.groovy", "!?*.java"));
            conventionMapping.map("modules", new Callable<List<IdeaModule>>() {
                @Override
                public List<IdeaModule> call() throws Exception {
                    return Lists.newArrayList(Iterables.transform(Sets.filter(project.getRootProject().getAllprojects(), new Predicate<Project>() {
                        @Override
                        public boolean apply(Project p) {
                            return p.getPlugins().hasPlugin(IdeaPlugin.class);
                        }

                    }), new Function<Project, IdeaModule>() {
                        @Override
                        public IdeaModule apply(Project p) {
                            return ideaModelFor(p).getModule();
                        }
                    }));
                }
            });

            conventionMapping.map("pathFactory", new Callable<PathFactory>() {
                @Override
                public PathFactory call() throws Exception {
                    return new PathFactory().addPathVariable("PROJECT_DIR", task.getOutputFile().getParentFile());
                }
            });

            addWorker(task);
        }

    }

    private static IdeaModel ideaModelFor(Project project) {
        return project.getExtensions().getByType(IdeaModel.class);
    }

    private JavaVersion getMaxJavaModuleCompatibilityVersionFor(Function<Project, JavaVersion> toJavaVersion) {
        List<Project> allJavaProjects = getAllJavaProjects();
        if (allJavaProjects.isEmpty()) {
            return JavaVersion.VERSION_1_6;
        } else {
            return Collections.max(Lists.transform(allJavaProjects, toJavaVersion));
        }
    }

    private List<Project> getAllJavaProjects() {
        if (allJavaProjects != null) {
            // cache result because it is pretty expensive to compute
            return allJavaProjects;
        }
        allJavaProjects = Lists.newArrayList(Iterables.filter(project.getRootProject().getAllprojects(), HAS_IDEA_AND_JAVA_PLUGINS));
        return allJavaProjects;
    }

    private void configureIdeaModule(final Project project) {
        final GenerateIdeaModule task = project.getTasks().create("ideaModule", GenerateIdeaModule.class);
        task.setDescription("Generates IDEA module files (IML)");
        IdeaModuleIml iml = new IdeaModuleIml(task.getXmlTransformer(), project.getProjectDir());
        final IdeaModule module = instantiator.newInstance(IdeaModule.class, project, iml);
        task.setModule(module);
        ideaModel.setModule(module);

        final String defaultModuleName = uniqueProjectNameProvider.getUniqueName(project);
        module.setName(defaultModuleName);

        ConventionMapping conventionMapping = ((IConventionAware) module).getConventionMapping();
        conventionMapping.map("sourceDirs", new Callable<Set<File>>() {
            @Override
            public Set<File> call() throws Exception {
                return Sets.newLinkedHashSet();
            }
        });
        conventionMapping.map("contentRoot", new Callable<File>() {
            @Override
            public File call() throws Exception {
                return project.getProjectDir();
            }
        });
        conventionMapping.map("testSourceDirs", new Callable<Set<File>>() {
            @Override
            public Set<File> call() throws Exception {
                return Sets.newLinkedHashSet();
            }
        });
        conventionMapping.map("excludeDirs", new Callable<Set<File>>() {
            @Override
            public Set<File> call() throws Exception {
                Set<File> defaultExcludes = Sets.newLinkedHashSet();
                defaultExcludes.add(project.file(".gradle"));
                defaultExcludes.add(project.getBuildDir());
                return defaultExcludes;
            }
        });

        conventionMapping.map("pathFactory", new Callable<PathFactory>() {
            @Override
            public PathFactory call() throws Exception {
                final PathFactory factory = new PathFactory();
                factory.addPathVariable("MODULE_DIR", task.getOutputFile().getParentFile());
                for (Map.Entry<String, File> entry : module.getPathVariables().entrySet()) {
                    factory.addPathVariable(entry.getKey(), entry.getValue());
                }
                return factory;
            }

        });

        addWorker(task);
    }

    private void configureForJavaPlugin(final Project project) {
        project.getPlugins().withType(JavaPlugin.class, new Action<JavaPlugin>() {
            @Override
            public void execute(JavaPlugin javaPlugin) {
                configureIdeaModuleForJava(project);
            }
        });
    }
    private void configureForWarPlugin(final Project project) {
        project.getPlugins().withType(WarPlugin.class, new Action<WarPlugin>() {
            @Override
            public void execute(WarPlugin warPlugin) {
                configureIdeaModuleForWar(project);
            }
        });
    }

    private void configureIdeaModuleForJava(final Project project) {
        project.getTasks().withType(GenerateIdeaModule.class, new Action<GenerateIdeaModule>() {
            @Override
            public void execute(GenerateIdeaModule ideaModule) {
                // Defaults
                setupScopes(ideaModule);

                // Convention
                ConventionMapping convention = ((IConventionAware) ideaModule.getModule()).getConventionMapping();
                convention.map("sourceDirs", new Callable<Set<File>>() {
                    @Override
                    public Set<File> call() throws Exception {
                        SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
                        return sourceSets.getByName("main").getAllSource().getSrcDirs();
                    }
                });
                convention.map("testSourceDirs", new Callable<Set<File>>() {
                    @Override
                    public Set<File> call() throws Exception {
                        SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
                        return sourceSets.getByName("test").getAllSource().getSrcDirs();
                    }
                });
                convention.map("singleEntryLibraries", new Callable<Map<String, FileCollection>>() {
                    @Override
                    public Map<String, FileCollection> call() throws Exception {
                        SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
                        LinkedHashMap<String, FileCollection> map = new LinkedHashMap<String, FileCollection>(2);
                        map.put("RUNTIME", sourceSets.getByName("main").getOutput().getDirs());
                        map.put("TEST", sourceSets.getByName("test").getOutput().getDirs());
                        return map;
                    }

                });
                convention.map("targetBytecodeVersion", new Callable<JavaVersion>() {
                    @Override
                    public JavaVersion call() throws Exception {
                        JavaVersion moduleTargetBytecodeLevel = project.getConvention().getPlugin(JavaPluginConvention.class).getTargetCompatibility();
                        return includeModuleBytecodeLevelOverride(project.getRootProject(), moduleTargetBytecodeLevel) ? moduleTargetBytecodeLevel : null;
                    }

                });
                convention.map("languageLevel", new Callable<IdeaLanguageLevel>() {
                    @Override
                    public IdeaLanguageLevel call() throws Exception {
                        IdeaLanguageLevel moduleLanguageLevel = new IdeaLanguageLevel(project.getConvention().getPlugin(JavaPluginConvention.class).getSourceCompatibility());
                        return includeModuleLanguageLevelOverride(project.getRootProject(), moduleLanguageLevel) ? moduleLanguageLevel : null;
                    }

                });
                // Dependencies
                ideaModule.dependsOn(new Callable<FileCollection>() {
                    @Override
                    public FileCollection call() throws Exception {
                        SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
                        return sourceSets.getByName("main").getOutput().getDirs().plus(sourceSets.getByName("test").getOutput().getDirs());
                    }

                });
            }

        });
    }

    private void setupScopes(GenerateIdeaModule ideaModule) {
        Map<String, Map<String, Collection<Configuration>>> scopes = Maps.newLinkedHashMap();
        for (GeneratedIdeaScope scope : GeneratedIdeaScope.values()) {
            Map<String, Collection<Configuration>> plusMinus = Maps.newLinkedHashMap();
            plusMinus.put(IdeaDependenciesProvider.SCOPE_PLUS, Lists.<Configuration>newArrayList());
            plusMinus.put(IdeaDependenciesProvider.SCOPE_MINUS, Lists.<Configuration>newArrayList());
            scopes.put(scope.name(), plusMinus);
        }

        Project project = ideaModule.getProject();
        ConfigurationContainer configurations = project.getConfigurations();

        Collection<Configuration> provided = scopes.get(GeneratedIdeaScope.PROVIDED.name()).get(IdeaDependenciesProvider.SCOPE_PLUS);
        provided.add(configurations.getByName(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME));

        Collection<Configuration> runtime = scopes.get(GeneratedIdeaScope.RUNTIME.name()).get(IdeaDependenciesProvider.SCOPE_PLUS);
        runtime.add(configurations.getByName(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME));

        Collection<Configuration> test = scopes.get(GeneratedIdeaScope.TEST.name()).get(IdeaDependenciesProvider.SCOPE_PLUS);
        test.add(configurations.getByName(JavaPlugin.TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME));
        test.add(configurations.getByName(JavaPlugin.TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME));

        ideaModule.getModule().setScopes(scopes);
    }

    private void configureIdeaModuleForWar(final Project project) {
        project.getTasks().withType(GenerateIdeaModule.class, new Action<GenerateIdeaModule>() {
            @Override
            public void execute(GenerateIdeaModule ideaModule) {
                ConfigurationContainer configurations = project.getConfigurations();
                Configuration providedRuntime = configurations.getByName(WarPlugin.PROVIDED_RUNTIME_CONFIGURATION_NAME);
                Collection<Configuration> providedPlus = ideaModule.getModule().getScopes().get(GeneratedIdeaScope.PROVIDED.name()).get(IdeaDependenciesProvider.SCOPE_PLUS);
                providedPlus.add(providedRuntime);
                Collection<Configuration> runtimeMinus = ideaModule.getModule().getScopes().get(GeneratedIdeaScope.RUNTIME.name()).get(IdeaDependenciesProvider.SCOPE_MINUS);
                runtimeMinus.add(providedRuntime);
                Collection<Configuration> testMinus = ideaModule.getModule().getScopes().get(GeneratedIdeaScope.TEST.name()).get(IdeaDependenciesProvider.SCOPE_MINUS);
                testMinus.add(providedRuntime);
            }
        });
    }

    private static boolean includeModuleBytecodeLevelOverride(Project rootProject, JavaVersion moduleTargetBytecodeLevel) {
        if (!rootProject.getPlugins().hasPlugin(IdeaPlugin.class)) {
            return true;
        }

        IdeaProject ideaProject = ideaModelFor(rootProject).getProject();
        return !moduleTargetBytecodeLevel.equals(ideaProject.getTargetBytecodeVersion());
    }

    private static boolean includeModuleLanguageLevelOverride(Project rootProject, IdeaLanguageLevel moduleLanguageLevel) {
        if (!rootProject.getPlugins().hasPlugin(IdeaPlugin.class)) {
            return true;
        }

        IdeaProject ideaProject = ideaModelFor(rootProject).getProject();
        return !moduleLanguageLevel.equals(ideaProject.getLanguageLevel());
    }

    private void configureForScalaPlugin() {
        project.getPlugins().withType(ScalaBasePlugin.class, new Action<ScalaBasePlugin>() {
            @Override
            public void execute(ScalaBasePlugin scalaBasePlugin) {
                ideaModuleDependsOnRoot();
            }
        });
        project.getPlugins().withType(ScalaLanguagePlugin.class, new Action<ScalaLanguagePlugin>() {
            @Override
            public void execute(ScalaLanguagePlugin scalaLanguagePlugin) {
                ideaModuleDependsOnRoot();
            }

        });
        if (isRoot()) {
            new IdeaScalaConfigurer(project).configure();
        }
    }

    private void ideaModuleDependsOnRoot() {
        // see IdeaScalaConfigurer which requires the ipr to be generated first
        project.getTasks().findByName("ideaModule").dependsOn(project.getRootProject().getTasks().findByName("ideaProject"));
    }

    private void linkCompositeBuildDependencies(final ProjectInternal project) {
        if (isRoot()) {
            getLifecycleTask().dependsOn(new Callable<List<TaskDependency>>() {
                @Override
                public List<TaskDependency> call() throws Exception {
                    return allImlArtifactsInComposite(project);
                }
            });
        }
    }

    private List<TaskDependency> allImlArtifactsInComposite(ProjectInternal project) {
        List<TaskDependency> dependencies = Lists.newArrayList();
        ServiceRegistry services = project.getServices();
        ProjectPathRegistry projectPathRegistry = services.get(ProjectPathRegistry.class);
        LocalComponentRegistry localComponentRegistry = services.get(LocalComponentRegistry.class);
        ProjectComponentIdentifier thisProjectId = projectPathRegistry.getProjectComponentIdentifier(project.getIdentityPath());
        for (Path projectPath : projectPathRegistry.getAllProjectPaths()) {
            final ProjectComponentIdentifier otherProjectId = projectPathRegistry.getProjectComponentIdentifier(projectPath);
            if (thisProjectId.getBuild().equals(otherProjectId.getBuild())) {
                // IDEA Module for project in current build: handled via `modules` model elements.
                continue;
            }
            LocalComponentArtifactMetadata imlArtifact = localComponentRegistry.findAdditionalArtifact(otherProjectId, "iml");
            if (imlArtifact != null) {
                dependencies.add(imlArtifact.getBuildDependencies());
            }
        }
        return dependencies;
    }

    private static class ImlArtifact extends DefaultPublishArtifact {
        private final IdeaModule module;
        private final File projectDir;

        public ImlArtifact(IdeaModule module, Object... tasks) {
            super(null, "iml", "iml", null, null, null, tasks);
            this.module = module;
            this.projectDir = module.getProject().getProjectDir();
        }

        @Override
        public String getName() {
            return module.getName();
        }

        @Override
        public File getFile() {
            return new File(projectDir, getName() + ".iml");
        }
    }

}
