package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.message.SocketMessage;
import ru.atom.lecture08.websocket.message.Topic;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.service.ChatService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage m = objectMapper.readValue(message.getPayload(), SocketMessage.class);
        if(m.getTopic() == Topic.MESSAGE)
            chatService.putMessage(m);
        TextMessage msg = new TextMessage(chatService.getAllMessages().stream()
                .map(Message::format)
                .collect(Collectors.joining("\n")));
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(msg);
        }
        System.out.println("Received " + message.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        sessions.remove(session);
        super.afterConnectionClosed(session, closeStatus);
    }

}
