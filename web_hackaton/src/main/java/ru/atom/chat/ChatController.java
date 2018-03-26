package ru.atom.chat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.regex.*;
import java.util.*;


@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private HistorySaver historySaver = new HistorySaver();

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
            return ResponseEntity.badRequest().body("The name is too short, sorry\n");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("The name is too long, sorry\n");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in");
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        usersOnline.put(name, name);
        name = HtmlUtils.htmlEscape(name);
        historySaver.saveHistory("<span style=\"color:#ff00ff\">" + sdf.format(cal.getTime()) +
                "</span> [<span style=\"color:#ffff00\">" + name + "</span>] logged in");
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
        return new ResponseEntity<>(historySaver.getHistory(), HttpStatus.OK);
    }


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        String responseBody = String.join("\n", usersOnline.keySet().stream().map(HtmlUtils::htmlEscape).
                sorted().collect(Collectors.toList()));
        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path="logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logout(@RequestParam("name") String name) {
        if (usersOnline.containsKey(name)) {
            usersOnline.remove(name);
            name = HtmlUtils.htmlEscape(name);
            historySaver.saveHistory("[" + name + "] logged out");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("This user is not logged in\n");
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path="say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("This user is not logged in\n");
        }
        if (msg.length() < 1) {
            return ResponseEntity.badRequest().body("The message is too short\n");
        }
        if (msg.length() > 50) {
            return ResponseEntity.badRequest().body("The message is too long\n");
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        name = HtmlUtils.htmlEscape(name);
        historySaver.saveHistory("<span style=\"color:#ff00ff\">" + sdf.format(cal.getTime())
                + "</span> [<span style=\"color:#ffff00\">" + name + "</span>] " + handleMessage(msg));
        return ResponseEntity.ok().build();
    }

    private String handleMessage(String msg) {
        msg = HtmlUtils.htmlEscape(msg);
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Matcher m = Pattern.compile(regex).matcher(msg);
        List<String> links = new ArrayList<>();
        while (m.find()) {
            links.add(m.group(0));
        }
        for (String s : links) {
            System.out.println(s);
            System.out.println("\\b" + s +  "\\b");
            msg = msg.replaceFirst("\\b" + s +  "\\b", "<a href=\"" + s + "\">" + s + "</a>");
        }
        return msg;
    }
}
