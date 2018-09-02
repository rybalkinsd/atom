package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.input.InputMessages;
import ru.atom.message.Message;
import ru.atom.service.GameServerService;
import ru.atom.service.GameSession;
import ru.atom.util.JsonHelper;
import ru.atom.util.QueryProcessor;

import java.util.HashMap;

@Component
public class ConnectionHandler extends TextWebSocketHandler implements WebSocketHandler  {

    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("New connection handled " + session.getUri().getQuery());
        HashMap<String, String> params = QueryProcessor.process(session.getUri().getQuery());
        GameSession gameSession = GameServerService.getGameSession(Long.parseLong(params.get("gameId")));
        if (gameSession == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        if (!ConnectionPool.getInstance().add(session, params.get("name"))) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        InputMessages.addEntry(params.get("name"));
        if (!GameServerService.getGameSession(Long.parseLong(params.get("gameId"))).add(params.get("name"))) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
        Broker.getInstance().receive(session, msg.getPayload());
        Message message = JsonHelper.fromJson(msg.getPayload().toString(), Message.class);


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        HashMap<String, String> params = QueryProcessor.process(session.getUri().getQuery());
        GameSession gameSession = GameServerService.getGameSession(Long.parseLong(params.get("gameId")));
        if (gameSession == null) {
            return;
        }
        gameSession.removePlayer(params.get("name"));
    }

}
