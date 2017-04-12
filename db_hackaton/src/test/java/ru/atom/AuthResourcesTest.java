package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.User;
import ru.atom.dbhackaton.server.AuthResources;

import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by dmitriy on 27.03.17.
 */
public class AuthResourcesTest {
    private static final Logger log = LogManager.getLogger(AuthResourcesTest.class);
    static AtomicInteger atomInt = new AtomicInteger(1);
    static String typicalName = "username";
    static String typicalPassword = "qwerty12345";
    User user;

    @Test
    public void register0() throws Exception { // simple registration
        String username = typicalName + atomInt.getAndIncrement();

        Response response = AuthResources.register(username, typicalPassword);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 200);
    }

    @Test
    public void register1() throws Exception { // multiple registration
        String username = typicalName + atomInt.getAndIncrement();

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
        String username = typicalName + atomInt.getAndIncrement();

        Response response = AuthResources.register(username, typicalPassword);
        Response response1 = AuthResources.login(username, typicalPassword);
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 200);
    }

    @Test
    public void login1() throws Exception { // login without registration
        String username = typicalName + atomInt.getAndIncrement();

        Response response = AuthResources
                .login(username, typicalPassword);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 400);
        String body = response.hasEntity() ? response.getEntity().toString() : "";
        Assert.assertTrue(body.equals("Not registered"));
    }

    @Test
    public void login2() throws Exception { // registration with multiple login
        String username = typicalName + atomInt.getAndIncrement();

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
        String username = typicalName + atomInt.getAndIncrement();

        Response response = AuthResources.register(username, typicalPassword);
        Response response1 = AuthResources.login(username, typicalPassword + "1");
        log.info("[" + response + "]");
        log.info("[" + response1 + "]");
        Assert.assertTrue(response.getStatus() == 200);
        Assert.assertTrue(response1.getStatus() == 400);
        String body = response1.hasEntity() ? response1.getEntity().toString() : "";
        Assert.assertTrue(body.equals("Not valid data"));
    }

    @Test
    public void logout0() throws Exception { // registration, logout and attempt to login again
        String username = typicalName + atomInt.getAndIncrement();
        String username1 = typicalName + atomInt.getAndIncrement();

        AuthResources.register(username, typicalPassword);
        AuthResources.register(username1, typicalPassword);
        Response responseLogin = AuthResources.login(username, typicalPassword);
        Response responseLogout = AuthResources.logout("Bearer "
                + responseLogin.getEntity().toString()
        );
        log.info("[" + responseLogout + "]");
        log.info("[" + responseLogin + "]");
        Assert.assertTrue(responseLogout.getStatus() == 200);
        Assert.assertTrue(responseLogin.getStatus() == 200);
    }

    @Test
    public void logout1() throws Exception { // logout without registration
        user = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);

        Token token = new Token(user);
        Response response = AuthResources.logout("Bearer " + token);
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 400);
        String body1 = response.hasEntity() ? response.getEntity().toString() : "";
        Assert.assertTrue(body1.equals("Such user is not registered"));
    }
}
