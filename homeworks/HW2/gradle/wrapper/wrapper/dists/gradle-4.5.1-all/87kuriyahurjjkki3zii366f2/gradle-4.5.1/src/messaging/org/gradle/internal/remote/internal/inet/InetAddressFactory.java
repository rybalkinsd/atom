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
package org.gradle.internal.remote.internal.inet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides information on how two processes on this machine can communicate via IP addresses
 */
public class InetAddressFactory {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Object lock = new Object();
    private List<InetAddress> communicationAddresses;
    private InetAddress localBindingAddress;
    private InetAddresses inetAddresses;
    private boolean initialized;
    private String hostName;

    public String getHostname() {
        synchronized (lock) {
            if (hostName == null) {
                try {
                    hostName = InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    hostName = getCommunicationAddresses().get(0).toString();
                }
            }
            return hostName;
        }
    }

    /**
     * Determines if the IP address can be used for communication with this machine
     */
    public boolean isCommunicationAddress(InetAddress address) {
        try {
            synchronized (lock) {
                init();
                return communicationAddresses.contains(address);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not determine the IP addresses for this machine.", e);
        }
    }

    /**
     * Locates the possible IP addresses which can be used to communicate with this machine.
     *
     * Loopback addresses are preferred.
     */
    public List<InetAddress> getCommunicationAddresses() {
        try {
            synchronized (lock) {
                init();
                return communicationAddresses;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not determine the local IP addresses for this machine.", e);
        }
    }

    public InetAddress getLocalBindingAddress() {
        try {
            synchronized (lock) {
                init();
                return localBindingAddress;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not determine a usable local IP for this machine.", e);
        }
    }

    private void init() throws Exception {
        if (initialized) {
            return;
        }

        initialized = true;
        if (inetAddresses == null) { // For testing
            inetAddresses = new InetAddresses();
        }
        localBindingAddress = new InetSocketAddress(0).getAddress();

        findCommunicationAddresses();

        handleOpenshift();
    }

    private void handleOpenshift() {
        InetAddress openshiftBindAddress = findOpenshiftAddresses();
        if (openshiftBindAddress != null) {
            localBindingAddress = openshiftBindAddress;
            communicationAddresses.add(openshiftBindAddress);
        }
    }


    private InetAddress findOpenshiftAddresses() {
        for (String key : System.getenv().keySet()) {
            if (key.startsWith("OPENSHIFT_") && key.endsWith("_IP")) {
                String ipAddress = System.getenv(key);
                logger.debug("OPENSHIFT IP environment variable {} detected. Using IP address {}.", key, ipAddress);
                try {
                    return InetAddress.getByName(ipAddress);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(String.format("Unable to use OPENSHIFT IP - invalid IP address '%s' specified in environment variable %s.", ipAddress, key), e);
                }
            }
        }
        return null;
    }

    private void findCommunicationAddresses() throws UnknownHostException {
        communicationAddresses = new ArrayList<InetAddress>();
        if (inetAddresses.getLoopback().isEmpty()) {
            if (inetAddresses.getRemote().isEmpty()) {
                InetAddress fallback = InetAddress.getByName(null);
                logger.debug("No loopback addresses, using fallback {}", fallback);
                communicationAddresses.add(fallback);
            } else {
                logger.debug("No loopback addresses, using remote addresses instead.");
                communicationAddresses.addAll(inetAddresses.getRemote());
            }
        } else {
            communicationAddresses.addAll(inetAddresses.getLoopback());
        }
    }
}
