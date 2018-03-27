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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.text.*;
import java.util.Map;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private DatabaseHandler History = new DatabaseHandler();
    private Queue<String> messages = new ConcurrentLinkedQueue<>(History.toQueue());
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name, @RequestParam("password") String password) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        if(password.isEmpty())
            return ResponseEntity.badRequest().body("Enter password");
        usersOnline.put(name, password);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss dd.MM");
        String message = "[<font color=\"green\">" + name + "</font>][<font color=\"blue\">" + formatForDateNow.format(dateNow) + "</font>] logged in";
        messages.add(message);
        History.put(message);
        users_antispam.put(name, new Antispam(false));
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
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
    public ResponseEntity online() {
        String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
        return ResponseEntity.ok(responseBody);
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
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Not logged in:(");
        }
        usersOnline.remove(name);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss dd.MM");
        String message = "[<font color=\"green\">" + name + "</font>][<font color=\"blue\">" + formatForDateNow.format(dateNow) + "</font>] logged out";
        messages.add(message);
        History.put(message);
        return ResponseEntity.ok().build();
    }

    public class Antispam {

        boolean spam;

        Timer timer;

        Antispam(boolean spam) {
            this.spam = spam;
            if (spam) {
                timer = new Timer();
                timer.schedule(new ClearSpam(), 1500, 1500);
            }
        }

        class ClearSpam extends TimerTask {
            public void run() {
                spam = false;
            }
        }

    }

    static HashMap< String, Antispam > users_antispam = new HashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name,
                              @RequestParam("password") String password,
                              @RequestParam("msg") String msg) {

        if (msg.length() < 1) {
            return ResponseEntity.badRequest().body("Too short msg, sorry :(");
        }
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Not logged in:(");
        }

        if (users_antispam.get(name).spam) {
            return ResponseEntity.badRequest().body("Don't spam pls!");
        }
        if (!usersOnline.get(name).equals(password))
            return ResponseEntity.badRequest().body("Wrong Password");
        users_antispam.put(name, new Antispam(true));
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss dd.MM");
        String message = "[<font color=\"green\">" + name + "</font>][<font color=\"blue\">" + formatForDateNow.format(dateNow) + "</font>] " + msg;
        messages.add(message);
        History.put(message);
        return ResponseEntity.ok().build();
    }
}
