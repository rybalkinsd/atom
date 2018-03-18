/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.tasks;

import org.gradle.api.InvalidUserDataException;
import org.gradle.internal.exceptions.DefaultMultiCauseException;
import org.gradle.internal.exceptions.Contextual;

import java.util.List;

/**
 * A {@code TaskValidationException} is thrown when there is some validation problem with a task.
 */
@Contextual
public class TaskValidationException extends DefaultMultiCauseException {
    public TaskValidationException(String message, List<InvalidUserDataException> causes) {
        super(message, causes);
    }
}
