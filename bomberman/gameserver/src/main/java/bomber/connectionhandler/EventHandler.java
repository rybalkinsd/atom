package bomber.connectionhandler;

import bomber.connectionhandler.json.Json;
import bomber.gameservice.controller.GameController;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventHandler.class);
    private static final Map<Integer, Player> connectionPool = new HashMap<>();
    public static final String GAMEID_ARG = "gameId";
    public static final String NAME_ARG = "name";

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        connectionPool.put(session.hashCode(), uriSessionToPlayer(session.getUri(), session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info(message.getPayload());
        log.info("=============================================================================");
        GameController.getGameSession(connectionPool.get(session.hashCode()).getGameid()).getInputQueue()
                .add(Json.jsonToPlayerAction(session.hashCode(), message.getPayload()));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("here");
        System.out.println(session.hashCode());
        connectionPool.remove(session.hashCode());

        super.afterConnectionClosed(session, closeStatus);
    }

    public static void sendReplica(final int gameId) throws IOException {
        /*for (WebSocketSession session : HashMapUtil.getSessionsArrayByGameId(connectionPool, gameId))
            session.sendMessage(
                    new TextMessage(Json.replicaToJson(GameController.getGameSession(gameId).getReplica())));*/
        for (Integer id : connectionPool.keySet()) {
            if (connectionPool.get(id).getGameid() == gameId) {
                connectionPool.get(id).getWebSocketSession().sendMessage(
                        new TextMessage(Json.replicaToJson(GameController.getGameSession(gameId).getReplica())));
            }

        }
    }

    public static void sendPossess(final int playerId) throws IOException {
        connectionPool.get(playerId).getWebSocketSession().sendMessage(
                new TextMessage(Json.possesToJson(playerId)));
    }

    public static Player uriSessionToPlayer(final URI uri, WebSocketSession webSocketSession) {
        Player player = new Player(); //is id needed?
        player.setWebSocketSession(webSocketSession);
        for (String iter : uri.getQuery().split("&")) {
            if (iter.contains(GAMEID_ARG) && !(iter.indexOf("=") == iter.length() - 1)) {
                player.setGameid(Integer.parseInt(iter.substring(iter.indexOf("=") + 1, iter.length())));
            }
            if (iter.contains(NAME_ARG) && !(iter.indexOf("=") == iter.length() - 1)) {
                player.setName(iter.substring(iter.indexOf("=") + 1, iter.length()));
            }
        }
        return player;
    }

    public static List<Integer> getSessionIdList() {
        List<Integer> list = new ArrayList<>();
        for (Integer webSocketSession : connectionPool.keySet()) {
            list.add(webSocketSession.hashCode());

        }
        return list;
    }

    public static Map<Integer, Player> getConnectionPool() {
        return connectionPool;
    }
}