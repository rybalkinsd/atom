package ru.atom.lecture07.server.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageDaoTest {
    private MessageDao messageDao;
    private UserDao userDao;
    private String msg;
    private Message message;
    private int messagesBeforeTest;
    private String login;

    //TODO check if this test works
    @Before
    public void setUp() throws Exception {
        Database.setUp();
        messageDao = MessageDao.getInstance();
        userDao = UserDao.getInstance();
        msg = "Hiii " + new Random().nextInt(999999);
        login = "USERRR";
        User user = new User().setLogin(login);
        Database.execTransactionalConsumer(s -> userDao.insert(s, user));
        messagesBeforeTest = MessageDao.getInstance().getAll(Database.session()).size();
        message = new Message()
                .setUser(userDao.getByName(Database.session(), login))
                .setValue(msg)
                .setTime(new Date(System.currentTimeMillis()));

        Database.execTransactionalConsumer(s -> messageDao.insert(s, message));
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(MessageDao.getInstance().getAll(Database.session()).size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(messagesBeforeTest + 1, MessageDao.getInstance().getAll(Database.session()).size());
    }
}