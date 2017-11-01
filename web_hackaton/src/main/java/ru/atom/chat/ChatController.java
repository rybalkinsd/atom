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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private boolean historyLoaded = false;

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
        messages.addFirst(getPrefix("System", "#FF0000") + "[" + name + "] is online");
        log.info(name + " logged in");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getPrefix(String name, String color) {
        String reportDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        StringBuilder sb = new StringBuilder();
        return sb.append("<b>")
                .append("[" + reportDate + "]</b>")
                .append("<font color=\"" + color + "\"><b>")
                .append(name)
                .append(": </b></font>").toString();
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout?name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        if (!online.contains(name)) {
            log.info("bad login");
            return new ResponseEntity<>("Bad login", HttpStatus.BAD_REQUEST);
        } else {
            messages.addFirst(getPrefix("System", "#FF0000") + "[" + name + "] is logout");
            online.remove(name);
            log.info(name + " logged out");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        if (online.isEmpty()) {
            messages.add(getPrefix("System", "#FF0000") + "nobody here");
        } else {
            messages.add(getPrefix("System", "#00FF00")
                    + online.stream().collect(Collectors.joining(", ")));
        }
        return new ResponseEntity<>(online.stream()
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
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!online.contains(name)) {
            log.info("bad login");
            return new ResponseEntity<>("Bad login", HttpStatus.BAD_REQUEST);
        } else {
            messages.add(getPrefix(name, "#0000FF") + msg);
            log.info("good login");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    /**
     * curl -i localhost:8080/hello/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        if (!historyLoaded) {
            try {
                if (Files.exists(Paths.get(getClass().getClassLoader().getResource("history").toURI()))) {
                    Files.readAllLines(Paths.get(getClass().getClassLoader().getResource("history").toURI()))
                            .stream().forEach(messages::add);
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            historyLoaded = true;
        } else {
            save();
        }
        return new ResponseEntity<>(messages.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }

    private  void save() {
        try {
            Files.write(Paths.get(getClass().getClassLoader().getResource("history").toURI()),
                    messages);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
