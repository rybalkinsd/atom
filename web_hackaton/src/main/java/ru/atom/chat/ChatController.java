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

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.Date;
import javax.swing.Timer;
import java.util.TimerTask;


@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Queue<String> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Map<String, Integer> msgCount = new ConcurrentHashMap<>();

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
        usersOnline.put(name, name);
        msgCount.put(name, 0);
        String msg = "[" + name + "] logged in";
        messages.add(msg);
        try{
            FileWriter hFile = new FileWriter("hist.txt", true);
            hFile.append(msg);
            hFile.append("\n");
            hFile.close();
        }
        catch (IOException e) {
        }

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
    public ResponseEntity<String> logout(@RequestParam("name") String name) {
        if (usersOnline.containsKey(name)) {
            usersOnline.remove(name);
            msgCount.remove(name);
            String msg = "[" + name + "] logged out";
            messages.add(msg);
            try{
                FileWriter hFile = new FileWriter("hist.txt", true);
                hFile.append(msg);
                hFile.append("\n");
                hFile.close();
            }
            catch (IOException e) {
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("You are not logged in anyway :(");
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
    public ResponseEntity<String> login(@RequestParam("name") String name,
                                        @RequestParam("msg") String msg) {

        new java.util.Timer().schedule(
                new TimerTask() {
                    public void run() {
                        for (Map.Entry<String, Integer> entry : msgCount.entrySet())
                        {
                            entry.setValue(0);
                        }
                    }
                },
                10000 );

        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("You are not logged in:(");
        }

        if (msgCount.get(name) > 10) {
            String msgg = name + " is banned for 10 seconds";
            messages.add(msgg);
            return ResponseEntity.badRequest().body("You are banned:(\n 10 seconds cooldown");
        }
        msgCount.put(name, msgCount.get(name) + 1);
        Date date = new Date();
        SimpleDateFormat tFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a");
        String msgg = "[" + name + "] said: "+msg+"    {posted at " + tFormat.format(date) + "}";
        messages.add(msgg);
        try{
            FileWriter hFile = new FileWriter("hist.txt", true);
            hFile.append(msgg);
            hFile.append("\n");
            hFile.close();
        }
        catch (IOException e) {
        }
        return ResponseEntity.ok().build();
    }

}
