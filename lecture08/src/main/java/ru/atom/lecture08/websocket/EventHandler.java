package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.message.Topic;

import java.io.BufferedWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {


    @Autowired
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Queue<Message> messages = new ConcurrentLinkedDeque<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Message m = objectMapper.readValue(message.getPayload(), Message.class);
        if(m.getTopic() == Topic.MESSAGE) {
            messages.add(m);
        }
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            TextMessage msg = new TextMessage(messages.stream()
                    .map(triplet -> "<font color=\"grey\">" + dateFormat.format(triplet.getDate()) + "</font>"
                            + " <b style=\" color:" + usersOnline.get(triplet.getLogin()) + ";\">"
                            + triplet.getLogin() + "</b>: " + triplet.getMsg())
                    .collect(Collectors.joining("\n")));
            session.sendMessage(msg);
            System.out.println("Received " + message.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
