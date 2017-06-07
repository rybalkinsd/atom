package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.User;

/**
 * Created by dmitriy on 18.04.17.
 */
public class TokenDaoTest {
    private static final Logger log = LogManager.getLogger(AuthResourcesTest.class);
    static String typicalName = "username";
    static String typicalPassword = "qwerty12345";
    Session session;

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        session = Database.session();
    }

    @Test
    public void insertTest() throws Exception {
        User user = new User(typicalName + (int)(Math.random() * 1000), typicalPassword);
        final int beforeInsert = TokenDao.getInstance().getAll(session).size();
        Transaction txn = session.beginTransaction();
        UserDao.getInstance().insert(session, user);
        txn.commit();
        Token token = new Token(user);
        token.setUser(user);
        txn = session.beginTransaction();
        TokenDao.getInstance().insert(session, token);
        txn.commit();
        Assert.assertTrue(beforeInsert + 1 == TokenDao.getInstance().getAll(session).size());
    }

    @Test
    public void getAllTest() throws Exception {
        Transaction txn;
        User user;
        int beforeInsert = TokenDao.getInstance().getAll(session).size();
        Token token;
        for (int i = 0; i < 3; i++) {
            txn = session.beginTransaction();
            user = new User(typicalName + (int)(Math.random() * 1000), typicalPassword);
            UserDao.getInstance().insert(session, user);
            txn.commit();
            token = new Token(user);
            token.setUser(user);
            txn = session.beginTransaction();
            TokenDao.getInstance().insert(session, token);
            txn.commit();
        }
        Assert.assertTrue(beforeInsert + 3 == TokenDao.getInstance().getAll(session).size());
    }

    @Test
    public void getByStrTokenTest() throws Exception {
        Transaction txn = session.beginTransaction();
        String name = typicalName + (int)(Math.random() * 1000);
        User user = new User(name, typicalPassword);
        UserDao.getInstance().insert(session, user);
        txn.commit();
        Token token = new Token(UserDao.getInstance().getByName(session, name));
        token.setUser(user);
        txn = session.beginTransaction();
        TokenDao.getInstance().insert(session, token);
        txn.commit();
        Token token1 = TokenDao.getInstance().getByStrToken(session, token.getToken());
        Assert.assertTrue(token1.equals(token));
    }

    @Test
    public void cleanTest() throws  Exception {
        Transaction txn = session.beginTransaction();
        TokenDao.getInstance().clean(session);
        Assert.assertTrue(TokenDao.getInstance().getAll(session).size() == 0);
        txn.commit();
    }
}
