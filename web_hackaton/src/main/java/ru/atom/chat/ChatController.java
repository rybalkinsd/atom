package ru.atom.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger(ChatController.class);

    private Queue<String> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private WriteChatHistoryToFile writeHistoryToFile;
    private String previousUser = null;
    private SpamChecker spamChecker;

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
        messages.add("<span class='user'>[" + escapeHtml(name) + "]</span> logged in");
        writeHistoryToFile = new WriteChatHistoryToFile(getHistory());
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
        writeHistoryToFile = new WriteChatHistoryToFile(getHistory());
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
        String responseBody = String.join("\n", usersOnline.keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList()));
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
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("This user is not logged in :(");
        }
        usersOnline.remove(name);
        messages.add("<span class='user'>[" + escapeHtml(name) + "]</span> logged out");
        writeHistoryToFile = new WriteChatHistoryToFile(getHistory());
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
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!name.equals(previousUser)) {
            spamChecker = new SpamChecker(name, new Date());
            previousUser = name;
        }
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("This user is not logged in :( He can't chatting!");
        } else if (msg.trim().length() < 1) {
            return ResponseEntity.badRequest().body("This message is too short :(");
        } else if (msg.trim().length() > 250) {
            return ResponseEntity.badRequest().body("This message is too long :(");
        }
        if (!spamChecker.isSpamming(msg)) {
            messages.add("<span class='time'>" +
                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) +
                "</span> | \t" +
                "<span class='user'>[" + name + "]</span> says: " + highlightUrl(escapeHtml(msg)));
        } else {
            return ResponseEntity.badRequest().body("\"" + name + "\" IS " + "SPAMMING :(");
        }
        writeHistoryToFile = new WriteChatHistoryToFile(getHistory());
        return ResponseEntity.ok().build();
    }

    private String getHistory() {
        return this.messages.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    private String highlightUrl(String text) {
        String output = "";
        // separate input by spaces ( URLs don't have spaces )
        String [] parts = text.split("\\s+");

        // Attempt to convert each item into an URL.
        for (String item : parts) {
            try {
                URL url = new URL(item);
                // If possible then replace with anchor...
                output += "<a href=\"" + url + "\">" + url + "</a> ";
            } catch (MalformedURLException e) {
                // If there was an URL that was not it!...
                output += item + " ";
            }
        }
        return output;
    }
}