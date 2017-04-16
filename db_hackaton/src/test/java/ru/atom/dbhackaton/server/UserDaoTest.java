package ru.atom.dbhackaton.server;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserDaoTest {
    private UserDao userDao;
    private String login;
    private String password;
    private User user;
    private int usersBeforeTest;


    @Before
    public void setUp() throws Exception {
        Database.setUp();
        userDao = UserDao.getInstance();
        login = "Lolita " + new Random().nextInt(999999);
        password = "testPass";
        user = new User(login, password);
        usersBeforeTest = userDao.getAll(Database.session()).size();

        Database.execTransactionalConsumer(s -> userDao.insert(s, user));
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(userDao.getAll(Database.session()).size() > 0);
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(usersBeforeTest + 1, userDao.getAll(Database.session()).size());
    }
}