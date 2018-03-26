package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.atom.chat.models.Message;
import ru.atom.chat.models.User;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Queue<Message> messages = new ConcurrentLinkedQueue<>();
    private Map<User, String> usersOnline = new ConcurrentHashMap<>();

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
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }

        User user = new User(name);

        if (usersOnline.containsKey(user)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        usersOnline.put(user, "RED");
        Message message = new Message(LocalDateTime.now(), "[" + name + "] logged in" , user);
        messages.add(message);
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(path = "chat", method = RequestMethod.GET)
    public List<Message> chat() {
        return new LinkedList<>(messages);
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(path = "online", method = RequestMethod.GET)
    public List<User> online() {

        //String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
        return new LinkedList<>(usersOnline.keySet());
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
        User user = new User(name);
        if (!usersOnline.containsKey(user)) {
            return ResponseEntity.badRequest().body("User already logged out");
        }
        usersOnline.remove(user);
        messages.add(new Message(LocalDateTime.now(), "[" + name + "] logged out", user));
        return ResponseEntity.ok().build();
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
        messages.add(new Message(LocalDateTime.now(), "[" + name + "]:  "  + msg, new User(name)));
        return ResponseEntity.ok().build();
    }
}
