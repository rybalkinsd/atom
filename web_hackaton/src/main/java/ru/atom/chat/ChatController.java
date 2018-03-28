package ru.atom.chat;

import okhttp3.internal.Internal;
import org.javatuples.Triplet;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.TimerTask;
import java.util.Timer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private Queue<Triplet<String, Date, String>> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();
    private Map<String, Integer> coutOfMassage = new ConcurrentHashMap<>();
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
        if (name.length() < 1) {
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
        int l = 80  + r.nextInt(20);

        usersOnline.put(name, "hsl("+h+","+s+"%,"+l+"%)");
        coutOfMassage.put(name,0);
        messages.add(new Triplet<>("admin", new Date(), "[" + name + "] logged in"));

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ChatController.class.getClassLoader()
                .getResource("History.txt").getPath(),true))) {
            bw.write(what + "\n");
        } catch(IOException ex){
            log.warn("Unable to write to history");
        } catch(NullPointerException e) {
            log.warn("Unable to get resource 'History.txt'");
        }

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
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return new ResponseEntity<>(messages.stream()
                .map(triplet ->"<font color=\"grey\">" + dateFormat.format(triplet.getValue1()) + "</font>" +
                        " <font color=\" " +usersOnline.get(triplet.getValue0()) + "\">" + triplet.getValue0()+ "</font>: " + triplet.getValue2())
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
                .map(e -> " <font color=\" " +e.getValue() + "\">" + e.getKey() + "</font> ")
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
            coutOfMassage.remove(name);
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
        name = HtmlUtils.htmlEscape(name);
        msg = HtmlUtils.htmlEscape(msg);
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("User is not online");
        }
        else {

            new Timer().schedule(
                    new TimerTask() {
                        public void run() {
                            for (Map.Entry<String, Integer> entry : coutOfMassage.entrySet()) {
                                entry.setValue(0);
                            }
                        }
                    },
                    10000);
            if (coutOfMassage.get(name) > 3) {
                messages.add(new Triplet<>("admin", new Date(), " plz dont spam:" + "[" + name + "] " + " you was banned for 10 sec\n"));
                return ResponseEntity.badRequest().body("User is banned\n 10 sec");
            }

            coutOfMassage.put(name, coutOfMassage.get(name) + 1);

            messages.add(new Triplet<>(name, new Date(), msg));
        }
        return ResponseEntity.ok().build();
    }
}
