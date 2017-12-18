package gs;

import gs.message.Message;
import gs.message.Topic;
import gs.model.GameSession;
import gs.network.Broker;
import gs.network.ConnectionPool;
import gs.storage.SessionStorage;
import gs.ticker.Action;
import gs.ticker.Ticker;
import gs.util.JsonHelper;
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
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    SessionStorage storage;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        String idParam = parameters.get("gameId").toString();
        String name = parameters.get("name").toString();
        name = name.substring(1, name.length() - 1);
        long gameId = Long.parseLong(idParam.substring(1, idParam.length() - 1));
        GameSession gameSession = storage.getSessionById(gameId);

        if (!gameSession.isReady()) {
            storage.addByGameId(gameId, session);
            ConnectionPool.getInstance().add(session, name);
            int data = storage.getId(gameId);
            Broker.getInstance().send(session, Topic.POSSESS, data);
            gameSession.addPlayer(data);
            storage.putGirlToSocket(session, gameSession.getById(gameSession.getLastId()));
            Broker.getInstance().send(session, Topic.REPLICA,
                    storage.getSessionById(gameId).getGameObjects());
            if (gameSession.getPlayerCount()
                    == storage.getWebsocketsByGameSession(gameSession).size()) {
                Ticker ticker = new Ticker(gameSession);
                storage.putTicker(ticker, gameSession);
                ticker.setName("gameId : " + gameId);
                ticker.begin();
                ticker.start();
            }
        } else {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (storage.getByWebsocket(session).isReady()) {
            Message msg = JsonHelper.fromJson(message.getPayload(), Message.class);
            Action action = new Action(msg.getTopic(),
                    storage.getGirlBySocket(session), msg.getData());
            storage.putAction(storage.getByWebsocket(session), action);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" +
                closeStatus.getCode() + "] " + closeStatus.getReason());
        storage.removeWebsocket(session);
        super.afterConnectionClosed(session, closeStatus);
    }

}
