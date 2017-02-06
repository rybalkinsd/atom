package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.atom.network.Player;
import ru.atom.network.message.Broker;

import javax.servlet.Servlet;

public class ClientConnectionHandler extends WebSocketAdapter {
    private final static Logger log = LogManager.getLogger(ClientConnectionHandler.class);

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        log.info("Socket Connected: " + session);
        GameSessionManager.register(new Player("first", session));
    }

    @Override
    public void onWebSocketText(String message) {
        log.info("Received TEXT message: " + message);

        Broker.receive(getSession(), message);
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


    public static Servlet makeServlet() {
        return new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ClientConnectionHandler.class);
            }
        };
    }
}
