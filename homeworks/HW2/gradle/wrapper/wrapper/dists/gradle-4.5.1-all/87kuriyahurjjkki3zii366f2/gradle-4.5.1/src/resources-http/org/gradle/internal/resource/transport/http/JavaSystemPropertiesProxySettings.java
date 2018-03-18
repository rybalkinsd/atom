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

package org.gradle.internal.resource.transport.http;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public abstract class JavaSystemPropertiesProxySettings implements HttpProxySettings {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSystemPropertiesProxySettings.class);

    private final HttpProxy proxy;
    private final List<Pattern> nonProxyHosts;
    private final String propertyPrefix;
    private final int defaultPort;

    public JavaSystemPropertiesProxySettings(String propertyPrefix, int defaultPort) {
        this(propertyPrefix, defaultPort,
                System.getProperty(propertyPrefix + ".proxyHost"),
                System.getProperty(propertyPrefix + ".proxyPort"),
                System.getProperty(propertyPrefix + ".proxyUser"),
                System.getProperty(propertyPrefix + ".proxyPassword"),
                System.getProperty(propertyPrefix + ".nonProxyHosts"));
    }

    JavaSystemPropertiesProxySettings(String propertyPrefix, int defaultPort, String proxyHost, String proxyPortString, String proxyUser, String proxyPassword, String nonProxyHostsString) {
        this.propertyPrefix = propertyPrefix;
        this.defaultPort = defaultPort;
        if (StringUtils.isBlank(proxyHost)) {
            this.proxy = null;
        } else {
            this.proxy = new HttpProxy(proxyHost, initProxyPort(proxyPortString), proxyUser, proxyPassword);
        }
        this.nonProxyHosts = initNonProxyHosts(nonProxyHostsString);
    }

    private int initProxyPort(String proxyPortString) {
        if (StringUtils.isBlank(proxyPortString)) {
            return defaultPort;
        }
        try {
            return Integer.parseInt(proxyPortString);
        } catch (NumberFormatException e) {
            String key = propertyPrefix + ".proxyPort";
            LOGGER.warn("Invalid value for java system property '{}': {}. Default port '{}' will be used.",
                    key, System.getProperty(key), defaultPort);
            return defaultPort;
        }
    }

    private List<Pattern> initNonProxyHosts(String nonProxyHostsString) {
        if (StringUtils.isBlank(nonProxyHostsString)) {
            return Collections.emptyList();
        }

        LOGGER.debug("Found java system property 'http.nonProxyHosts': {}. Will ignore proxy settings for these hosts.", nonProxyHostsString);
        List<Pattern> patterns = new ArrayList<Pattern>();
        for (String nonProxyHost : nonProxyHostsString.split("\\|")) {
            patterns.add(createHostMatcher(nonProxyHost));
        }
        return patterns;
    }

    private Pattern createHostMatcher(String nonProxyHost) {
        if (nonProxyHost.startsWith("*")) {
            return Pattern.compile(".*" + Pattern.quote(nonProxyHost.substring(1)));
        }
        if (nonProxyHost.endsWith("*")) {
            return Pattern.compile(Pattern.quote(nonProxyHost.substring(0, nonProxyHost.length() - 1)) + ".*");
        }
        return Pattern.compile(Pattern.quote(nonProxyHost));
    }

    public HttpProxySettings.HttpProxy getProxy() {
        return proxy;
    }

    public HttpProxySettings.HttpProxy getProxy(String host) {
        if (proxy == null || isNonProxyHost(host)) {
            return null;
        }
        return proxy;
    }

    private boolean isNonProxyHost(String host) {
        for (Pattern nonProxyHost : nonProxyHosts) {
            if (nonProxyHost.matcher(host).matches()) {
                return true;
            }
        }
        return false;
    }

    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    public int getDefaultPort() {
        return defaultPort;
    }
}
