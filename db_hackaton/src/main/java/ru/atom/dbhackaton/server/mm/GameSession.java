package ru.atom.dbhackaton.server.mm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class GameSession {
    private static final Logger logger = LogManager.getLogger(GameSession.class);

    private static AtomicLong idGenerator = new AtomicLong();

    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;

    private final long id = idGenerator.getAndIncrement();


    public GameSession(Connection[] connections) {
        this.connections = connections;

    }

    public long getId() {
        return id;
    }

    public void sendIdToConnections(){
        for (Connection connection: connections) {
            connection.setSessionId(id);
        }
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                ", id=" + id +
                '}';
    }
}
