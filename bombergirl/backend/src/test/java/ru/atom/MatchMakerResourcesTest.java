package ru.atom;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.ResultDao;
import ru.atom.bombergirl.mmserver.MatchMakerResources;
import ru.atom.bombergirl.mmserver.ThreadSafeQueue;
import ru.atom.bombergirl.server.AuthResources;

import javax.ws.rs.core.Response;

/**
 * Created by ikozin on 17.04.17.
 */
public class MatchMakerResourcesTest {
    Response responseOfLogin;

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        Session session = Database.session();
        Transaction txn;
        for (int i = 1;i <= 4;i++) {
            txn = session.beginTransaction();
            AuthResources.register("user" + i, "qwerty");
            txn.commit();
        }
        responseOfLogin = AuthResources.login("user" + 1, "qwerty");
        AuthResources.login("user" + 2, "qwerty");
        txn = session.beginTransaction();
        ResultDao.getInstance().clean(session);
        txn.commit();
    }

    @Test
    public void finishOk() throws Exception {
        Response response =  MatchMakerResources.finish(
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

    @Test
    public void joinSimple() throws Exception {
        String token = responseOfLogin.hasEntity() ? responseOfLogin.getEntity().toString() : "";
        Response response = MatchMakerResources.join("user" + 1, token);
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void joinWithWrongToken() throws Exception {
        String token = responseOfLogin.hasEntity() ? responseOfLogin.getEntity().toString() : "";
        Response response = MatchMakerResources.join("user" + 2, token + "salt");
        Assert.assertTrue(response.getStatus() == 400);
    }
}
