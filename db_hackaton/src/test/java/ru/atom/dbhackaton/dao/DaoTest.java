package ru.atom.dbhackaton.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.Random;


/**
 * Created by alex on 19.04.17.
 */
//
//public class DaoTest {
//    private User newUser = new User().setLogin("user" + new Random()
//            .nextInt(999999));
//    // .setPassword("password");
//    private Token token = new Token();
//
//    private int usersNumberBefore;
//    private int tokensNumberBefore;
//    private UserDao userDao = UserDao.getInstance();
//    //private TokenDao tokenDao = TokenDao.getInstance();
//
//
//    @Before
//    public void dbSetUp() throws Exception {
//        Database.setUp();
//
//        usersNumberBefore = userDao.getAll(Database.session()).size();
//        //tokensNumberBefore = tokenDao.getAll(Database.session()).size();
//
//        Transaction txn = null;
//        try (Session session = Database.session()) {
//            txn = session.beginTransaction();
//            userDao.insert(session, newUser);
//            txn.commit();
//        } catch (RuntimeException e) {
//            if (txn != null && txn.isActive()) {
//                txn.rollback();
//            }
//        }
//        token.setUser(newUser).setToken(0L);
//        txn = null;
//        try (Session session = Database.session()) {
//            txn = session.beginTransaction();
//            tokenDao.insert(session, token);
//            resultDao.insert(session, result);
//            txn.commit();
//        } catch (RuntimeException e) {
//            if (txn != null && txn.isActive()) {
//                txn.rollback();
//            }
//        }
//    }
//
//    @Test
//    public void insertTest() {
//        Assert.assertEquals(usersNumberBefore + 1, userDao.getAll(Database.session()).size());
//        //Assert.assertEquals(tokensNumberBefore + 1, tokenDao.getAll(Database.session()).size());
//        //Assert.assertEquals(resultNumberBefore + 1, resultDao.getAll(Database.session()).size());
//    }
//}
//
//    @Test
//    public void getByNameTest() {
//        Transaction txn = null;
//        User nextUser = new User();
//        try (Session session = Database.session()) {
//            txn = session.beginTransaction();
//            nextUser = userDao.getByName(session, newUser.getLogin());
//            txn.commit();
//        } catch (RuntimeException e) {
//            if (txn != null && txn.isActive()) {
//                txn.rollback();
//            }
//        }
//        Assert.assertEquals(newUser.getLogin(), nextUser.getLogin());
//        Assert.assertEquals(newUser.getPassword(), nextUser.getPassword());
//        Assert.assertEquals(newUser.getId(), nextUser.getId());
//        Assert.assertEquals(newUser.getRegDate(), nextUser.getRegDate());
//    }
//
//    @Test
//    public void getTokenTest() {
//        Token nextToken = TokenDao.getInstance().getToken(Database.session(), token.getToken());
//        Assert.assertEquals(token.getUser(), nextToken.getUser());
//        Assert.assertEquals(token.getToken(), nextToken.getToken());
//        Assert.assertEquals(token.getId(), nextToken.getId());
//    }
//
//    @After
//    public void tokenDeletion() {
//        tokenDao.delete(Database.session(),token);
//    }
//}



public class DaoTest {
    private UserDao userDao;
    private String login;
    private User user;
    private int usersBeforeTest;


    @Before
    public void setUp() throws Exception {
        Database.setUp();
        userDao = UserDao.getInstance();
        login = "Lolita " + new Random().nextInt(999999);
        user = new User().setLogin(login);
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
