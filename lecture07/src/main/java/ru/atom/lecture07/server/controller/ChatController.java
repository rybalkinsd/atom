package ru.atom.lecture07.server.controller;

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
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;
import ru.atom.lecture07.server.service.ChatService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

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
                    .body("Too short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("Too long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn != null) {
            return ResponseEntity.badRequest()
                    .body("Already logged in");
        }
        chatService.login(name);

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
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No name provided");
        }
        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn == null) {
            return ResponseEntity.badRequest()
                    .body("Name is logout");
        }
        chatService.logout(alreadyLoggedIn.getLogin(), alreadyLoggedIn.getId());
        return ResponseEntity.ok().build();
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        List<User> online = chatService.getOnlineUsers();
        String responseBody = online.stream()
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
                    .body("No such name");
        }
        if (msg == null) {
            return ResponseEntity.badRequest()
                    .body("Enter message");
        }
        if (msg.length() > 120) {
            return ResponseEntity.badRequest()
                    .body("Too long message");
        }
        User loggedIn = chatService.getLoggedIn(name);
        if (loggedIn == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Not logged");
        }
        chatService.say(name, msg);
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
        List<Message> online = chatService.getMessages();
        String responseBody = online.stream()
                .map(Message::getValue)
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok().body(responseBody + "\n");
    }
}
