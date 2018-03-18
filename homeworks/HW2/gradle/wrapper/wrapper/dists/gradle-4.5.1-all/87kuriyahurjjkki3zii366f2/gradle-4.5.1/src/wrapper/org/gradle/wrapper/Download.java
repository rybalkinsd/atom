/*
 * Copyright 2007-2009 the original author or authors.
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

package org.gradle.wrapper;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;

public class Download implements IDownload {
    private static final int PROGRESS_CHUNK = 1024 * 1024;
    private static final int BUFFER_SIZE = 10 * 1024;
    private final Logger logger;
    private final String appName;
    private final String appVersion;
    private final DownloadProgressListener progressListener;

    public Download(Logger logger, String appName, String appVersion) {
        this(logger, null, appName, appVersion);
    }

    public Download(Logger logger, DownloadProgressListener progressListener, String appName, String appVersion) {
        this.logger = logger;
        this.appName = appName;
        this.appVersion = appVersion;
        this.progressListener = progressListener;
        configureProxyAuthentication();
    }

    private void configureProxyAuthentication() {
        if (System.getProperty("http.proxyUser") != null) {
            Authenticator.setDefault(new ProxyAuthenticator());
        }
    }

    public void download(URI address, File destination) throws Exception {
        destination.getParentFile().mkdirs();
        downloadInternal(address, destination);
    }

    private void downloadInternal(URI address, File destination)
            throws Exception {
        OutputStream out = null;
        URLConnection conn;
        InputStream in = null;
        try {
            URL url = safeUri(address).toURL();
            out = new BufferedOutputStream(new FileOutputStream(destination));
            conn = url.openConnection();
            addBasicAuthentication(address, conn);
            final String userAgentValue = calculateUserAgent();
            conn.setRequestProperty("User-Agent", userAgentValue);
            in = conn.getInputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int numRead;
            int contentLength = conn.getContentLength();
            long progressCounter = 0;
            long numDownloaded = 0;
            while ((numRead = in.read(buffer)) != -1) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.print("interrupted");
                    throw new IOException("Download was interrupted.");
                }
                numDownloaded += numRead;
                progressCounter += numRead;
                if (progressCounter / PROGRESS_CHUNK > 0) {
                    logger.append(".");
                    progressCounter = progressCounter - PROGRESS_CHUNK;
                    if (progressListener != null) {
                        progressListener.downloadStatusChanged(address, contentLength, numDownloaded);
                    }
                }
                out.write(buffer, 0, numRead);
            }
        } finally {
            logger.log("");
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Create a safe URI from the given one by stripping out user info.
     *
     * @param uri Original URI
     * @return a new URI with no user info
     */
    static URI safeUri(URI uri) throws URISyntaxException {
        return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
    }

    private void addBasicAuthentication(URI address, URLConnection connection) throws IOException {
        String userInfo = calculateUserInfo(address);
        if (userInfo == null) {
            return;
        }
        if (!"https".equals(address.getScheme())) {
            logger.log("WARNING Using HTTP Basic Authentication over an insecure connection to download the Gradle distribution. Please consider using HTTPS.");
        }
        connection.setRequestProperty("Authorization", "Basic " + base64Encode(userInfo));
    }

    /**
     * Base64 encode user info for HTTP Basic Authentication.
     *
     * Try to use {@literal java.util.Base64} encoder which is available starting with Java 8.
     * Fallback to {@literal javax.xml.bind.DatatypeConverter} from JAXB which is available starting with Java 6 but is not anymore in Java 9.
     * Fortunately, both of these two Base64 encoders implement the right Base64 flavor, the one that does not split the output in multiple lines.
     *
     * @param userInfo user info
     * @return Base64 encoded user info
     * @throws RuntimeException if no public Base64 encoder is available on this JVM
     */
    private String base64Encode(String userInfo) {
        ClassLoader loader = getClass().getClassLoader();
        try {
            Method getEncoderMethod = loader.loadClass("java.util.Base64").getMethod("getEncoder");
            Method encodeMethod = loader.loadClass("java.util.Base64$Encoder").getMethod("encodeToString", byte[].class);
            Object encoder = getEncoderMethod.invoke(null);
            return (String) encodeMethod.invoke(encoder, new Object[]{userInfo.getBytes("UTF-8")});
        } catch (Exception java7OrEarlier) {
            try {
                Method encodeMethod = loader.loadClass("javax.xml.bind.DatatypeConverter").getMethod("printBase64Binary", byte[].class);
                return (String) encodeMethod.invoke(null, new Object[]{userInfo.getBytes("UTF-8")});
            } catch (Exception java5OrEarlier) {
                throw new RuntimeException("Downloading Gradle distributions with HTTP Basic Authentication is not supported on your JVM.", java5OrEarlier);
            }
        }
    }

    private String calculateUserInfo(URI uri) {
        String username = System.getProperty("gradle.wrapperUser");
        String password = System.getProperty("gradle.wrapperPassword");
        if (username != null && password != null) {
            return username + ':' + password;
        }
        return uri.getUserInfo();
    }

    private String calculateUserAgent() {
        String javaVendor = System.getProperty("java.vendor");
        String javaVersion = System.getProperty("java.version");
        String javaVendorVersion = System.getProperty("java.vm.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        return String.format("%s/%s (%s;%s;%s) (%s;%s;%s)", appName, appVersion, osName, osVersion, osArch, javaVendor, javaVersion, javaVendorVersion);
    }

    private static class ProxyAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                    System.getProperty("http.proxyUser"), System.getProperty(
                    "http.proxyPassword", "").toCharArray());
        }
    }
}
