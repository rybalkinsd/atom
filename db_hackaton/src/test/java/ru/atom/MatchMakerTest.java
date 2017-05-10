package ru.atom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.atom.dbhackaton.auth.dao.Database;
import ru.atom.dbhackaton.auth.dao.UserDao;
import ru.atom.dbhackaton.auth.model.Token;
import ru.atom.dbhackaton.auth.model.User;
import ru.atom.dbhackaton.auth.server.Services;
import ru.atom.dbhackaton.mm.dao.ResultDao;
import ru.atom.dbhackaton.mm.model.Result;
import ru.atom.dbhackaton.mm.server.MatchMakerServices;


import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 20.04.2017.
 */

public class MatchMakerTest {
    Response responseOfLogin;
    private static final Logger log = LogManager.getLogger(AuthResourcesTest.class);
    static String typicalName = "username";
    static String typicalPassword = "12345password";
    String username;
    String[] token = new String[4];
    Services authServices = new Services();
    MatchMakerServices mmServices = new MatchMakerServices();

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        username = typicalName + (int) (Math.random() * 10000);
        for (int i = 0; i < 4; i++) {
            authServices.register(username + i, typicalPassword + i);
        }
        for (int i = 0; i < 4; i++) {
            token[i] = authServices.login(username + i, typicalPassword + i)
                    .getEntity()
                    .toString();
        }

        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            ResultDao.getInstance().clean(session);
            log.info("Result database has been cleaned");

            txn.commit();
        } catch (Exception e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            log.info("Transaction exception: " + e.toString());
        }
    }

    @Test
    public void finish() throws Exception {
        Response response1 = mmServices.finish(
                "{id='1234', 'result':{'user1'=1, 'user2'=2, 'user3'=3, 'user4'=4}}");
        Response response2 = mmServices.finish(
                "{id='1234', 'result':{'user1'=1, 'user2'=2, 'user3'=3, 'user4'=4}}");
        Assert.assertTrue(response1.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 200);
    }

    @Test
    public void join() throws Exception {
        String[] url = new String[4];
        int[] number = new int[4];
        for (int i = 0; i < 4; i++) {
            url[i] = mmServices.join(token[i]).getEntity().toString();
        }
        for (int i = 0; i < 4; i++) {
            number[i] = Integer.parseInt(url[i].substring("localhost:8095/gs/".length()));
        }
        for (int i = 0; i < 3; i++) {
            Assert.assertTrue(number[i + 1] - number[i] == 1);
        }
    }

    @Test
    public void joinWithWrongToken() throws Exception {
        String wrongToken = "odinnadstatiklassnitsa";
        Response response = mmServices.join(wrongToken);
        Assert.assertTrue(response.getStatus() == 400);
    }
}
