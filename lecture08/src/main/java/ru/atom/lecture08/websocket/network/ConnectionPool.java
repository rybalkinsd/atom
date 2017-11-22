package ru.atom.lecture08.websocket.network;

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
    private static final int PARALLELISM_LEVEL = 4;

    private final ConcurrentHashMap<WebSocketSession, String> pool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public void send(@NotNull WebSocketSession session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(msg));
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
                try {
                    session.close();
                } catch (IOException ignored) {
                }
            }
        });
    }

    public String getPlayer(WebSocketSession session) {
        return pool.get(session);
    }

    public WebSocketSession getSession(String player) {
        return pool.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(null);
    }

    public void add(WebSocketSession session, String player) {
        if (pool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
        }
    }

    public void remove(WebSocketSession session) {
        pool.remove(session);
    }
}
