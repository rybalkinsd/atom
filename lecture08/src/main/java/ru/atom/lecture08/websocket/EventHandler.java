package ru.atom.lecture08.websocket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.util.JsonHelper;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("GG");
        Message received = JsonHelper.fromJson(message.toString(), Message.class);
        if (received.getTopic().equals("history")) {
                String history = new String();
                for (String i: MessagesQueue.getMessages()) {
                    history += i + '\n';
                }
                session.sendMessage(new TextMessage(history));
        } else {
            if (received.getTopic().equals("message")) {
                MessagesQueue.getMessages().add(received.getData());
                session.sendMessage(new TextMessage(received.getData()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
