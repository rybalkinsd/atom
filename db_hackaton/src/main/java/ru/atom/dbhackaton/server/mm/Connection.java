package ru.atom.dbhackaton.server.mm;

/**
 * Created by konstantin on 19.04.17.
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
