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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        String result = messageDao.loadHistory().stream()
                .map(Message::getData)
                .reduce("", (e1,e2) -> e1 + "\n" + e2);
        session.sendMessage(new TextMessage(result));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Response response = JsonHelper.fromJson(message.getPayload(),Response.class);
        System.out.println(message.getPayload());
        User user;
        if (session.getAttributes().containsKey("user")) {
            user = (User) session.getAttributes().get("user");
        } else {
            user = userDao.getByLogin(response.getData().get("sender"));
            session.getAttributes().put("user",user);
        }
        switch (response.getTopic().toString()) {
            case "History":
                String result = messageDao.loadHistory().stream()
                        .map(Message::getData)
                        .reduce("", (e1,e2) -> e1 + "\n" + e2);
                session.sendMessage(new TextMessage(result));
                break;
            case "Say":
                messageDao.save(new Message(Topic.Say,"[" + user.getLogin() + "]: "
                        + response.getData().get("msg")).setUser(user));
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}
