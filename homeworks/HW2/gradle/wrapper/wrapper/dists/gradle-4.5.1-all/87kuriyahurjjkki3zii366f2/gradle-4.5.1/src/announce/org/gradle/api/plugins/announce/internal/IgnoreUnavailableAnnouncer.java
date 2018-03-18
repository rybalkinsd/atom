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

import org.gradle.api.plugins.announce.Announcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IgnoreUnavailableAnnouncer implements Announcer {
    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoreUnavailableAnnouncer.class);
    private final Announcer announcer;

    public IgnoreUnavailableAnnouncer(Announcer announcer) {
        this.announcer = announcer;
    }

    @Override
    public void send(String title, String message) {
        try {
            announcer.send(title, message);
        } catch (AnnouncerUnavailableException e) {
            // Ignore
            LOGGER.debug("Discarding message [" + title + "][" + message + "] as announcer is not available: " + e.getMessage());
        }
    }

}
