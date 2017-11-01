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
import java.text.SimpleDateFormat;
import java.util.Deque;
import  java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Queue;
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
    private Set<String> onlinename = new HashSet<>();
    private String trash;
    //private String onlinename;

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
     * curl -X POST -i localhost:8080/chat/logout?name=I_AM_STUPID"
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
            return new ResponseEntity<>("User is not authorized", HttpStatus.BAD_REQUEST);
        }


        messages.add("[" + name + "] is logout");
        log.info(name + " logged out");
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

        for (String text : online)
        {
          onlinename.add(text);
        }
        return new ResponseEntity<>(onlinename.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    Date curDate = new Date();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
       // System.out.println("date: " + dateFormat.format( new Date() ) );
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!online.contains(name)) {
            return new ResponseEntity<>("User is not authorized", HttpStatus.BAD_REQUEST);
        }

        if (msg.contains("www") || msg.contains("http"))
            trash = dateFormat.format( new Date() ).toString()+"<text> <font color=#FF0000>" + "[" + name + "] </font> <font color=#0000FF> say: <a href=\""+msg+"\"> " + msg + "</a>;" + "</font></text>";
        else trash = dateFormat.format( new Date() ).toString()+"<script language=\"javascript\" type=\"text/javascript\"> document.write(getDate()); </script>"+ "<text> <font color=#FF0000>" + "[" + name + "] </font> <font color=#0000FF> say: " + msg + ";" + "</font></text>";
        messages.add(trash);
        log.info(name + " say " + msg);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * curl -i localhost:8080/hello/chat
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
