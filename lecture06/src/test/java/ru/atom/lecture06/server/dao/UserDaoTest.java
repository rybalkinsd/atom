package ru.atom.lecture06.server.dao;

import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture06.server.model.User;
import ru.atom.lecture06.server.resource.ChatResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergey on 3/25/17.
 */
public class UserDaoTest {
    private UserDao userDao;
    private MessageDao messageDao;
    private String login;
    private User user;
    private int usersBeforeTest;
    private int messagesBeforeTest;

    private ChatResource resource;


    @Before
    public void setUp() throws Exception {
        resource = new ChatResource();
        userDao = new UserDao();
        login = "Lolita " + new Random().nextInt(999999);
        user = new User().setLogin(login);
        usersBeforeTest = userDao.getAll().size();

        messageDao = new MessageDao();
        messagesBeforeTest = messageDao.getAll().size();

        resource.login(login);
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(userDao.getAll().size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(usersBeforeTest + 1, userDao.getAll().size());
    }

    @Test
    public void msgAfterLoginTest() throws Exception {
        assertEquals(messagesBeforeTest + 1, messageDao.getAll().size());
    }

    @Test
    public void findWhereTest() throws Exception {
        List<User> lol = userDao.getAllWhere("login like 'Lol%'");
        assertTrue(
                lol.stream()
                        .map(User::getLogin)
                        .anyMatch(s -> s.startsWith(login))
        );
    }

    @Test
    public void getByNameTest() throws Exception {
        User user = userDao.getByName(login);
        assertEquals(user.getLogin(), login);
    }

}