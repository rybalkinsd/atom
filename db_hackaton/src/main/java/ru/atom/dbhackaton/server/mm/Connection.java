package ru.atom.dbhackaton.server.mm;


import java.util.concurrent.atomic.AtomicLong;

public class Connection {
    private final String token;
    private AtomicLong sessionId = new AtomicLong(-1);

    public Connection(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        return token.equals(that.token);

    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    public void setSessionId(long sessionId) {
        this.sessionId.set(sessionId);
    }

    public long getSessionIdValue() {
        return sessionId.get();
    }

    public boolean idNull() {
        return sessionId.equals(-1);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "token='" + token + '\'' +
                ", sessionId=" + sessionId +
                '}';
    }
}
