package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.lecture08.websocket.message.SocketMessage;
import ru.atom.lecture08.websocket.message.Topic;
import ru.atom.lecture08.websocket.service.ChatService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SessionNotifier {

    private static final Logger log = LoggerFactory.getLogger(SessionNotifier.class);
    private final ChatService chatService;
    private Map<WebSocketSession, Boolean> sessions = new ConcurrentHashMap<>();

    @Autowired
    public SessionNotifier(ChatService chatService) {
        this.chatService = chatService;
    }

    public void addSession(WebSocketSession session) {
        sessions.put(session, Boolean.FALSE);
    }

    public void deleteSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void notifyAllSessions(SocketMessage socketMessage) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String author = "";
        String msg = "";
        switch (socketMessage.getTopic()) {
            case LOGIN:
                author = "admin";
                msg = "[<b style=\" color:"
                        + chatService.getUserColor(socketMessage.getLogin()) + ";\">" + socketMessage.getLogin() + "</b>] logged in";
                break;
            case LOGOUT:
                author = "admin";
                msg = "[<b style=\" color:"
                        + chatService.getUserColor(socketMessage.getLogin()) + ";\">" + socketMessage.getLogin() + "</b>] logged out";
                break;
            case MESSAGE:
                author = socketMessage.getLogin();
                msg = socketMessage.getMsg();
                break;
        }
        String response = "\n<font color=\"grey\">" + dateFormat.format(socketMessage.getDate()) + "</font>"
                + " <b style=\" color:" + chatService.getUserColor(author) + ";\">" +
                author + "</b>: " + msg;
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage value = new SocketMessage(Topic.MESSAGE, author, response);
        TextMessage toSend = new TextMessage(objectMapper.writeValueAsString(value));
        sessions.forEach((s, b) -> {
            if (b) {
                try {
                    s.sendMessage(toSend);
                } catch (IOException e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        });

    }

    public void setSessionStatus(WebSocketSession webSession, boolean status) {
        sessions.replace(webSession, status);
    }
}
