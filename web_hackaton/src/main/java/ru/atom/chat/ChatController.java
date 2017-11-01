package ru.atom.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


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

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger(ChatController.class);

    private Deque<Message> messages = new ConcurrentLinkedDeque<>();
    private LinkedHashMap<String, User> online = new LinkedHashMap<>();
    private LinkedHashMap<String, User> registeredUsers = new LinkedHashMap<>();

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
        if (name.length() >= 30) {
            return new ResponseEntity<>("Name is too long", HttpStatus.BAD_REQUEST);
        }

        if (online.containsKey(name) || registeredUsers.containsKey(name)) {
            return new ResponseEntity<>("Already logged in", HttpStatus.BAD_REQUEST);
        }
        online.put(name, new User(name));

        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dtf.format(date);
        messages.addFirst(new Message(time,"SYSTEM", "[" + name + "] is online"));
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
        if (online.containsKey(name)) {
            online.remove(name);
            return new ResponseEntity<>(name + " logged out", HttpStatus.OK);
        }
        return new ResponseEntity<>("Noone to logout",HttpStatus.BAD_REQUEST);
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        return new ResponseEntity<>(online.keySet().stream()
                .collect(Collectors.joining("\n")), HttpStatus.OK);
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
        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String time = dtf.format(date);
        if (online.containsKey(name)) {
            if (!msg.equals("")) {
                messages.add(new Message(time, name, msg));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Write something", HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>("You are not logged in", HttpStatus.BAD_REQUEST);
    }


    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> chat() {
        return new ResponseEntity<>(
                messages.stream()
                        .map(message -> {
                            return message.getHtml();
                        })
                        .collect(Collectors.joining("\n")), HttpStatus.OK);
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity register() {
        return new ResponseEntity<>("<!DOCTYPE html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "  <section class=\"container\">\n" +
                "    <div class=\"login\">\n" +
                "      <form method=\"post\" action=\"http://localhost:8080/chat/newuser\">\n" +
                "        <p><input type=\"text\" name=\"login\" value=\"\" placeholder=\"login\"></p>\n" +
                "        <p><input type=\"password\" name=\"password\" value=\"\" placeholder=\"password\"></p>\n" +
                "        <p class=\"submit\"><input type=\"submit\" name=\"commit\" value=\"Registration\"></p>\n" +
                "      </form>\n" +
                "    </div>\n" +
                "\n" +
                "  </section>\n" +
                "</body>\n" +
                "</html>", HttpStatus.OK);
    }

    @RequestMapping(
            path = "newuser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity newUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        if (!registeredUsers.containsKey(login)) {
            registeredUsers.put(login, new User(login, password));

            DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String time = dtf.format(date);
            messages.addFirst(new Message(time,"SYSTEM", "[" + login + "] is registered"));
            log.info(login + " is registered");
            return new ResponseEntity<>("Successful registration", HttpStatus.OK);
        }
        return new ResponseEntity<>("This login is unavailable", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(
            path = "auth",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity auth(@RequestParam("login") String login, @RequestParam("password") String password) {
        if (registeredUsers.containsKey(login)) {
            if (password.equals(registeredUsers.get(login).getPassword())) {
                online.put(login, registeredUsers.get(login));
                DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String time = dtf.format(date);
                messages.addFirst(new Message(time,"SYSTEM", "[" + login + "] is logged in"));
                log.info(login + " is logged in");
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Login or password is incorrect", HttpStatus.BAD_REQUEST);

    }

}

