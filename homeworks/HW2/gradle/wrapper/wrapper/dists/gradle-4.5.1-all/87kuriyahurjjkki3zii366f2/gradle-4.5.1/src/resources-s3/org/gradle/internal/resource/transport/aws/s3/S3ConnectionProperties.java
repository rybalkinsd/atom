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

package org.gradle.internal.resource.transport.aws.s3;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.apache.commons.lang.StringUtils;
import org.gradle.internal.resource.transport.http.HttpProxySettings;
import org.gradle.internal.resource.transport.http.JavaSystemPropertiesHttpProxySettings;
import org.gradle.internal.resource.transport.http.JavaSystemPropertiesSecureHttpProxySettings;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import static com.amazonaws.services.s3.internal.Constants.S3_HOSTNAME;
import static java.lang.System.getProperty;

public class S3ConnectionProperties {
    public static final String S3_ENDPOINT_PROPERTY = "org.gradle.s3.endpoint";
    //The maximum number of times to retry a request when S3 responds with a http 5xx error
    public static final String S3_MAX_ERROR_RETRY = "org.gradle.s3.maxErrorRetry";
    private static final Set<String> SUPPORTED_SCHEMES = Sets.newHashSet("HTTP", "HTTPS");

    private final Optional<URI> endpoint;
    private final HttpProxySettings proxySettings;
    private final HttpProxySettings secureProxySettings;
    private final Optional<Integer> maxErrorRetryCount;

    public S3ConnectionProperties() {
        endpoint = configureEndpoint(getProperty(S3_ENDPOINT_PROPERTY));
        proxySettings = new JavaSystemPropertiesHttpProxySettings();
        secureProxySettings = new JavaSystemPropertiesSecureHttpProxySettings();
        maxErrorRetryCount = configureErrorRetryCount(getProperty(S3_MAX_ERROR_RETRY));
    }

    public S3ConnectionProperties(HttpProxySettings proxySettings, HttpProxySettings secureProxySettings, URI endpoint, Integer maxErrorRetryCount) {
        this.endpoint = Optional.fromNullable(endpoint);
        this.proxySettings = proxySettings;
        this.secureProxySettings = secureProxySettings;
        this.maxErrorRetryCount = Optional.fromNullable(maxErrorRetryCount);
    }

    private Optional<URI> configureEndpoint(String property) {
        URI uri = null;
        if (StringUtils.isNotBlank(property)) {
            try {
                uri = new URI(property);
                if (StringUtils.isBlank(uri.getScheme()) || !SUPPORTED_SCHEMES.contains(uri.getScheme().toUpperCase())) {
                    throw new IllegalArgumentException("System property [" + S3_ENDPOINT_PROPERTY + "=" + property + "] must have a scheme of 'http' or 'https'");
                }
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("System property [" + S3_ENDPOINT_PROPERTY + "=" + property + "]  must be a valid URI");
            }
        }
        return Optional.fromNullable(uri);
    }

    public Optional<URI> getEndpoint() {
        return endpoint;
    }

    public Optional<HttpProxySettings.HttpProxy> getProxy() {
        if (endpoint.isPresent()) {
            String host = endpoint.get().getHost();
            if (endpoint.get().getScheme().toUpperCase().equals("HTTP")) {
                return Optional.fromNullable(proxySettings.getProxy(host));
            } else {
                return Optional.fromNullable(secureProxySettings.getProxy(host));
            }
        }
        return Optional.fromNullable(secureProxySettings.getProxy(S3_HOSTNAME));
    }

    private Optional<Integer> configureErrorRetryCount(String property) {
        Integer count = null;
        if (null != property) {
            count = Ints.tryParse(property);
            if (null == count || count < 0) {
                throw new IllegalArgumentException("System property [" + S3_MAX_ERROR_RETRY + "=" + property + "]  must be a valid positive Integer");

            }
        }
        return Optional.fromNullable(count);
    }

    public Optional<Integer> getMaxErrorRetryCount() {
        return maxErrorRetryCount;
    }
}
