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
    private String msg ;
    private Message message;
    private int messagesBeforeTest;

    //TODO check if this test works
    @Before
    public void setUp() throws Exception {
        Database.setUp();
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = MessageDao.getInstance().getAll(Database.session()).size();
        message = new Message()
                .setUser(new User().setLogin("test user"))
                .setValue(msg);

        Database.execTransactionalConsumer(s -> MessageDao.getInstance().insert(Database.session(), message));
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