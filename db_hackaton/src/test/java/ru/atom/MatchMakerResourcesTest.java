package ru.atom;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.ResultDao;
import ru.atom.dbhackaton.mmserver.MatchMakerResources;
import ru.atom.dbhackaton.server.AuthResources;

import javax.ws.rs.core.Response;

/**
 * Created by ikozin on 17.04.17.
 */
public class MatchMakerResourcesTest {
    @Before
    public void setUp() throws Exception {
        Database.setUp();
        Session session = Database.session();
        for (int i = 1;i <= 4;i++)
            AuthResources.register("user" + i, "qwerty");
        Transaction txn = session.beginTransaction();
        ResultDao.getInstance().clean(session);
        txn.commit();
    }

    @Test
    public void finishOk() throws Exception {
        Response response = MatchMakerResources.finish(
                "{id='12345', 'result':{'user1'=10, 'user2'=15, 'user3'=16, 'user4'=5}}");
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void finishGameIdNotUnique() throws Exception {
        Assert.assertTrue(MatchMakerResources.finish(
                "{id='12346', 'result':{'user1'=10, 'user2'=15, 'user3'=16, 'user4'=5}}").getStatus() == 200);
        Assert.assertTrue(MatchMakerResources.finish(
                "{id='12346', 'result':{'user1'=10, 'user2'=15, 'user3'=16, 'user4'=5}}").getStatus() == 400);
    }
}
