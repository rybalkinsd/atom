package ru.atom.lecture08.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.atom.lecture08.websocket.dao.MessageDao;
import ru.atom.lecture08.websocket.dao.UserDao;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.Response;
import ru.atom.lecture08.websocket.model.Topic;
import ru.atom.lecture08.websocket.model.User;
import ru.atom.lecture08.websocket.util.JsonHelper;
import java.util.Map;
import java.util.HashMap;


import java.util.Date;


@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.getAttributes().put("time",new Date());
        String result = messageDao.loadHistory();
        if (result == null) {
            return;
        }

        Map<String,String> msg = new HashMap<>(4);
        msg.put("topic", Topic.History.toString());
        msg.put("data", result);

        session.sendMessage(new TextMessage(JsonHelper.toJson(msg)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Response response = JsonHelper.fromJson(message.getPayload(),Response.class);
        User user;
        String result;
        switch (response.getTopic().toString()) {
            case "Enter":
                session.getAttributes().put("sender",userDao.getByLogin(response.getData().get("sender")));
                break;

            case "Say":
                user = (User)session.getAttributes().get("sender");
                messageDao.save(new Message(Topic.Say,"[" + user.getLogin() + "]: "
                        + response.getData().get("msg")).setUser(user));
                result = messageDao.loadHistory((Date) session.getAttributes().get("time"));
                session.getAttributes().put("time",new Date());

                Map<String,String> msg = new HashMap<>(4);
                msg.put("topic", Topic.History.toString());
                msg.put("data", result);

                session.sendMessage(new TextMessage(JsonHelper.toJson(msg)));
                break;

            case "History":
                result = messageDao.loadHistory((Date) session.getAttributes().get("time"));
                session.getAttributes().put("time",new Date());

                Map<String,String> mssg = new HashMap<>(4);
                mssg.put("topic", Topic.History.toString());
                mssg.put("data", result);

                //session.sendMessage(new TextMessage(result));
                session.sendMessage(new TextMessage(JsonHelper.toJson(mssg)));

                Map<String,String> online = new HashMap<>(4);
                online.put("topic", Topic.Users.toString());
                online.put("data", userDao.getUsersOnline());
                session.sendMessage(new TextMessage(JsonHelper.toJson(online)));

                break;

            case "Top":
                Map<String,String> top = new HashMap<>(4);
                top.put("topic", Topic.Top.toString());
                top.put("data", "You reached top\n");
                session.sendMessage(new TextMessage(JsonHelper.toJson(top)));
                System.out.println("User scrolled to top");
                break;

            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        User user = (User)session.getAttributes().get("sender");
        userDao.setLoggedOut(user);
        super.afterConnectionClosed(session, closeStatus);
    }

}
