package ru.atom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.dao.MessageDao;
import ru.atom.dao.UserDao;
import ru.atom.model.Message;
import ru.atom.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final UserDao userDao = new UserDao();
    private final MessageDao messageDao = new MessageDao();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("Too short name, sorry :(");
        }

        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("Too long name, sorry :(");
        }

        if (name.toLowerCase().contains("hitler")) {
            return ResponseEntity.badRequest()
                    .body("hitler is not allowed, sorry :(");
        }

        List<User> alreadyLogined = userDao.getAllWhere("chat.user.login = '" + name + "'");
        if (alreadyLogined.stream().anyMatch(l -> l.getLogin().equals(name))) {
            return ResponseEntity.badRequest()
                    .body("Already logined");
        }
        User newUser = new User().setLogin(name);
        userDao.insert(newUser);
        newUser = userDao.getByName(newUser.getLogin());
        log.info("[" + name + "] logined");
        String newMsg = "[" + name + "] logined";
        Message message = new Message()
                .setUser(newUser)
                .setValue(newMsg);
        messageDao.insert(message);


        return ResponseEntity.ok().build();
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        throw new UnsupportedOperationException();
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        List<User> all = userDao.getAll();
        String responseBody = all.stream()
                .map(User::getLogin)
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok().body(responseBody);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (name == null) {
            return ResponseEntity.badRequest()
                    .body("Name not provided");
        }

        if (msg == null) {
            return ResponseEntity.badRequest()
                    .body("No message provided");
        }

        if (msg.length() > 140) {
            return ResponseEntity.badRequest()
                    .body("Too long message");
        }

        List<User> authors = userDao.getAllWhere("chat.user.login = '" + name + "'");
        if (authors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Not logined");
        }

        User author = authors.get(0);

        Message message = new Message()
                .setUser(author)
                .setValue(msg);

        messageDao.insert(message);
        log.info("[" + name + "]: " + msg);

        return ResponseEntity.ok().build();
    }


    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        List<Message> resultHistory = new ArrayList<>();
        List<Message> chatHistory = messageDao.getAll();
        for (Message m : chatHistory) {
            if (userDao.getByName(m.getUser().getLogin()) != null)
                resultHistory.add(m);
        }
        String responseBody = resultHistory.stream()
                .map(m -> "[" + m.getUser().getLogin() + "]: " + m.getValue())
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(responseBody);
    }
}
