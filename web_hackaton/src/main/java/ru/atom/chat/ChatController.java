package ru.atom.chat;

import org.javatuples.Triplet;
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
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.TimerTask;
import java.util.Timer;


@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Map<String, Integer> countOfMessages = new ConcurrentHashMap<>();

    @Autowired
    private Queue<Triplet<String, Date, String>> messages;


    private Random r = new Random();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if(!name.equals(HtmlUtils.htmlEscape(name)) || name.contains(":")) {
            return ResponseEntity.badRequest().body("Bad symbols in your name, sorry :(");
        }
        if (name.length() < 3) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        int h = r.nextInt(360);
        int s = r.nextInt(100);
        int l = 30  + r.nextInt(40);

        usersOnline.put(name, "hsl("+h+","+s+"%,"+l+"%)");
        countOfMessages.put(name,0);
        Triplet<String,Date,String> msg = new Triplet<>("admin", new Date(), "[<b style=\" color:" +usersOnline.get(name) + ";\">" + name + "</b>] logged in");
        messages.add(msg);
        toHistory(msg);

        return ResponseEntity.ok().build();
    }

    private void toHistory(Triplet<String, Date, String> msg) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ChatController.class.getClassLoader()
                .getResource("history.txt").getPath(),true))) {
            bufferedWriter.write(dateFormat.format(msg.getValue1()) + " " + msg.getValue0() + ": " + msg.getValue2() +"\n");
        } catch(IOException e){
            log.error(e.getLocalizedMessage());
        } catch(NullPointerException e) {
            log.error("can`t load 'history.txt'");
        }
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> chat() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return new ResponseEntity<>(messages.stream()
                .map(triplet ->"<font color=\"grey\">" + dateFormat.format(triplet.getValue1()) + "</font>" +
                        " <b style=\" color:" +usersOnline.get(triplet.getValue0()) + ";\">" + triplet.getValue0()+ "</b>: " + triplet.getValue2())
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "users",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity online() {
        return new ResponseEntity<>(usersOnline.entrySet().stream()
                .map(e -> "<li class=\"list-group-item\" style=\"color:"+ e.getValue() +";\">" + e.getKey() + "</li>")
                .collect(Collectors.joining("\n")), HttpStatus.OK);
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
            return ResponseEntity.badRequest().body("User is not online");
        } else {
            Triplet<String,Date,String> msg = new Triplet<>("admin", new Date(), "[<b style=\" color:" +usersOnline.get(name) + ";\">" + name + "</b>] logged out");
            messages.add(msg);
            toHistory(msg);
            countOfMessages.remove(name);
            usersOnline.remove(name);
            return ResponseEntity.ok().build();
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
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        String cleanMsg = HtmlUtils.htmlEscape(msg);
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("User is not online");
        }
        else {
            cleanMsg = cleanMsg.replaceAll("^(http://www\\.|https://www\\.|http://|https://)[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?",
                    "<a href=\"$0\">$0</a>");

            new Timer().schedule(
                    new TimerTask() {
                        public void run() {
                            countOfMessages.forEach((s, e) -> e = 0);
                        }
                    },
                    10000);
            if (countOfMessages.get(name) > 3) {
                messages.add(new Triplet<>("admin", new Date(), " plz dont spam:" + "[" + name + "] " + " you was banned for 10 sec\n"));
                return ResponseEntity.badRequest().body("User is banned\n 10 sec");
            }

            countOfMessages.put(name, countOfMessages.get(name) + 1);

            messages.add(new Triplet<>(name, new Date(), cleanMsg));
            toHistory(new Triplet<>(name, new Date(), cleanMsg));
            return ResponseEntity.ok().build();
        }
    }
}
