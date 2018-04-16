package ru.atom.chat.socket.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import ru.atom.chat.dao.MessageDao;
import ru.atom.chat.dao.UserDao;
import ru.atom.chat.message.HrefHandler;
import ru.atom.chat.message.Message;
import ru.atom.chat.socket.message.request.messagedata.IncomingMessage;
import ru.atom.chat.socket.message.response.messagedata.ResponseData;
import ru.atom.chat.socket.message.response.messagedata.OutgoingMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingUser;
import ru.atom.chat.socket.topics.ResponseTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.user.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    public ResponseData say(IncomingMessage message) {

        User user = userDao.getByLogin(message.getSender());
        if (user == null)
            return new ResponseData(ResponseTopic.ERROR, "No such user:(");

        if (!user.passCheck(message.getPassword()))
            return new ResponseData(ResponseTopic.ERROR, "Wrong password");

        if (!user.getActive())
            return new ResponseData(ResponseTopic.ERROR, "User is logged out:(");

        if (!user.spamCheck())
            return new ResponseData(ResponseTopic.ERROR, "Spam");

        String checkedMsg = HtmlUtils.htmlEscape(message.getMsg());
        Message newMsg = new Message(user, HrefHandler.handleHref(checkedMsg));

        messageDao.save(newMsg);
        OutgoingMessage outgoingMessage = new OutgoingMessage(newMsg);
        return new ResponseData(ResponseTopic.OK, JsonHelper.toJson(outgoingMessage));
    }

    public String allMessages() {
        List<Message> messages = messageDao.findAll();
        List<OutgoingMessage> outgoingMessages = new ArrayList<>();
        for (Message message : messages) {
            outgoingMessages.add(new OutgoingMessage(message));
        }
        return JsonHelper.toJson(outgoingMessages);
    }

    public String allUsers() {
        List<User> users = userDao.findAll();
        List<OutgoingUser> outgoingUsers = new ArrayList<>();
        for (User user : users) {
            outgoingUsers.add(new OutgoingUser(user));
        }
        return JsonHelper.toJson(outgoingUsers);
    }
}
