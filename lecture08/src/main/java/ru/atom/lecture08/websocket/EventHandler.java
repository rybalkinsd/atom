package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;
import ru.atom.lecture08.websocket.message.SocketMessage;
import ru.atom.lecture08.websocket.message.Topic;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.service.ChatService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Autowired
    private SessionNotifier sessionNotifier;

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        if(session instanceof WebSocketServerSockJsSession)
            sessionNotifier.addSession(session);
        TextMessage msg = new TextMessage(chatService.getAllMessages().stream()
                .map(Message::format)
                .collect(Collectors.joining("\n")));
        session.sendMessage(msg);

        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage m = objectMapper.readValue(message.getPayload(), SocketMessage.class);
        if(m.getTopic() == Topic.MESSAGE)
            chatService.putMessage(m);
        TextMessage textMessage = new TextMessage("\n" + chatService.getLastMessage().format());
        sessionNotifier.notifyAllSessions(textMessage);
        System.out.println("Received " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        sessionNotifier.deleteSession(session);
    }

}
