package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;
import ru.atom.network.message.Broker;

public class ClientConnectionHandler extends WebSocketAdapter {
    private final static Logger log = LogManager.getLogger(ClientConnectionHandler.class);
    private final static Broker broker = new Broker();

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        log.info("Socket Connected: " + session);
        ConnectionPool.putIfAbsent(new Player("first"), session);
    }

    @Override
    public void onWebSocketText(String message) {
        log.info("Received TEXT message: " + message);
        broker.recieve(getSession(), message);
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
