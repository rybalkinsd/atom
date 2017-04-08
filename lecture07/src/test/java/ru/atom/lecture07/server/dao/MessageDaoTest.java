package ru.atom.lecture07.server.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class MessageDaoTest {
    private MessageDao messageDao;
    private String msg ;
    private Message message;
    private int messagesBeforeTest;

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        Database.execTransactionalConsumer(s -> UserDao.getInstance()
                .insert(s, new User().setLogin("test user")));
        messageDao = MessageDao.getInstance();
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = messageDao.getAll(Database.session()).size();
        message = new Message()
                .setUser(UserDao.getInstance().getByName(Database.session(), "test user"))
                .setValue(msg);

        Database.execTransactionalConsumer(s -> messageDao.insert(s, message));
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(messageDao.getAll(Database.session()).size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(messagesBeforeTest + 1, messageDao.getAll(Database.session()).size());
    }
}