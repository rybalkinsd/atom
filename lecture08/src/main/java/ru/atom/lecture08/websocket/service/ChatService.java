package ru.atom.lecture08.websocket.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture08.websocket.dao.MessageDao;
import ru.atom.lecture08.websocket.dao.UserDao;
import ru.atom.lecture08.websocket.message.SocketMessage;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    private final UserDao userDao;
    private final MessageDao messageDao;

    @Autowired
    public ChatService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
        this.messageDao = messageDao;
    }

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @Transactional
    public void login(@NotNull String login, @NotNull String color) {
        User user = new User();
        userDao.save(user.setColor(color).setLogin(login));
        log.info("[" + login + "] logged in");
    }

    @NotNull
    @Transactional
    public String getUserColor(String login) {
        return userDao.getColorByLogin(login);
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return new ArrayList<>(userDao.findAll());
    }

    @NotNull
    @Transactional
    public List<Message> getAllMessages() {
        return new ArrayList<>(messageDao.getAll());
    }

    @NotNull
    @Transactional
    public Message getLastMessage() {
        return messageDao.getLast();
    }

    @Transactional
    public void logout(String login) {
        userDao.delete(login);
        log.info("[" + login + "] logged out");
    }

    @Transactional
    public void putMessage(SocketMessage message)
    {
        putMessage(message.getLogin(),message.getMsg(),message.getDate());
    }

    @Transactional
    public void putMessage(@NotNull String login, @NotNull String msg, @NotNull Date date) {
        messageDao.putMessage(login, msg, date);
        log.info(login + ": " + msg);
    }
}
