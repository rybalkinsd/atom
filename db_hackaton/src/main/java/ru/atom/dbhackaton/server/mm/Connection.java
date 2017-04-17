package ru.atom.dbhackaton.server.mm;


import java.util.concurrent.atomic.AtomicLong;

public class Connection {
    private final String token;
    private final String name;
    private AtomicLong sessionId = new AtomicLong(-1);

    public Connection(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        if (!token.equals(that.token)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + name.hashCode();
        return result;
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
                ", name='" + name + '\'' +
                ", sessionId=" + sessionId +
                '}';
    }
}
