package ru.atom.lecture07.server.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

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
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = MessageDao.getInstance().getAll(Database.session()).size();
        message = new Message()
                .setUser(new User().setLogin("test user" + new Random().nextInt(999999)))
                .setValue(msg);

        Transaction tnx = null;
        try (Session session = Database.session()) {
            tnx = session.beginTransaction();
            MessageDao.getInstance().insert(session, message);
            tnx.commit();
        } catch (RuntimeException e) {
            if (tnx != null && tnx.isActive()) {
                tnx.rollback();
            }
        }
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