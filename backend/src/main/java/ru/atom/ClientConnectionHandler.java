package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;

public class ClientConnectionHandler extends WebSocketAdapter {
    private final static Logger log = LogManager.getLogger(ClientConnectionHandler.class);

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        log.info("Socket Connected: " + session);
        ConnectionPool.putIfAbsent(new Player("first"), session);
    }

    @Override
    public void onWebSocketText(String message) {
        log.info("Received TEXT message: " + message);
        ConnectionPool.broadcast("ping");
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        log.info("Socket Closed: [" + statusCode + "] " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        log.error("Socket error: ", cause);
    }
}
