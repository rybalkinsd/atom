package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.TokenDao;
import ru.atom.bombergirl.dbmodel.Token;
import ru.atom.bombergirl.dbmodel.User;
import ru.atom.bombergirl.server.AuthResources;

import javax.ws.rs.core.Response;


/**
 * Created by dmitriy on 27.03.17.
 */
public class AuthResourcesTest {
    private static final Logger log = LogManager.getLogger(AuthResourcesTest.class);
    static String typicalName = "username";
    static String typicalPassword = "qwerty12345";
    User user;
    String username;
    String username1;

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        username = typicalName + (int)(Math.random() * 10000);
        username1 = typicalName + (int)(Math.random() * 10000) + 1;
    }

    @Test
    public void register0() throws Exception { // simple registration
        Response response = AuthResources.register(username, typicalPassword);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void register1() throws Exception { // multiple registration
        Response response = AuthResources.register(username, typicalPassword);
        Response response1 = AuthResources.register(username, typicalPassword);
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 400);
        String body1 = response1.hasEntity() ? response1.getEntity().toString() : "";
        Assert.assertTrue(body1.equals("Already registered"));
    }

    @Test
    public void login0() throws Exception { // login after registration
        Response response = AuthResources.register(username, typicalPassword);
        Response response1 = AuthResources.login(username, typicalPassword);
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 200);
    }

    @Test
    public void login1() throws Exception { // login without registration
        Response response = AuthResources.login(username, typicalPassword);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 400);
        String body = response.hasEntity() ? response.getEntity().toString() : "";
        Assert.assertTrue(body.equals("Not registered"));
    }

    @Test
    public void login2() throws Exception { // registration with multiple login
        AuthResources.register(username, typicalPassword);
        Response response = AuthResources.login(username, typicalPassword);
        Response response1 = AuthResources.login(username, typicalPassword);
        String body = response.hasEntity() ? response.getEntity().toString() : "";
        String body1 = response1.hasEntity() ? response1.getEntity().toString() : "";
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(body.equals(body1));
    }

    @Test
    public void login3() throws Exception { // login with wrong password
        Response response = AuthResources.register(username, typicalPassword);
        Response response1 = AuthResources.login(username, typicalPassword + "1");
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 400);
        String body = response1.hasEntity() ? response1.getEntity().toString() : "";
    }

    @Test
    public void logout0() throws Exception { // registration, logout and attempt to login again
        AuthResources.register(username, typicalPassword);
        Response responseLogin = AuthResources.login(username, typicalPassword);
        int numberAfterAdding =  TokenDao.getInstance().getAll(Database.session()).size();
        Response responseLogout = AuthResources.logout("Bearer "
                + responseLogin.getEntity().toString()
        );
        log.info("[" + responseLogout + "]");
        log.info("[" + responseLogin + "]");
        Assert.assertTrue(TokenDao.getInstance()
                .getAll(Database.session()).size() == numberAfterAdding - 1);
        Assert.assertTrue(responseLogout.getStatus() == 200);
        Assert.assertTrue(responseLogin.getStatus() == 200);
    }

    @Test
    public void logout1() throws Exception { // logout without registration
        user = new User(username, typicalPassword);
        Token token = new Token(user);
        Response response = AuthResources.logout("Bearer " + token);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 400);
        String body1 = response.hasEntity() ? response.getEntity().toString() : "";
        Assert.assertTrue(body1.equals("Not logined"));
    }

    @Test
    public void hashPassword() throws Exception {
        String hashPasswd = AuthResources.hashPassword(typicalPassword);
        Assert.assertTrue(AuthResources.checkPassword(typicalPassword, hashPasswd));
    }

    @Test
    public void checkPassword() throws Exception {
        String hashPasswd = AuthResources.hashPassword(typicalPassword);
        String hashPasswd1 = AuthResources.hashPassword(typicalPassword + "1");
        Assert.assertTrue(AuthResources.checkPassword(typicalPassword + "1", hashPasswd1));
        Assert.assertTrue(AuthResources.checkPassword(typicalPassword, hashPasswd));
        Assert.assertFalse(AuthResources.checkPassword(typicalPassword + "1", hashPasswd));
        Assert.assertFalse(AuthResources.checkPassword(typicalPassword, hashPasswd1));
    }
}
