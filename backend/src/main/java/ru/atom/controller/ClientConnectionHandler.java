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
import java.util.List;
import java.util.Map;

public class ClientConnectionHandler extends WebSocketAdapter {
    private final static Logger log = LogManager.getLogger(ClientConnectionHandler.class);
    private final GameSessionManager sessionManager;
    private final Broker broker;

    public ClientConnectionHandler() {
        sessionManager = GameSessionManager.getInstance();
        broker = Broker.getInstance();
    }

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        log.info("Socket Connected: " + session);
        Map<String, List<String>> parameterMap = session.getUpgradeRequest().getParameterMap();
        List<String> login = parameterMap.get("name");
        if(login != null){
            sessionManager.register(new Player(login.get(0), session));
        } else {
            log.error("No login provided");
        }
    }

    @Override
    public void onWebSocketText(String message) {
        log.info("Received TEXT message: " + message);

        try {
            broker.receive(getSession(), message);
        } catch (Exception e) {
            log.error("Recieve message failed with", e);
        }
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


    static Servlet makeServlet() {
        return new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ClientConnectionHandler.class);
            }
        };
    }
}
