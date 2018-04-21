package ru.atom.chat;

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
import ru.atom.chat.dao.MessageDao;
import ru.atom.chat.dao.UserDao;
import ru.atom.chat.message.Message;
import ru.atom.chat.socket.message.response.ResponseMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingChatMessage;
import ru.atom.chat.socket.message.response.messagedata.OutgoingUser;
import ru.atom.chat.socket.services.ChatService;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.util.JsonHelper;
import ru.atom.chat.socket.util.SessionsList;
import ru.atom.chat.user.User;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    /**
     * curl -X GET -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        String messageList = chatService.allMessages();
        log.info(messageList);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/users
     */
    @RequestMapping(
            path = "users",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> users() {
        String userList = chatService.allUsers();
        log.info(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
