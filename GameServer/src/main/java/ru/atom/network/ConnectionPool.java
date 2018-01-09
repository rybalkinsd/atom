package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private static final int PARALLELISM_LEVEL = 2;

    private final ConcurrentHashMap<String, WebSocketSession> pool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public void send(WebSocketSession session, String msg) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(@NotNull String msg) {
        pool.entrySet().stream().forEach(sessionEntry -> send(sessionEntry.getValue(), msg));
    }

    public void shutdown() {
        pool.entrySet().stream().forEach(sessionEntry -> {
            if (sessionEntry.getValue().isOpen()) {
                try {
                    sessionEntry.getValue().close();
                } catch (IOException ignored) {
                }
            }
        });
    }

    public String getPlayer(WebSocketSession session) {
        return pool.entrySet().stream()
                .filter(entry -> entry.getValue().equals(session))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    public WebSocketSession getSession(String player) {
        return pool.get(player);
    }

    public boolean add(WebSocketSession session, String player) {
        if (getSession(player) != null) {
            return false;
        }
        if (pool.putIfAbsent(player, session) == null) {
            log.info("{} joined", player);
        }
        return true;
    }

    public void remove(String player) {
        try {
            pool.get(player).close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        pool.remove(player);
    }

    public void remove(WebSocketSession session) {
        try {
            session.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        pool.remove(session);
    }
}
