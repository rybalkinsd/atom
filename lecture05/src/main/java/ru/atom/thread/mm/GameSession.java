package ru.atom.thread.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
public class GameSession {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    private static AtomicLong idGenerator = new AtomicLong();

    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;
    private final long id = idGenerator.getAndIncrement();

    public GameSession(Connection[] connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }
}
