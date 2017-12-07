package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.util.JsonHelper;

import java.util.Vector;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler  {

    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        String[] params = query.split("&");
        String name = params[1].split("=")[1];
        Long gameId = Long.parseLong(params[0].split("=")[1]);
        log.info("new connection handled " + query);
        Vector<Message> messages = new Vector<Message>();
        InputMessages.getInstance().put(name, messages);
        GameSession.send(session, new Message(Topic.POSSESS,
                Long.toString(GameServerService.getGameSession(gameId).add(name, session))));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
        String query = session.getUri().getQuery();
        String[] params = query.split("&");
        String name = params[1].split("=")[1];
        Message message = JsonHelper.fromJson(msg.getPayload().toString(), Message.class);
        log.info("RECEIVED: " + message.getData());
        InputMessages.getInstance().get(name).add(message);


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
    }

}
