package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.ResultDao;
import ru.atom.bombergirl.dao.UserDao;
import ru.atom.bombergirl.mmserver.MatchMakerResources;
import ru.atom.bombergirl.dbmodel.Result;
import ru.atom.bombergirl.dbmodel.User;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by dmitriy on 18.04.17.
 */
public class ResultDaoTest {
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
        Transaction txn;
        User user = new User(typicalName + (int)(Math.random() * 1000), typicalPassword);
        final int beforeInsert = ResultDao.getInstance().getAll(session).size();
        txn = session.beginTransaction();
        UserDao.getInstance().insert(session, user);
        txn.commit();
        Result result = new Result(5, user, 10);
        txn = session.beginTransaction();
        ResultDao.getInstance().insert(session, result);
        txn.commit();
        Assert.assertTrue(beforeInsert + 1 == ResultDao.getInstance().getAll(session).size());
    }

    @Test
    public void getAllTest() throws Exception {
        Transaction txn;
        User user;
        Result result;
        int beforeInsert = ResultDao.getInstance().getAll(session).size();
        for (int i = 0; i < 5; i++) {
            txn = session.beginTransaction();
            user = new User(typicalName + (int)(Math.random() * 1000), typicalPassword);
            UserDao.getInstance().insert(session, user);
            txn.commit();
            result = new Result(5, user, 10);
            txn = session.beginTransaction();
            ResultDao.getInstance().insert(session, result);
            txn.commit();
        }
        Assert.assertTrue(beforeInsert + 5 == ResultDao.getInstance().getAll(session).size());
    }

    @Test
    public void getByGameIdTest() throws Exception {
        Transaction txn = session.beginTransaction();
        Response response = MatchMakerResources.finish(
                "{id='12345', 'result':{'user1'=10, 'user2'=15, 'user3'=16, 'user4'=5}}");
        String name = typicalName + (int)(Math.random() * 1000);
        List<Result> results = ResultDao.getInstance().getByGameId(session, 12345);
        results.forEach(s -> {
            Assert.assertTrue(s.getGameId() == 12345);
        });
        txn.commit();
    }

    @Test
    public void cleanTest() throws  Exception {
        Transaction txn = session.beginTransaction();
        ResultDao.getInstance().clean(session);
        Assert.assertTrue(ResultDao.getInstance().getAll(session).size() == 0);
        txn.commit();
    }
}
