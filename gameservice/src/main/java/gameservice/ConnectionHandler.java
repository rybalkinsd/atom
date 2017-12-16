package gameservice;

import gameservice.network.Message;
import gameservice.network.Topic;
import gameservice.model.GameSession;
import gameservice.network.Broker;
import gameservice.network.ConnectionPool;
import gameservice.gamemechanics.Action;
import gameservice.gamemechanics.GameMechanics;
import gameservice.network.JsonHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    @Autowired
    Sessions storage;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        String idParam = parameters.get("gameId").toString();
        String name = parameters.get("name").toString();
        name = name.substring(1, name.length() - 1);
        long gameId = Long.parseLong(idParam.substring(1, idParam.length() - 1));
        storage.addByGameId(gameId, session);
        GameSession gameSession = storage.getSessionById(gameId);
        ConnectionPool.getInstance().add(session, name);
        int data = storage.getId(gameId);
        Broker.getInstance().send(session, Topic.POSSESS, data);
        gameSession.addPlayer(data);
        storage.putPawnToSocket(session, gameSession.getById(gameSession.getLastId()));
        Broker.getInstance().send(session, Topic.REPLICA, storage.getSessionById(gameId).getGameObjects());
        if (gameSession.getPlayerCount() == storage.getWebsocketsByGameSession(gameSession).size()) {
            GameMechanics gameMechanics = new GameMechanics(gameSession);
            storage.putTickables(gameMechanics, gameSession);
            gameMechanics.setName("gameId : " + gameId);
            gameMechanics.start();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message msg = JsonHelper.fromJson(message.getPayload(), Message.class);
        log.info(msg.toString());
        Action action = new Action(msg.getTopic(), storage.getPawnBySocket(session), msg.getData());
        storage.putAction(storage.getByWebsocket(session), action);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
