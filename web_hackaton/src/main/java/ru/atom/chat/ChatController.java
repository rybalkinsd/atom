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

import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static final Logger log = LogManager.getLogger(ChatController.class);
    private Deque<String> messages = new ConcurrentLinkedDeque<>();
    private Set<String> online = new HashSet<>();
    private static boolean gethistory = false;

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
        messages.addFirst("<p><font color=\"red\">" + name + "</font></p> is online");
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
            return new ResponseEntity<>("This user does not exist", HttpStatus.BAD_REQUEST);
        }
        online.remove(name);
        messages.addFirst("[" + name + "] logout");
        log.info(name + " logout");
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

        return new ResponseEntity<>(online.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) throws IOException {
        if (name == null || name.isEmpty() || msg == null || msg.isEmpty()) {
            return new ResponseEntity<>("No name or message provided", HttpStatus.BAD_REQUEST);
        }
        if (!online.contains(name)) {
            return new ResponseEntity<>("There is no this user", HttpStatus.BAD_REQUEST);
        }
        Date date = new Date();
        String finalmsg = "<p><font color=\"blue\">[" + sdf.format(date) +
                "]</font></p> <p><font color=\"green\">"   + name +  "</font></p> says: " + msg;
        messages.add(finalmsg);
        log.info("name" + name +  "say" + msg);
        try {
            FileWriter writer = new FileWriter("web_hackaton/src/main/resources/static/history.txt", true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(finalmsg + "\n");
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() throws FileNotFoundException {
        if (!gethistory) {
            StringBuilder sb = new StringBuilder();
            File file = new File("web_hackaton/src/main/resources/static/history.txt");
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String str;
            try {
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            messages.add(sb.toString());
            gethistory = true;
        }
        return new ResponseEntity<>(messages.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }
}
