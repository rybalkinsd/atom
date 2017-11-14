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
import java.util.Comparator;
import java.util.Date;
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
    public void logout(@NotNull Integer id, String login) {
        userDao.delete(id);
        log.info("[" + login + "] logged out");
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return Lists.newArrayList(userDao.findAll());
    }

    @NotNull
    @Transactional
    public List<Message> getMessages() {
        return Lists.newArrayList(messageDao.findAll()).stream()
                .sorted(Comparator.comparing(Message::getTime))
                .collect(Collectors.toList());
    }

    @Transactional
    public void say(@NotNull User user, @NotNull Date time, @NotNull String value) {
        Message message = new Message();
        message.setUser(user).setTime(time).setValue(value);
        messageDao.save(message);
        log.info("[" + user.getLogin() + "] say: \"" + value + "\"");
    }

}
