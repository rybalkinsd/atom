package ru.atom.lecture08.websocket.controllers;

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
import org.springframework.web.util.HtmlUtils;
import ru.atom.lecture08.websocket.queues.MessagesQueue;
import ru.atom.lecture08.websocket.queues.UsersOnline;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private Map<String, String> password = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name, @RequestParam("pass") String pass) {
        if (MessagesQueue.getMessages().isEmpty()) {
            try{
                FileReader hFile = new FileReader("hist.txt");
                Scanner scan = new Scanner(hFile);
                while (scan.hasNextLine()) {
                    MessagesQueue.getMessages().add(scan.nextLine());
                }
                hFile.close();
            }
            catch (IOException e) {
            }
        }

        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (UsersOnline.getUsersOnline().contains(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        if (password.containsKey(name) && !pass.equals(password.get(name))) {
            return ResponseEntity.badRequest().body("Incorrect password. Try again");
        }
        if (pass.length() < 1) {
            return ResponseEntity.badRequest().body("Too short pass. At least 1 character required :<");
        }
        if (pass.length() > 20) {
            return ResponseEntity.badRequest().body("Too long password, sorry :<");
        }
        UsersOnline.getUsersOnline().offer(name);
        if (!password.containsKey(name)) {
            password.put(name, pass);
        }
        String msg = "[" + name + "] logged in";
        MessagesQueue.getMessages().add(msg);
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
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logout(@RequestParam("name") String name) {
        name = HtmlUtils.htmlEscape(name);
        if (UsersOnline.getUsersOnline().contains(name)) {
            UsersOnline.getUsersOnline().remove(name);
            String msg = "[" + name + "] logged out";
            MessagesQueue.getMessages().add(msg);
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
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        if (MessagesQueue.getMessages().isEmpty()) {
            try{
                FileReader hFile = new FileReader("hist.txt");
                Scanner scan = new Scanner(hFile);
                while (scan.hasNextLine()) {
                    MessagesQueue.getMessages().add(scan.nextLine());
                }
                hFile.close();
            }
            catch (IOException e) {
            }
        }

        return new ResponseEntity<>(MessagesQueue.getMessages().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }
}
