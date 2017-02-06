package ru.atom;

import org.eclipse.jetty.websocket.api.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sergey on 2/6/17.
 */
public class SessionMock implements Session {
    private static AtomicInteger number = new AtomicInteger();

    private int hash = number.incrementAndGet();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionMock that = (SessionMock) o;

        return hash == that.hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(CloseStatus closeStatus) {

    }

    @Override
    public void close(int statusCode, String reason) {

    }

    @Override
    public void disconnect() throws IOException {

    }

    @Override
    public long getIdleTimeout() {
        return 0;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public WebSocketPolicy getPolicy() {
        return null;
    }

    @Override
    public String getProtocolVersion() {
        return null;
    }

    @Override
    public RemoteEndpoint getRemote() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public UpgradeRequest getUpgradeRequest() {
        return null;
    }

    @Override
    public UpgradeResponse getUpgradeResponse() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void setIdleTimeout(long ms) {

    }

    @Override
    public SuspendToken suspend() {
        return null;
    }
}
