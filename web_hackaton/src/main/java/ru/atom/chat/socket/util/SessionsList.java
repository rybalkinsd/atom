package ru.atom.chat.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SessionsList {
    private static Logger log = LoggerFactory.getLogger(SessionsList.class);

    private static List<WebSocketSession> sessions = new ArrayList<>();

    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public static void deleteSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public static void sendAll(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Cant send message to socket " + session.getId());
            }
        }
    }

    public static void sendAllBy(String message, WebSocketSession sender) {
        for (WebSocketSession session : sessions) {
            if (sender.equals(session))
                continue;
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Cant send message to socket " + session.getId());
            }
        }
    }
}
