package ru.atom.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.ResultDao;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.Result;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;

import java.util.Random;

/**
 * Created by BBPax on 19.04.17.
 */
public class DaoTest {
    private User newUser = new User().setLogin("user" + new Random()
            .nextInt(999999))
            .setPassword("password");
    private Token token = new Token();
    private Result result = new Result()
            .setGameId(new Random().nextInt(999999))
            .setScore(4);
    private int usersNumberBefore;
    private int tokensNumberBefore;
    private int resultNumberBefore;
    private UserDao userDao = UserDao.getInstance();
    private TokenDao tokenDao = TokenDao.getInstance();
    private ResultDao resultDao = ResultDao.getInstance();

    @Before
    public void dbSetUp() throws Exception {
        Database.setUp();

        usersNumberBefore = userDao.getAll(Database.session()).size();
        tokensNumberBefore = tokenDao.getAll(Database.session()).size();
        resultNumberBefore = resultDao.getAll(Database.session()).size();
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            userDao.insert(session, newUser);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        token.setUser(newUser).setToken(0L);
        result.setUser(newUser);
        txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            tokenDao.insert(session, token);
            resultDao.insert(session, result);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @Test
    public void insertTest() {
        Assert.assertEquals(usersNumberBefore + 1, userDao.getAll(Database.session()).size());
        Assert.assertEquals(tokensNumberBefore + 1, tokenDao.getAll(Database.session()).size());
        Assert.assertEquals(resultNumberBefore + 1, resultDao.getAll(Database.session()).size());
    }

    @Test
    public void getByNameTest() {
        Transaction txn = null;
        User nextUser = new User();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            nextUser = userDao.getByName(session, newUser.getLogin());
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        Assert.assertEquals(newUser.getLogin(), nextUser.getLogin());
        Assert.assertEquals(newUser.getPassword(), nextUser.getPassword());
        Assert.assertEquals(newUser.getId(), nextUser.getId());
        Assert.assertEquals(newUser.getRegDate(), nextUser.getRegDate());
    }

    @Test
    public void getTokenTest() {
        Token nextToken = TokenDao.getInstance().getToken(Database.session(), token.getToken());
        Assert.assertEquals(token.getUser(), nextToken.getUser());
        Assert.assertEquals(token.getToken(), nextToken.getToken());
        Assert.assertEquals(token.getId(), nextToken.getId());
    }

    @After
    public void tokenDeletion() {
        tokenDao.delete(Database.session(),token);
    }
}
