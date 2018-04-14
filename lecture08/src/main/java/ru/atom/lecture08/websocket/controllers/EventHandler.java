package ru.atom.lecture08.websocket.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.message.HistoryMessage;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.queues.MessagesQueue;
import ru.atom.lecture08.websocket.queues.UsersOnline;
import ru.atom.lecture08.websocket.util.JsonHelper;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        try {
            HistoryMessage received = JsonHelper.fromJson(msg, HistoryMessage.class);
            if (received.getTopic().equals("history")) {
                StringBuilder history = new StringBuilder();
                for (String i: MessagesQueue.getMessages()) {
                    history.append(i).append('\n');
                }
                session.sendMessage(new TextMessage(history.toString()));
            } else {
                if (received.getTopic().equals("online")) {
                    StringBuilder online = new StringBuilder();
                    for (String i: UsersOnline.getUsersOnline()) {
                        online.append(i).append('\n');
                    }
                    session.sendMessage(new TextMessage(online.toString()));
                }
            }
            return;
        } catch (Exception e){
        }
        try {
            Message received = JsonHelper.fromJson(msg, Message.class);
            if (received.getTopic().equals("message")) {
                session.sendMessage(new TextMessage(received.toString()));
            }
        } catch (Exception e){
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
