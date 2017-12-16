package boxes;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);
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

    public ConcurrentLinkedQueue<WebSocketSession> getSessionsWithGameId(int gameId) {

        ConcurrentLinkedQueue<WebSocketSession> result = new ConcurrentLinkedQueue<>();
        try {
            for (String player : pool.values()) {
                if (gameId == ConnectionPool.getInstance().getGameId(player)) {
                    result.offer(getSession(player));
                }
            }
        } catch (NullPointerException e) {
            log.warn("Mistake :(");
        }
        return result;
    }

    public ConcurrentLinkedQueue<String> getPlayersWithGameId(int gameId) {
        ConcurrentLinkedQueue<String> result = new ConcurrentLinkedQueue<>();
        try {
            for (String player : pool.values()) {
                if (gameId == ConnectionPool.getInstance().getGameId(player)) {
                    result.offer(player);
                }
            }
        } catch (NullPointerException e) {
            log.warn("Mistake :(");
        }
        return result;
    }

    public void add(WebSocketSession session, String player) {
        if (pool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
        }
    }

    public void remove(WebSocketSession session) {
        pool.remove(session);
    }

    public int getGameId(String player) {
        WebSocketSession session = getSession(player);
        String temp = session.getUri().getQuery();
        String[] tempStr = temp.split("&");
        String[] idStr = tempStr[0].split("=");
        return Integer.parseInt(idStr[1]);
    }
}

