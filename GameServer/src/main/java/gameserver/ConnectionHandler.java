package gameserver;

import dto.PossesDto;
import gamesession.GameSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import websocket.Broker;
import websocket.ConnectionPool;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String[] name = session.getUri().getQuery().split("=");
        ConnectionPool.getInstance().add(session, name[2]);
        System.out.println("Socket Connected: " + session);
        GameSession.addPlayer(name[2]);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Broker.getInstance().receive(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
        ConnectionPool.getInstance().remove(session);
    }
}
