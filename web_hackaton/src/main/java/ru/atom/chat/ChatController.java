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
import org.springframework.web.util.HtmlUtils;
import ru.atom.chat.message.HrefHandler;
import ru.atom.chat.message.IMessage;
import ru.atom.chat.message.Message;
import ru.atom.chat.user.User;
import ru.atom.chat.message.MessageCreator;


import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Queue<IMessage> messages = new ConcurrentLinkedQueue<>();
    private Map<String, User> usersOnline = new ConcurrentHashMap<>();
    private String historyFilename = "ServerMsgHistory.txt";

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name, @RequestParam("password") String pass) {
        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);


        if (usersOnline.containsKey(name) && usersOnline.get(name).getIsActive()) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        if (usersOnline.containsKey(name)) {
            try {
                if (!usersOnline.get(name).login(pass)) {
                    return ResponseEntity.badRequest().body("Wrong password " + "[" + pass + "]");
                }
            } catch (RuntimeException re) {
                return ResponseEntity.badRequest().body("Wrong password RE");
            }
        } else {
            return ResponseEntity.badRequest().body("No such user registered:(");
        }
        messages.add(new Message(name,"logged in"));
        try {
            HistoryWriter hw = new HistoryWriter(historyFilename);
            hw.saveHistory(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(
            @RequestParam("name") String name,
            @RequestParam("password") String pass,
            @RequestParam("passCopy") String passCopy
    ) {

        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 30) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (!pass.equals(passCopy))
            return ResponseEntity.badRequest().body("passwords does not equal:(");

        name = HtmlUtils.htmlEscape(name);
        pass = HtmlUtils.htmlEscape(pass);
        passCopy = HtmlUtils.htmlEscape(passCopy);

        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already registered :(");
        }
        User newUser = new User(name, pass);
        usersOnline.put(name, newUser);
        messages.add(new Message(name,"Registered"));
        try {
            HistoryWriter hw = new HistoryWriter(historyFilename);
            hw.saveHistory(messages);
        } catch (IOException e) {
            e.printStackTrace();
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
    public ResponseEntity<String> chat(@RequestParam("name") String name) {
        String historyFilename = "ServerMsgHistory.txt";
        try {
            HistoryReader hr = new HistoryReader(historyFilename);
            this.messages = hr.readHistory();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        String result = MessageCreator.messageInHtml(messages, name);
        return new ResponseEntity<>(result,
                HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/users
     */
    @RequestMapping(
            path = "users",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> users() {
        String users = "";
        for (User user:usersOnline.values()) {
            if (user.getIsActive()) {
                users += user.getUserName() + "\n";
            }
        }
        return new ResponseEntity<>(users,
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
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("No such user:(");
        }
        if (!usersOnline.get(name).getIsActive()) {
            return ResponseEntity.badRequest().body("Already logged out");
        }
        messages.add(new Message(name, "logout"));
        usersOnline.get(name).logout();
        try {
            HistoryWriter hw = new HistoryWriter(historyFilename);
            hw.saveHistory(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public ResponseEntity<String> say(
            @RequestParam("name") String name,
            @RequestParam("password") String pass,
            @RequestParam("msg") String msg
    ) {

        if (!usersOnline.containsKey(name))
            return ResponseEntity.badRequest().body("No such user:(");

        if (!usersOnline.get(name).passCheck(pass))
            return ResponseEntity.badRequest().body("Wrong password");

        if (!usersOnline.get(name).getIsActive())
            return ResponseEntity.badRequest().body("User is logged out:(");

        if (!usersOnline.get(name).spamCheck())
            return ResponseEntity.badRequest().body("Spam");

        String checkedMsg = HtmlUtils.htmlEscape(msg);
        Message newMsg = new Message(name, HrefHandler.handleHref(checkedMsg));

        usersOnline.get(name).setLastDate(newMsg.getDate());

        messages.add(newMsg);
        try {
            HistoryWriter hw = new HistoryWriter(historyFilename);
            hw.saveHistory(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();

    }
}
