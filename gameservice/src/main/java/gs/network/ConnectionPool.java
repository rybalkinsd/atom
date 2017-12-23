package gs.network;

import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();

    private final ConcurrentHashMap<WebSocketSession, String> pool;

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void send(@NotNull WebSocketSession session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException ignored) {
            }
        }
    }

    public void add(WebSocketSession session, String player) {
        if (pool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
        }
    }
}
