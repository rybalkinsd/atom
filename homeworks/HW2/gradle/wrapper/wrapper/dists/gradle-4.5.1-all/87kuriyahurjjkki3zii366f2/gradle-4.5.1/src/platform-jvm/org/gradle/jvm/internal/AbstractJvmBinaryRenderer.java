/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.jvm.internal;

import org.gradle.api.reporting.components.internal.AbstractBinaryRenderer;
import org.gradle.api.tasks.diagnostics.internal.text.TextReportBuilder;
import org.gradle.jvm.JvmBinarySpec;
import org.gradle.jvm.toolchain.JavaToolChain;
import org.gradle.model.internal.manage.schema.ModelSchemaStore;

public abstract class AbstractJvmBinaryRenderer<T extends JvmBinarySpec> extends AbstractBinaryRenderer<T> {
    protected AbstractJvmBinaryRenderer(ModelSchemaStore schemaStore) {
        super(schemaStore);
    }

    @Override
    protected void renderDetails(T binary, TextReportBuilder builder) {
        JavaToolChain toolChain = binary.getToolChain();
        builder.item("tool chain", toolChain != null ? toolChain.getDisplayName() : "not set");
    }

    @Override
    protected void renderOutputs(T binary, TextReportBuilder builder) {
        builder.item("classes dir", binary.getClassesDir());
        builder.item("resources dir", binary.getResourcesDir());
    }
}
