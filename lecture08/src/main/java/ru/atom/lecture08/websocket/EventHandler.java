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

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;


import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;


@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket opened: " + session.getId());
        session.getAttributes().put("time",new Date());
        BlockingQueue<Message> queue = messageDao.loadHistory();
        /*
        String result = queue.stream().map(Message::getData)
                .reduce("", (e1,e2) -> e1 + "\n" + e2);
        */
        if (queue.isEmpty()) {
            session.getAttributes().put("topBorder",null);
            return;
        }
        session.getAttributes().put("topBorder",queue.peek().getTime());
        Map<String,Object> msg = new HashMap<>(4);
        msg.put("topic", Topic.History.toString());
        msg.put("data", queue.stream()
                .map(Message::getData)
                .collect(Collectors.toList()));

        session.sendMessage(new TextMessage(JsonHelper.toJson(msg)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Response response = JsonHelper.fromJson(message.getPayload(),Response.class);
        User user;
        List<String> result;
        switch (response.getTopic().toString()) {
            case "Enter":
                session.getAttributes().put("sender",userDao.getByLogin(response.getData().get("sender")));
                break;

            case "Say":
                user = (User)session.getAttributes().get("sender");
                messageDao.save(new Message(Topic.Say,"[" + user.getLogin() + "]: "
                        + response.getData().get("msg")).setUser(user));
                result = messageDao.updateGetList((Date) session.getAttributes().get("time"));
                session.getAttributes().put("time",new Date());

                Map<String,Object> msg = new HashMap<>(4);
                msg.put("topic", Topic.History.toString());
                msg.put("data", result);

                session.sendMessage(new TextMessage(JsonHelper.toJson(msg)));
                break;

            case "History":
                result = messageDao.updateGetList((Date) session.getAttributes().get("time"));
                session.getAttributes().put("time",new Date());

                Map<String,Object> mssg = new HashMap<>(4);
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
                Date border = (Date)session.getAttributes().get("topBorder");
                Map<String,Object> top = new HashMap<>(4);
                top.put("topic", Topic.Top.toString());
                if (border == null)
                    top.put("data", Collections.EMPTY_LIST);
                else {
                    List<Message> res = messageDao.getHistory(border);
                    if (res.size() == 0)
                        top.put("data", Collections.EMPTY_LIST);
                    else {
                        int batch = Math.min(res.size(),100);
                        res = res.subList(res.size() - batch , res.size());
                        session.getAttributes().put("topBorder",res.get(0).getTime());
                        top.put("data",res.stream()
                                .map(Message::getData)
                                .collect(Collectors.toList()));
                    }
                }
                session.sendMessage(new TextMessage(JsonHelper.toJson(top)));
                break;

            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        User user = (User)session.getAttributes().get("sender");
        userDao.setLoggedOut(user);
        super.afterConnectionClosed(session, closeStatus);
    }

}
