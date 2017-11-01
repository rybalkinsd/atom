package ru.atom.chat;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger(ChatController.class);

    private Deque<String> messages = new ConcurrentLinkedDeque<>();
    private Set<String> online = new HashSet<>();

    private List<String> history = new ArrayList<>(100);

    //+ different colors for name and timestamp

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    //+ timestamp
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
        Date ts = new Date();
        SimpleDateFormat dateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm");
        messages.addFirst("<text> <font color=#008000>" + dateNow.format(ts) + " " +
                        "</font> <font color=#FF8C00>" + "[" + name + "]" +
                        "</font> </text>" + "is online");
        log.info(name + " logged in");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    //++ + timestamp
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>("No name provided", HttpStatus.BAD_REQUEST);
        }
        if (!online.remove(name))
            return new ResponseEntity<>(name + "are not logged in", HttpStatus.BAD_REQUEST);
        Date ts = new Date();
        SimpleDateFormat dateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm");
        messages.addFirst("<text> <font color=#008000>" + dateNow.format(ts) + " " +
                        "</font> <font color=#FF8C00>" + "[" + name + "]" +
                        "</font> </text>" + "is offline");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    //++
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> online() {
        return new ResponseEntity<>(online.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    //++ + timestamp + save history to file
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!online.contains(name)) {
            return new ResponseEntity<>("You are not logged in", HttpStatus.BAD_REQUEST);
        }
        if (msg == null || msg.isEmpty())
            return new ResponseEntity<>("No message", HttpStatus.BAD_REQUEST);
        Date ts = new Date();
        SimpleDateFormat dateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm");
        messages.addFirst("<text> <font color=#008000>" + dateNow.format(ts) + " " +
                "</font> <font color=#FF8C00>" + "[" + name + "]" +
                "</font> </text>" + ": " + msg);
        addToHistory(messages.getFirst());
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

    public void addToHistory(String msg) {
        if (history.size() > 100) {
            history.remove(0);
        }
        history.add(msg);
        String path = "/home/alex/atom/web_hackaton/src/main/resources/History/history.txt";
        try (FileWriter writer = new FileWriter(path,false)) {
            String text = history.get(history.size() - 1);
            writer.write(text);
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
