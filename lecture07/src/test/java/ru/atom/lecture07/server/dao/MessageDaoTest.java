package ru.atom.lecture07.server.dao;

import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MessageDaoTest {
    private String msg;
    private Message message;
    private int messagesBeforeTest;

    @Before
    public void setUp() throws Exception {
        Database.setUp();

        String login = "Kolobok" + String.valueOf((new Random().nextInt(999999)));
        msg = "Hello World from " + login;

        messagesBeforeTest = MessageDao.getInstance().getAll(Database.session()).size();
        User user = new User().setLogin(login);

        Database.execTransactionalConsumer(s -> UserDao.getInstance().insert(s, user));

        message = new Message()
                .setUser(user)
                .setValue(msg)
                .setTime(new Date(System.currentTimeMillis()));

        Database.execTransactionalConsumer(s -> MessageDao.getInstance().insert(s, message));
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(MessageDao.getInstance().getAll(Database.session()).size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        System.out.println(MessageDao.getInstance().getAll(Database.session()));

        assertEquals(messagesBeforeTest + 1, MessageDao.getInstance().getAll(Database.session()).size());
    }

}