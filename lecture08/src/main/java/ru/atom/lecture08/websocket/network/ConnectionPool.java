package ru.atom.lecture08.websocket.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private static final int PARALLELISM_LEVEL = 4;

    private final ConcurrentHashMap<Session, String> pool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public void send(@NotNull Session session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.getRemote().sendString(msg);
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(@NotNull String msg) {
        pool.forEachKey(PARALLELISM_LEVEL, session -> send(session, msg));
    }

    public void shutdown() {
        pool.forEachKey(PARALLELISM_LEVEL, session -> {
            if (session.isOpen()) {
                session.close();
            }
        });
    }

    public String getPlayer(Session session) {
        return pool.get(session);
    }

    public Session getSession(String player) {
        return pool.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(null);
    }

    public void add(Session session, String player) {
        if (pool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
        }
    }

    public void remove(Session session) {
        pool.remove(session);
    }
}
