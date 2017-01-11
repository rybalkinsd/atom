package ru.atom.network;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static final int PARALLELISM_LEVEL = 16;
    private static ConcurrentHashMap<Player, Session> pool = new ConcurrentHashMap<>();

    public static void putIfAbsent(Player player, Session session) {
        pool.putIfAbsent(player, session);
    }

    public static void broadcast(String msg) {
        pool.forEachValue(PARALLELISM_LEVEL, session -> {
            if (session.isOpen()) {
                try {
                    session.getRemote().sendString(msg);
                } catch (IOException ignored) { }
            }
        });
    }

    public static void shutdown() {
        pool.forEachValue(PARALLELISM_LEVEL, session -> {
            if (session.isOpen()) {
                session.close();
            }
        });
    }

}
