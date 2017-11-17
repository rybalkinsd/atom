package ru.atom.lecture07.server.service;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture07.server.controller.ChatController;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @Transactional
    public void login(@NotNull String login) {
        User user = new User();
        userDao.save(user.setLogin(login));
        log.info("[" + login + "] logged in");
    }

    @Transactional
    public void logout(@NotNull String name) {
        userDao.removeByLogin(name);
        log.info("[" + name + "] logged out");
    }

    @Transactional
    public void say(@NotNull String name, @NotNull String msg) {
        User user = userDao.getByLogin(name);
        Message message = new Message();
        messageDao.save(message.setUser(user).setValue(msg).setTime(new Timestamp(System.currentTimeMillis())));
        log.info("[" + name + "] said: " + msg);
    }

    @Transactional
    public String loadHistory() {
        List<Message> messages = Lists.newArrayList(messageDao.findAll());
        messages.sort((a,b) ->  a.getTime().compareTo(b.getTime()));
        return messages.stream().map(Message::toString)
                .collect(Collectors.joining("<br>"));
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return Lists.newArrayList(userDao.findAll());
    }
}
