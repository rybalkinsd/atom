package ru.atom.gameserver.component;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.gameserver.gsession.GameSession;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.service.GameRepository;
import ru.atom.gameserver.service.MatchMakerService;
import ru.atom.gameserver.util.JsonHelper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private final Map<WebSocketSession, Long> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MatchMakerService matchMakerService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        sessionMap.put(session, idLoginPair.getKey());
        gameRepository.getGameById(idLoginPair.getKey()).addPlayer(idLoginPair.getValue());
        logger.info("new ws connection gameid=" + idLoginPair.getKey() + " login=" + idLoginPair.getValue());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        sessionMap.remove(session);
        Long id = idLoginPair.getKey();
        GameSession gameSession = gameRepository.getGameById(id);
        if (gameSession.removePlayer(idLoginPair.getValue())) {
            gameRepository.deleteGame(id);
            gameSession.stop();
            logger.info("delete game with id=" + id);
        }
        matchMakerService.disconnectionWithPlayer(idLoginPair.getValue());
        logger.info("ws connection has been closed with status code " + status.getCode());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = JsonHelper.fromJson(textMessage.getPayload(), Message.class);
        gameRepository.getGameById(sessionMap.get(session)).messagesOffering().offerMessage(message);
        Pair<Long, String> idLoginPair = getParameters(session.getUri().toString());
        //logger.info("text message has been received from " + idLoginPair.getValue());
    }

    public void sendMessage(long gameId, Message message) {
        sessionMap.forEach((ws, id) -> {
            if (id.equals(gameId)) {
                sendMessage(ws, message);
            }
        });
    }

    public void sendMessage(long gameId, String login, Message message) {
        for (Map.Entry<WebSocketSession, Long> entry : sessionMap.entrySet()) {
            Pair<Long, String> idLoginPair = getParameters(entry.getKey().getUri().toString());
            if (entry.getValue().equals(gameId) && idLoginPair.getValue().equals(login)) {
                sendMessage(entry.getKey(), message);
                return;
            }
        }
    }

    public void sendGameOver(Long gameId, int playerId) {
        if (playerId != -1) {
            String winnerLogin = gameRepository.getGameById(gameId).getPlayerLogin(playerId);
            matchMakerService.sendGameOver(winnerLogin);
        }
    }

    private void sendMessage(WebSocketSession session, Message message) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(JsonHelper.toJson(message)));
            } catch (IOException e) {
                logger.warn("warn1: " + e.getMessage());
            } catch (Exception e) {
                logger.warn("warn2: " + e.getMessage());
            }
        } else {
            logger.info("wb has been closed!");
        }
    }

    private Pair<Long, String> getParameters(String uri) {
        String uriParameters = uri.split("[?]")[1];
        String[] parameters = uriParameters.split("[&]");
        Long gameId = Long.valueOf(parameters[0].split("[=]")[1]);
        String login = parameters[1].split("[=]")[1];
        return new Pair<>(gameId, login);
    }

}
