package ru.atom.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.thread.mm.MatchMaker;

import java.util.Arrays;

/**
 * Created by sergey on 3/14/17.
 */
public class GameSession {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;

    public GameSession(Connection[] connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                '}';
    }
}
