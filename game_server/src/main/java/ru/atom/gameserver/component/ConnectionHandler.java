package ru.atom.gameserver.component;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private Map<Long, Set<WebSocketSession>> sessionUnion = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        if (!sessionUnion.containsKey(idLoginPair.getKey())) {
            sessionUnion.put(idLoginPair.getKey(), new HashSet<>());
        }
        sessionUnion.get(idLoginPair.getKey()).add(session);

        logger.info("new ws connection gameid=" + idLoginPair.getKey() + " login=" + idLoginPair.getValue());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        logger.info("ws connection has been closed with status code " + status.getCode());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        logger.info("text message has been received");
    }

    public void sendMessage(String jsonReplica) {

        logger.info("text message has been sent");
    }

    private Pair<Long, String> getParameters(String uri) {
        String uriParameters = uri.split("[?]")[1];
        String[] parameters = uriParameters.split("[&]");
        Long gameId = Long.valueOf(parameters[0].split("[=]")[1]);
        String login = parameters[1].split("[=]")[1];
        return new Pair<>(gameId, login);
    }

}
