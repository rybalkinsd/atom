package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;
import ru.atom.lecture08.websocket.message.SocketMessage;
import ru.atom.lecture08.websocket.message.Topic;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.User;
import ru.atom.lecture08.websocket.service.ChatService;

import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    private final SessionNotifier sessionNotifier;

    private final ChatService chatService;

    @Autowired
    public EventHandler(SessionNotifier sessionNotifier, ChatService chatService) {
        this.sessionNotifier = sessionNotifier;
        this.chatService = chatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionNotifier.addSession(session);
        log.info("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received " + message.getPayload());
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage m = objectMapper.readValue(message.getPayload(), SocketMessage.class);
        SocketMessage socketMessage;
        boolean success = false;
        switch (m.getTopic()) {
            case LOGIN:
                socketMessage = login(m);
                success = socketMessage.getTopic() == Topic.SUCCESS;
                if (success) sessionNotifier.setSessionStatus(session, true);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(socketMessage)));
                break;
            case LOGOUT:
                if (success) sessionNotifier.setSessionStatus(session, false);
                //TODO: logout
                break;
            case MESSAGE:
                socketMessage = message(m);
                success = socketMessage.getTopic() == Topic.SUCCESS;
                if (!success)
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(socketMessage)));
                break;
        }
        if (success) sessionNotifier.notifyAllSessions(m);
    }

    private SocketMessage message(SocketMessage socketMessage) {
        String name = socketMessage.getLogin();
        SocketMessage value = new SocketMessage(Topic.FAIL, "admin", "");
        User loggedIn = chatService.getLoggedIn(name);
        if (loggedIn == null)
            return value.setMsg("Log in first.");
         else
            chatService.putMessage(socketMessage);
        return value.setTopic(Topic.SUCCESS);
    }

    private SocketMessage login(SocketMessage message) {
        Date now = new Date();
        SocketMessage value = new SocketMessage(Topic.FAIL, "admin", "");
        String name = message.getLogin();
        if (!name.equals(HtmlUtils.htmlEscape(name)) || name.contains(":")) {
            value.setMsg("Bad symbols in your name, sorry :(");
        }
        if (name.length() < 3) {
            value.setMsg("Your name is too short, sorry :(");
        }
        if (name.length() > 20) {
            value.setMsg("Your name is too long, sorry :(");
        }
        if (chatService.getLoggedIn(name) != null) {
            value.setMsg("Already logged in");
        }
        if(!value.getMsg().isEmpty()) return value;
        Random r = new Random();
        int hh = r.nextInt(360);
        int ss = r.nextInt(100);

        int ll = 30 + r.nextInt(40);

        String color = "hsl(" + hh + "," + ss + "%," + ll + "%)";
        chatService.login(name, color);


        value.setMsg(chatService.getAllMessages().stream()
                .map(Message::format)
                .collect(Collectors.joining("\n"))).setTopic(Topic.SUCCESS);

        chatService.putMessage("admin", "[<b style=\" color:"
                + color + ";\">" + name + "</b>] logged in", now);

        log.info(name + " post login");
        return value;

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        sessionNotifier.deleteSession(session);
    }

}
