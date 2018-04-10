package ru.atom.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.model.Message;
import ru.atom.model.User;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergey on 3/25/17.
 */
@Ignore
public class MessageDaoTest {
    private MessageDao messageDao;
    private String msg ;
    private Message message;
    private int messagesBeforeTest;

    @Before
    public void setUp() throws Exception {
        messageDao = new MessageDao();
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = messageDao.getAll().size();
        message = new Message()
                .setUser(new User().setId(7))
                .setValue(msg);

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

}