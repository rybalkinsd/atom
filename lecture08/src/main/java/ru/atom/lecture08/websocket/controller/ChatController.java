package ru.atom.lecture08.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.HtmlUtils;
import ru.atom.lecture08.websocket.SessionNotifier;
import ru.atom.lecture08.websocket.model.User;
import ru.atom.lecture08.websocket.service.ChatService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);


    @Autowired
    private SessionNotifier sessionNotifier;

    @Autowired
    private ChatService chatService;

    private Random r = new Random();


    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "users",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity online() {

        List<User> online = chatService.getOnlineUsers();
        String responseBody = online.stream()
                .map(user -> "<li class=\"list-group-item\" style=\"color:" + chatService.getUserColor(user.getLogin()) + ";\">" + user.getLogin() + "</li>")
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/delete -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "delete",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn == null) {
            return ResponseEntity.badRequest().body("User is not online");
        } else {
            chatService.logout(name);
            chatService.putMessage("admin", "[<b style=\" color:"
                    + chatService.getUserColor(name) + ";\">" + name + "</b>] logged out", new Date());

            return ResponseEntity.ok().build();
        }
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    /*
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {

        String cleanMsg = HtmlUtils.htmlEscape(msg);
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("User is not online");
        } else {
            cleanMsg = cleanMsg.replaceAll("^(http://www\\.|https://www\\.|http://|https://)[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?",
                    "<a href=\"$0\">$0</a>");

            new Timer().schedule(
                    new TimerTask() {
                        public void run() {
                            countOfMessages.put(name, 0);
                        }
                    },
                    10000);
            if (countOfMessages.get(name) > 3) {
                socketMessages.add(new SocketMessage(Topic.MESSAGE, "admin", " plz dont spam:"
                        + "[" + name + "] " + " you were banned for 10 sec\n"));
                return ResponseEntity.badRequest().body("User is banned\n 10 sec");
            }

            countOfMessages.put(name, countOfMessages.get(name) + 1);

            socketMessages.add(new SocketMessage(Topic.MESSAGE, name, cleanMsg));
            toHistory(new SocketMessage(Topic.MESSAGE, name, cleanMsg));
            return ResponseEntity.ok().build();
        }
    }
    */
}
