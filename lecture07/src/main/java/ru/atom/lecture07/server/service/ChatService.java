package ru.atom.lecture07.server.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.lecture07.server.controller.ChatController;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.OnlineState;
import ru.atom.lecture07.server.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    @Nullable
    @Transactional
    public OnlineState getLoggedIn(@NotNull String name) {
        return userDao.getStateByLogin(name);
    }

    @Transactional
    public void login(@NotNull String login) {
        User user = userDao.getByLogin(login);
        if (user == null) {
            user = new User();
            user.setLogin(login);
            userDao.saveUser(user.setLogin(login));
            user = userDao.getByLogin(login);
        }
        userDao.saveState(user.toOnlineState());
        log.info("[" + login + "] logged in");
    }

    @Transactional
    public void logout(@NotNull String name) {
        OnlineState state = userDao.getStateByLogin(name);
        userDao.leave(state);
        log.info("[" + name + "] logged out");

    }

    @Transactional
    public List<Message> getMsgHistory() {
        List<Message> tmp = messageDao.findAll();
        if (tmp == null)
            return null;
        return  new ArrayList<>(tmp);
    }

    @Transactional
    public void say(String name,String message) {
        Message msg = new Message();
        msg.setUser(userDao.getByLogin(name));
        messageDao.save(msg.setValue(message));
        log.info("[" + name + "]: " + message);
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return new ArrayList<>(userDao.findAll());
    }
}
