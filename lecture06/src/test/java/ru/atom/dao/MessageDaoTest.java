package ru.atom.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.atom.model.Message;
import ru.atom.model.User;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergey on 3/25/17.
 */
public class MessageDaoTest {
    private MessageDao messageDao;
    private UserDao userDao;
    private String msg;
    private String login;
    private Message message;
    private User user;
    private int messagesBeforeTest;

    @Before
    public void setUp() throws Exception {
        messageDao = new MessageDao();
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = messageDao.getAll().size();
        userDao = new UserDao();
        login = "Lolita " + new Random().nextInt(999999);
        user = new User().setLogin(login);
        userDao.insert(user);
        user = userDao.getByName(login);
        message = new Message().setUser(user).setValue(msg);
        messageDao.insert(message);
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(messageDao.getAll().size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(messagesBeforeTest + 1, messageDao.getAll().size());
    }

    @After
    public void tearDown() {
        userDao.delete(user);
    }
}