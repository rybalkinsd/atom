package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.atom.chat.models.Message;
import ru.atom.chat.models.User;
import ru.atom.chat.repositories.MessageRepository;
import ru.atom.chat.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
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

        List<User> users = userRepository.findByName(name);
        if (users.size() != 0) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        User user = new User(name);
        user.setOnline(true);
        userRepository.save(user);
        Message message = new Message(LocalDateTime.now(), "[" + name + "] logged in", user);
        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(path = "chat", method = RequestMethod.GET)
    public List<Message> chat() {
        return messageRepository.findAll();
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(path = "online", method = RequestMethod.GET)
    public List<User> online() {

        //String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
        return new LinkedList<>(userRepository.findAllByisOnline(true));
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
        User user = userRepository.findByName(name).get(0);
        if (!user.isOnline()) {
            return ResponseEntity.badRequest().body("User already logged out");
        }
        userRepository.delete(user);
        messageRepository.save(new Message(LocalDateTime.now(), "[" + name + "] logged out", user));
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
        User user = userRepository.findByName(name).get(0);
        messageRepository.save(new Message(LocalDateTime.now(), "[" + name + "]:  " + msg, user));
        return ResponseEntity.ok().build();
    }
}
