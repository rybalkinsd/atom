package ru.atom.chat.socket.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SessionsList {

    private static class MatchedSocked {
        private WebSocketSession session;
        private String user;

        MatchedSocked(@NotNull WebSocketSession session, String user) {
            this.session = session;
            this.user = user;
        }

        public WebSocketSession getSession() {
            return session;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    private static Logger log = LoggerFactory.getLogger(SessionsList.class);
    private static List<MatchedSocked> sessions = new CopyOnWriteArrayList<>();

    public static void addSession(WebSocketSession session) {
        sessions.add(new MatchedSocked(session, null));
    }

    public static void deleteSession(WebSocketSession session) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSession().equals(session)) {
                sessions.remove(i);
                i--;
            }
        }
    }

    public static WebSocketSession finByUsername(@NotNull String name) {
        for (MatchedSocked session : sessions) {
            if (name.equals(session.getUser()))
                return session.getSession();
        }
        return null;
    }

    public static void sendAll(String message) {
        for (MatchedSocked session : sessions) {
            try {
                session.getSession().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Cant send message to socket " + session.getSession().getId());
            }
        }
    }

    public static void sendAllBy(String message, WebSocketSession sender) {
        for (MatchedSocked session : sessions) {
            if (sender.equals(session.getSession()))
                continue;
            try {
                session.getSession().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Cant send message to socket " + session.getSession().getId());
            }
        }
    }

    public static void matchSessionWithName(@NotNull WebSocketSession socket, @NotNull String name) {
        for (MatchedSocked session : sessions) {
            if (socket.equals(session.getSession())) {
                session.setUser(name);
                return;
            }
        }
        log.warn("Tried to match session with name, but didn`t find session.");
    }

    public static void unfastenSessionWithName(@NotNull String name) {
        for (MatchedSocked session : sessions) {
            if (name.equals(session.getUser())) {
                session.setUser(null);
                return;
            }
        }
        log.warn("Tried to unfasten session with name, but cant find session.");
    }
}
