package gs;

import gs.gamerepository.GameController;
import gs.replicator.Replicator;
import gs.network.Broker;
import gs.network.ConnectionPool;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class GameHandler extends TextWebSocketHandler implements WebSocketHandler {
    private String str = "{";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String str = session.getUri().toString();
        String name = str.substring(str.indexOf("&") + 6);
        GameController.getGameMechanics().getGs().addSession(session, name);
        GameController.getGameMechanics().getGs().increasePlayersInGame(name);
        ConnectionPool.getInstance().add(session, name);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Broker broker = new Broker();
        broker.receive(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (GameController.getGameMechanics().getGs().getAllSessions().contains(session)) {
            String player = ConnectionPool.getInstance().getPlayer(session);
            GameController.getGameMechanics().removePlayer(player);
        } else {
            String player = ConnectionPool.getInstance().getPlayer(session);
            GameController.getGameMechanics().removePlayer(player);
        }
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
