package ru.atom.lecture06.server.dao;

import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture06.server.model.Message;
import ru.atom.lecture06.server.model.User;

import java.util.Random;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergey on 3/25/17.
 */
public class MessageDaoTest {
    private MessageDao messageDao;
    private String msg ;
    private Message message;
    private int messagesBeforeTest;
    private String login;
    private User user;

    @Before
    public void setUp() throws Exception {
        login = "Lolita " + new Random().nextInt(999999);
        user = new User()
                .setLogin(login)
                .setId(7);

        messageDao = new MessageDao();
        msg = "Hello World " + new Random().nextInt(999999);
        messagesBeforeTest = messageDao.getAll().size();

        message = new Message()
                .setUser(user)
                .setValue(msg);

        messageDao.insert(message);

        msg = "Hello World " + new Random().nextInt(999999);
        message = new Message()
                .setUser(user)
                .setValue(msg);
        messageDao.insert(message);

        msg = "Hello World " + new Random().nextInt(999999);
        message = new Message()
                .setUser(user)
                .setValue(msg);
        messageDao.insert(message);
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(messageDao.getAll().size() > 0);
    }

    @Test
    public void getAllWhereTest() throws Exception {
        List<Message> lol = messageDao.getAllWhere("login like 'Lolita%'");
        assertTrue(lol.stream()
                .map(Message::getUser)
                .map(User::getLogin)
                .allMatch(s -> s.startsWith("Lolita"))
        );
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(messagesBeforeTest + 3, messageDao.getAll().size());
    }

}