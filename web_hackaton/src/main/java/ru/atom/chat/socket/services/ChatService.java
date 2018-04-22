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
import ru.atom.chat.socket.message.request.messagedata.LoginUser;
import ru.atom.chat.socket.message.request.messagedata.LogoutUser;
import ru.atom.chat.socket.message.request.messagedata.RegisterUser;
import ru.atom.chat.socket.message.response.ResponseData;
import ru.atom.chat.socket.message.response.OperationResponse;
import ru.atom.chat.socket.message.response.messagedata.OutgoingChatMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingUser;
import ru.atom.chat.socket.topics.IncomingTopic;
import ru.atom.chat.socket.topics.MailingType;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.topics.ResponseTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.util.SessionsList;
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
        ResponseData response = new ResponseData(IncomingTopic.MESSAGE);

        User user = userDao.getByLogin(message.getSender());
        if (user == null) {
            response.addSenderError("No such user:(");
            return response;
        }

        if (!user.passCheck(message.getPassword())) {
            response.addSenderError("Wrong password");
            return response;
        }

        if (!isLoggedIn(user)) {
            response.addSenderError("User is logged out");
            return response;
        }

        if (!user.spamCheck()) {
            response.addSenderError("Spam");
            return response;
        }

        String checkedMsg = HtmlUtils.htmlEscape(message.getMsg());
        Message newMsg = new Message(user, HrefHandler.handleHref(checkedMsg));
        messageDao.save(newMsg);

        response.addSenderOkStatus();
        response.addCommonMessage(newMsg);
        return response;
    }

    public ResponseData login(LoginUser loginUser) {
        ResponseData response = new ResponseData(IncomingTopic.LOGIN);

        String name = HtmlUtils.htmlEscape(loginUser.getSender());
        String pass = HtmlUtils.htmlEscape(loginUser.getPassword());

        User user = userDao.getByLogin(name);
        if (user == null) {
            response.addSenderError("No such user registered:(");
            return response;
        }
        if (isLoggedIn(user)) {
            response.addSenderError("Already logged in:(");
            return response;
        }
        try {
            if (!user.login(pass)) {
                response.addSenderError("Wrong password " + "[" + pass + "]");
                return response;
            }
        } catch (RuntimeException re) {
            response.addSenderError("Wrong password RE");
            return response;
        }

        Message logined = new Message(user, "logged in");
        messageDao.save(logined);

        response.addSenderOkStatus();
        response.addCommonMessage(logined);
        return response;
    }

    public ResponseData register(RegisterUser registerUser) {
        ResponseData response = new ResponseData(IncomingTopic.REGISTER);

        String name = registerUser.getSender();
        String pass = registerUser.getPassword();
        String passCopy = registerUser.getPassCopy();

        if (name.length() < 1) {
            response.addSenderError("Too short name, sorry :(");
            return response;
        }
        if (name.length() > 30) {
            response.addSenderError("Too long name, sorry :(");
            return response;
        }
        if (!pass.equals(passCopy)) {
            response.addSenderError("passwords does not equal:(");
            return response;
        }

        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);

        User user = userDao.getByLogin(name);
        if (user != null) {
            response.addSenderError("Already registered :(");
            return response;
        }

        User newUser = new User(name, pass);
        userDao.save(newUser);
        Message message = new Message(newUser, "Registered");
        messageDao.save(message);


        response.addSenderOkStatus();
        response.add(response.buildMail(
                MailingType.TO_ALL,
                OutgoingTopic.NEW_USER,
                JsonHelper.toJson(new OutgoingUser(newUser)))
        );
        response.addCommonMessage(message);
        return response;
    }

    public ResponseData logout(LogoutUser logoutUser) {
        ResponseData response = new ResponseData(IncomingTopic.LOGOUT);

        String name = HtmlUtils.htmlEscape(logoutUser.getSender());
        String password = HtmlUtils.htmlEscape(logoutUser.getPassword());

        User user = userDao.getByLogin(name);
        if (user == null) {
            response.addSenderError("No such user:(");
            return response;
        }
        if (!isLoggedIn(user)) {
            response.addSenderError("Already logged out");
            return response;
        }
        if (!user.passCheck(password)) {
            response.addSenderError("Wrong password");
            return response;
        }
        Message message = new Message(user, "logout");
        messageDao.save(message);

        response.addSenderOkStatus();
        response.addCommonMessage(message);
        return response;
    }

    public String allMessages() {
        List<Message> messages = messageDao.findAll();
        List<OutgoingChatMessage> outgoingChatMessages = new ArrayList<>();
        for (Message message : messages) {
            outgoingChatMessages.add(new OutgoingChatMessage(message));
        }
        return JsonHelper.toJson(outgoingChatMessages);
    }

    public String allUsers() {
        List<User> users = userDao.findAll();
        List<OutgoingUser> outgoingUsers = new ArrayList<>();
        for (User user : users) {
            outgoingUsers.add(new OutgoingUser(user));
        }
        return JsonHelper.toJson(outgoingUsers);
    }

    public boolean isLoggedIn(User user) {
        return SessionsList.finByUsername(user.getLogin()) != null;
    }
}
