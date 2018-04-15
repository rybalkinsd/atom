package ru.atom.lecture08.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SessionNotifier {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public void notifyAllSessions(TextMessage textMessage) throws Exception {
        for(WebSocketSession session: sessions) {
                session.sendMessage(textMessage);
        }
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void deleteSession(WebSocketSession session) {
        sessions.remove(session);
    }
}
