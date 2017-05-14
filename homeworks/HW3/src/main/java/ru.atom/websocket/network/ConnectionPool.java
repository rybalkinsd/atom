package ru.atom.websocket.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.jetbrains.annotations.NotNull;
import ru.atom.websocket.server.GameManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private static final int PARALLELISM_LEVEL = 1;

    private final ConcurrentHashMap<Session, String> pool;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        pool = new ConcurrentHashMap<>();
    }

    public void loggingEnterance(Session sess) {
        log.info("====================================");
        log.info("King of party: {}", pool.get(sess));
        pool.forEachKey(PARALLELISM_LEVEL, session -> {
            log.info("Session {} ", session);
            log.info("is opened: {}  by {}", session.isOpen(), pool.get(session));
        });
        log.info("====================================");
    }

    public void send(@NotNull Session session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.getRemote().sendString(msg);
            } catch (WebSocketException wse) {
                log.warn(wse.getMessage());
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(@NotNull String msg) {
        pool.forEachKey(PARALLELISM_LEVEL, session -> send(session, msg));
    }

    // TODO: 08.05.17   need to be threadSafe
    public void localBroadcast(@NotNull ConcurrentHashMap<Session, String> localPool, @NotNull String msg) {
        localPool.forEachKey(PARALLELISM_LEVEL, session -> {
            if (pool.get(session) == localPool.get(session)) {
                send(session,msg);
            }
        });
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
        if (!verifyLogin(player)) {
            return;
        }
        log.info("pool.size() before add: {}", pool.size());
        if (pool.putIfAbsent(session, player) == null) {
            log.info("{} joined", player);
            log.info("pool.size() after add: {}", pool.size());
            GameManager.getInstance().addPlayer(session, player);
        }
    }

    private boolean verifyLogin(String login) {
        // TODO: 04.05.17
        return true;
    }

    public void remove(Session session) {
        log.info("pool.size() before remove: {}", pool.size());
        pool.remove(session);
        log.info("pool.size() after remove: {}", pool.size());
        GameManager.getInstance().removePlayer(session);
    }

//    private int getGameId(Session session) {
//       int idGame =  gamesPool.entrySet().stream().filter(entry -> {
//            return entry.getValue().contains(session);
//        }).findFirst().get().getKey();
//       return idGame;
//    }
}
