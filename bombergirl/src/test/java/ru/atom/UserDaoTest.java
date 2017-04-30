package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.UserDao;
import ru.atom.bombergirl.model.User;

/**
 * Created by dmitriy on 18.04.17.
 */
public class UserDaoTest {
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
        Transaction txn = session.beginTransaction();
        int beforeInsert = UserDao.getInstance().getAll(session).size();
        UserDao.getInstance().insert(session, new User(typicalName + (int)(Math.random() * 1000), typicalPassword));
        txn.commit();
        Assert.assertTrue(beforeInsert + 1 == UserDao.getInstance().getAll(session).size());
    }

    @Test
    public void getAllTest() throws Exception {
        Transaction txn;
        int beforeInsert = UserDao.getInstance().getAll(session).size();
        for (int i = 0; i < 3; i++) {
            txn = session.beginTransaction();
            UserDao.getInstance().insert(session, new User(typicalName + (int)(Math.random() * 1000), typicalPassword));
            txn.commit();
        }
        Assert.assertTrue(beforeInsert + 3 == UserDao.getInstance().getAll(session).size());
    }

    @Test
    public void getByNameTest() throws Exception {
        String name = typicalName + (int)(Math.random() * 1000);
        Transaction txn = session.beginTransaction();
        UserDao.getInstance().insert(session, new User(name, typicalPassword));
        txn.commit();
        User user = UserDao.getInstance().getByName(session, name);
        Assert.assertTrue(user.getName().equals(name));
        Assert.assertTrue(user.getPassword().equals(typicalPassword));
    }
}
