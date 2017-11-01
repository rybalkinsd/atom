package ru.atom.chat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Time;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger(ChatController.class);

    private Deque<String> messages = new ConcurrentLinkedDeque<>();
    private Set<String> online = new HashSet<>();
    private Time lastMsgTime = new Time(0);


    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>("No name provided", HttpStatus.BAD_REQUEST);
        }
        if (!online.add(name)) {
            return new ResponseEntity<>("Already logged in", HttpStatus.BAD_REQUEST);
        }
        messages.addFirst("[" + name + "] is online");
        log.info(name + " logged in");
        return new ResponseEntity<>(HttpStatus.OK);
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
            return new ResponseEntity<>("No name provided", HttpStatus.BAD_REQUEST);
        }
        if (!online.remove(name)) {
            return new ResponseEntity<>("There is no such person", HttpStatus.BAD_REQUEST);
        }
        messages.addFirst("[" + name + "] is logout");
        messages.remove(name);
        log.info(name + "is logout");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        return new ResponseEntity<>(online.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }

    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(
            @RequestParam("name") String name, @RequestParam("msg") String msg
    ) throws InterruptedException {
        if (online.add(name)) {
            return new ResponseEntity<>("Not logged in", HttpStatus.UNAUTHORIZED);
        }
        if (msg.isEmpty()) {
            return new ResponseEntity<>("Message field is empty", HttpStatus.BAD_REQUEST);
        }

        Time time = new Time(new Date().getHours(), new Date().getMinutes(), new Date().getSeconds());
        if (time.getTime() - lastMsgTime.getTime() <= 5000) {
            return new ResponseEntity<>(
                    "Don't try to send messages more often than 1 messages per 5 secs",
                    HttpStatus.FORBIDDEN);
        }
        lastMsgTime = time;
        if (msg.contains("http")) {
            messages.addFirst("<a style=\"color:SlateBlue\">" + time +
                    "</a> " + "<a style=\"color:Tomato\">" + "[" + name + "]" +
                    "</a>" + ":<a href=" + msg + ">" + msg + "</a>");
        } else {
            messages.addFirst("<a style=\"color:SlateBlue\">" + time +
                    "</a> " + "<a style=\"color:Tomato\">" + "[" + name + "]" +
                    "</a>" + ":" + msg);
        }
        log.info("message by" + name + "message text" + msg);
        return new ResponseEntity<>(HttpStatus.OK);

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


}
