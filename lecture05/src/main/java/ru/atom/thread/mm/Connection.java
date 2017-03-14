package ru.atom.thread.mm;

/**
 * Created by sergey on 3/14/17.
 */
public class Connection {
    private final long playerId;
    private final String name;

    public Connection(long playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                '}';
    }
}
