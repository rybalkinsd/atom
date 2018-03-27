package ru.atom.chat;

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

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private Map<String, User> usersOnline = new ConcurrentHashMap<>();

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
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        usersOnline.put(name, new User(name));
        ChatMessage msg = new ChatMessage("logged in",usersOnline.get(name));
        messages.add(msg);
        msg.saveInFile();
        //messages.add("[" + User. + "] logged in");
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
        return new ResponseEntity<>(messages.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> online() {
        if (usersOnline.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("No users online");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    String.join("\n", usersOnline.keySet()
                            .stream().sorted().collect(Collectors.toList())));
        }
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
        if (usersOnline.containsKey(name)) {
            ChatMessage msg = new ChatMessage("logged out",usersOnline.get(name));
            messages.add(msg);
            msg.saveInFile();
            usersOnline.remove(name);
            //messages.add("[" + name + "] logged out");
            return ResponseEntity.ok().build();
        } else {
            //TODO
            //needs msg to chat?
            return ResponseEntity.badRequest().body("Not logged in");
        }
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (usersOnline.containsKey(name)) {
            ChatMessage message = new ChatMessage(msg,usersOnline.get(name));
            messages.add(message);
            message.saveInFile();
            // messages.add(name + ": " + msg + "    ( " +
            //       LocalDateTime.now().format(DateTimeFormatter
            // .ofPattern("dd-LLLL-yyyy HH:mm:ss")).toString() + " )");
            return ResponseEntity.ok(msg);
        } else {
            //TODO
            //needs msg in chat?
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }
}