package ru.atom.gameserver.component;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.service.GameRepository;
import ru.atom.gameserver.util.JsonHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private final Map<Long, Set<WebSocketSession>> sessionUnion = new ConcurrentHashMap<>();

    @Autowired
    private GameRepository gameRepository;

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
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        Long gameId = idLoginPair.getKey();
        if (!sessionUnion.containsKey(gameId)) {
            logger.warn("connection handler has not gameId " + gameId);
            return;
        }
        Set<WebSocketSession> webSocketSessions = sessionUnion.get(gameId);
        webSocketSessions.remove(session);
        if (webSocketSessions.isEmpty()) {
            sessionUnion.remove(gameId);
        }

        logger.info("ws connection has been closed with status code " + status.getCode());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        Long gameId = idLoginPair.getKey();
        if (!sessionUnion.containsKey(gameId)) {
            logger.warn("connection handler has not gameId " + gameId);
            return;
        }
        Message message = JsonHelper.fromJson(textMessage.toString(), Message.class);
        gameRepository.getGameById(gameId).messagesOffering().offerMessage(message);
        logger.info("text message has been received");
    }

    public void sendMessage(long gameId, Message message) {
        if (!sessionUnion.containsKey(gameId)) {
            logger.warn("connection handler has not gameId " + gameId);
            return;
        }
        Set<WebSocketSession> webSocketSessions = sessionUnion.get(gameId);
        for (WebSocketSession webSocketSession : webSocketSessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(new TextMessage(JsonHelper.toJson(message)));
                } catch (IOException exception) {
                    logger.warn(exception.getMessage());
                }
            } else {
                logger.warn("web socket " + webSocketSession + " is not opened");
            }
        }
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
