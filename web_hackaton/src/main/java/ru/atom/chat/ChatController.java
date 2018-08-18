package ru.atom.chat;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import ru.atom.chat.service.MessageService;
import ru.atom.chat.util.HtmlUtils;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Map<String, List<LocalTime>> messageCounter = new ConcurrentHashMap<>();
    private List<String> bannedUsers = Collections.synchronizedList(new LinkedList<>());

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

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

        if (HtmlUtils.isHtml(name)) {
            return ResponseEntity.badRequest().body("Not use HTML!!!!");
        }

        usersOnline.put(name, name);
        messageService.addMessage("[" + name + "] logged in");
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
        return new ResponseEntity<>(messageService.getAllMessages(),
                HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> online() {
        return new ResponseEntity<>(usersOnline.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
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
        String logoutName = usersOnline.remove(name);
        if (logoutName != null && !logoutName.isEmpty()) {
            messageService.addMessage("[" + logoutName + "] logged out");
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
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!usersOnline.containsKey(name)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (bannedUsers.contains(name)) {
            List<LocalTime> times = messageCounter.get(name);
            LocalTime lastTime = times.get(times.size() - 1);
            if (ChronoUnit.SECONDS.between(lastTime, LocalTime.now()) <= 10) {
                return ResponseEntity.badRequest().body("You're banned for 10 seconds :(");
            } else {
                bannedUsers.remove(name);
                messageCounter.remove(name);
            }
        }

        if (HtmlUtils.isHtml(msg)) {
            return ResponseEntity.badRequest().body("Not use html !!!");
        }

        Pattern pattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            String link = matcher.group();
            msg = msg.replace(link, "");
            msg = "<a href=" + link + ">" + msg + "</a>";
        }

        messageService.addMessage("[" + name + "] " + msg);
        if (messageCounter.containsKey(name)) {
            List<LocalTime> times = messageCounter.get(name);
            times.add(LocalTime.now());
            int count = 0;
            int size = times.size() - 1;
            for (int i = 0; i < size; i++) {
                if (ChronoUnit.SECONDS.between(times.get(i), times.get(i + 1)) < 1) {
                    count++;
                }
                if (count > 3) {
                    messageService.addMessage("[SERVER] User " + name + " were banned for 10 seconds");
                    bannedUsers.add(name);
                    return ResponseEntity.badRequest().body("You're banned for 10 seconds :(");
                }
            }
        } else {
            messageCounter.put(name, new LinkedList<>());
            messageCounter.get(name).add(LocalTime.now());
        }
        return ResponseEntity.ok().build();
    }
}
