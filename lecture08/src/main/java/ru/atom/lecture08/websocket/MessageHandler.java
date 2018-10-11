package ru.atom.lecture08.websocket;

import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.User;
import ru.atom.lecture08.websocket.service.ChatService;
import ru.atom.lecture08.websocket.util.JsonHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private ChatService chatService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
    private static Set<WebSocketSession> chatEndpoints = new HashSet<>();
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.session = session;
        System.out.println("Socket Connected: " + this.session);
        chatEndpoints.add(this.session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String topic = JsonHelper.getJsonNode(payload).get("topic").textValue().toLowerCase();
        switch (topic) {
            case "login":
                broadcast(login(payload));
                break;
            case "history":
                // TODO Can't load history until bug with lazy loading will be fixed.
                broadcast(history());
                break;
            case "logout":
                broadcast(logout(payload));
                try {
                    this.session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "message":
                broadcast(message(payload));
                break;
            default:
                broadcast(new TextMessage("Unknown topic!"));
                break;
        }
        log.info("Received " + payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.error("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
        chatEndpoints.remove(session);
    }

    private TextMessage login(String payload) {
        String user = JsonHelper.getJsonNode(payload).get("data").get("user").textValue();
        if (user.length() < 1) {
            return new TextMessage("<script>alert(\"Too short username!\");</script>");
        }
        if (user.length() > 20) {
            return new TextMessage("<script>alert(\"Too long username!\");</script>");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(user);
        if (alreadyLoggedIn != null) {
            return new TextMessage("<script>alert(\"This user already logged in!\");</script>");
        }
        chatService.login(user);
        return new TextMessage("[" + simpleDateFormat.format(new Date()) + "] User \'" + user
                + "\' is logged in!");
    }

    @NotNull
    private TextMessage logout(String payload) {
        String user = JsonHelper.getJsonNode(payload).get("data").get("user").textValue();
        User newUser = chatService.getLoggedIn(user);
        if (newUser == null) {
            return new TextMessage("<script>alert(\"User \'" + user + "\' is not logged in :(\");</script>");
        }
        chatService.logout(newUser);
        log.info("[" + user + "] logged out");
        return new TextMessage("[" + simpleDateFormat.format(new Date()) + "] User \'" + user
                + "\' is logged out!");
    }

    @NotNull
    private TextMessage history() {
        List<Message> chatHistory = chatService.getAllMessages();
        String responseBody = chatHistory.stream()
                .map(m -> "[" + simpleDateFormat.format(new Date()) + " from \"" + m.getUser().getLogin() + "\"]\t "
                        + m.getValue())
                .collect(Collectors.joining("\n"));
        return new TextMessage(responseBody);
    }

    @NotNull
    private TextMessage message(String payload) {
        String user = JsonHelper.getJsonNode(payload).get("data").get("user").textValue();
        String msg = JsonHelper.getJsonNode(payload).get("data").get("msg").textValue();
        if (user == null) {
            return new TextMessage("<script>alert(\"Name not provided!\");</script>");
        }

        if (msg == null) {
            return new TextMessage("<script>alert(\"Message not provided!\");</script>");
        }

        if (msg.length() > 140) {
            return new TextMessage("<script>alert(\"Too long message!\");</script>");
        }

        List<User> authors = chatService.getAllUsers();
        if (authors.stream().noneMatch(chatUser -> chatUser.getLogin().equals(user))) {
            return new TextMessage("<script>alert(\"User \'" + user + "\' is not logged in\");</script>");
        }

        chatService.sendMessage(user, msg);
        log.info("[" + user + "]: " + msg);
        return new TextMessage("[" + simpleDateFormat.format(new Date()) + " from \"" + user + "\"]:\t " + msg);
    }

    private static void broadcast(TextMessage message) {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}