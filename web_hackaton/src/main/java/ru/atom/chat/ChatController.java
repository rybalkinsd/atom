package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.HtmlUtils;
import ru.atom.chat.dao.MessageDao;
import ru.atom.chat.dao.UserDao;
import ru.atom.chat.message.HrefHandler;
import ru.atom.chat.message.Message;
import ru.atom.chat.socket.message.response.OutcomingMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingUser;
import ru.atom.chat.socket.services.ChatService;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.util.SessionsList;
import ru.atom.chat.user.User;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID&password=pwd"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name, @RequestParam("password") String pass) {
        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);

        User user = userDao.getByLogin(name);
        if (user == null) {
            return ResponseEntity.badRequest().body("No such user registered:(");
        }
        if (user.getActive()) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        try {
            if (!user.login(pass)) {
                return ResponseEntity.badRequest().body("Wrong password " + "[" + pass + "]");
            }
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().body("Wrong password RE");
        }

        Message logined = new Message(user, "logged in");
        messageDao.save(logined);
        userDao.update(user);

        OutcomingMessage newMessage = new OutcomingMessage(
                OutgoingTopic.NEW_MESSAGE,
                JsonHelper.toJson(new OutgoingMessage(logined))
        );
        SessionsList.sendAll(JsonHelper.toJson(newMessage));
        return ResponseEntity.ok().build();
    }

    /**
     * curl -X POST -i localhost:8080/chat/register -d "name=I_AM_STUPID&password=pwd&passCopy=pwd"
     */
    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(
            @RequestParam("name") String name,
            @RequestParam("password") String pass,
            @RequestParam("passCopy") String passCopy
    ) {

        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 30) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (!pass.equals(passCopy))
            return ResponseEntity.badRequest().body("passwords does not equal:(");

        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);

        User user = userDao.getByLogin(name);
        if (user != null) {
            return ResponseEntity.badRequest().body("Already registered :(");
        }
        User newUser = new User(name, pass);
        userDao.save(newUser);
        Message message = new Message(newUser, "Registered");
        messageDao.save(message);

        OutcomingMessage newUserRegistered = new OutcomingMessage(
                OutgoingTopic.NEW_USER,
                JsonHelper.toJson(new OutgoingUser(newUser))
        );
        SessionsList.sendAll(JsonHelper.toJson(newUserRegistered));
        OutcomingMessage newMessage = new OutcomingMessage(
                OutgoingTopic.NEW_MESSAGE,
                JsonHelper.toJson(new OutgoingMessage(message))
        );
        SessionsList.sendAll(JsonHelper.toJson(newMessage));
        return ResponseEntity.ok().build();
    }

    /**
     * curl -X GET -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        String messageList = chatService.allMessages();
        log.info(messageList);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/users
     */
    @RequestMapping(
            path = "users",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> users() {
        String userList = chatService.allUsers();
        log.info(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        List<User> users = userDao.getByActive(true);
        String responseBody = users.stream()
                .map(User::getLogin)
                .collect(Collectors.joining("\n")) + "\n";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logout(
            @RequestParam("name") String name,
            @RequestParam("password") String password) {

        name = HtmlUtils.htmlEscape(name);
        password = HtmlUtils.htmlEscape(password);

        User user = userDao.getByLogin(name);
        if (user == null) {
            return ResponseEntity.badRequest().body("No such user:(");
        }
        if (!user.getActive()) {
            return ResponseEntity.badRequest().body("Already logged out");
        }
        if (!user.passCheck(password)) {
            return ResponseEntity.badRequest().body("Wrong password");
        }
        Message message = new Message(user, "logout");
        messageDao.save(message);
        user.logout();
        userDao.update(user);

        OutcomingMessage newMessage = new OutcomingMessage(
                OutgoingTopic.NEW_MESSAGE,
                JsonHelper.toJson(new OutgoingMessage(message))
        );
        SessionsList.sendAll(JsonHelper.toJson(newMessage));
        return ResponseEntity.ok().build();
    }
}
