package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.auth.dao.Database;

import ru.atom.dbhackaton.auth.server.Services;
import javax.ws.rs.core.Response;

/**
 * Created by Сергей on 20.04.2017.
 */

public class AuthResourcesTest {
    private static final Logger log = LogManager.getLogger(AuthResourcesTest.class);
    static String typicalName = "username";
    static String typicalPassword = "12345password";
    String username;
    Services services = new Services();

    @Before
    public void setUp() throws Exception {
        Database.setUp();
        username = typicalName + (int) (Math.random() * 10000);
    }

    @Test
    public void checkIncorrectLength() throws Exception {
        String smallIncorrectName = "";
        String longIncorrectName = "odinnadtsatiklassnitsa";
        String smallIncorrectPass = "";
        String longIncorrectPass = "odinnadtsatiklassnitsaPassword";

        Response response1 = services.register(smallIncorrectName, typicalPassword);
        Response response2 = services.register(longIncorrectName, typicalPassword);
        Response response3 = services.register(username, smallIncorrectPass);
        Response response4 = services.register(username, longIncorrectPass);
        log.info("[" + response1 + "]");
        log.info("[" + response2 + "]");
        log.info("[" + response3 + "]");
        log.info("[" + response4 + "]");

        String body1 = "";
        if (response1.hasEntity()) body1 = response1.getEntity().toString();
        String body2 = "";
        if (response2.hasEntity()) body2 = response2.getEntity().toString();
        String body3 = "";
        if (response3.hasEntity()) body3 = response3.getEntity().toString();
        String body4 = "";
        if (response4.hasEntity()) body4 = response4.getEntity().toString();

        Assert.assertTrue(response1.getStatus() == 400);
        Assert.assertTrue(response2.getStatus() == 400);
        Assert.assertTrue(response3.getStatus() == 400);
        Assert.assertTrue(response4.getStatus() == 400);
        Assert.assertTrue(body1.equals("Too short name, sorry :("));
        Assert.assertTrue(body2.equals("Too long name, sorry :("));
        Assert.assertTrue(body3.equals("Too short pass, sorry :("));
        Assert.assertTrue(body4.equals("Too long pass, sorry :("));
    }

    @Test
    public void doubleRegister() throws Exception {
        Response response1 = services.register(username, typicalPassword);
        Response response2 = services.register(username, typicalPassword);
        log.info("[" + response1 + "]");
        log.info("[" + response2 + "]");
        String body2 = "";
        if (response2.hasEntity()) body2 = response2.getEntity().toString();
        Assert.assertTrue(response1.getStatus() == 200);
        Assert.assertTrue(response2.getStatus() == 400);
        Assert.assertTrue(body2.equals("Already registered"));
    }

    @Test
    public void loginWithRegistration() throws Exception {
        Response responseRegister = services.register(username, typicalPassword);
        Response responseLogin = services.login(username, typicalPassword);
        log.info("[" + responseRegister + "]");
        log.info("[" + responseLogin + "]");
        Assert.assertTrue(responseRegister.getStatus() == 200);
        Assert.assertTrue(responseLogin.getStatus() == 200);
    }

    @Test
    public void loginWithoutRegistration() throws Exception {
        Response response = services.login(username, typicalPassword);
        String body = "";
        if (response.hasEntity()) body = response.getEntity().toString();
        log.info("[" + response + "]");
        Assert.assertTrue(response.getStatus() == 400);
        Assert.assertTrue(body.equals("Not registered"));
    }

    @Test
    public void doubleLogin() throws Exception {
        services.register(username, typicalPassword);
        Response response1 = services.login(username, typicalPassword);
        Response response2 = services.login(username, typicalPassword);
        String body1 = "";
        String body2 = "";
        if (response1.hasEntity()) body1 = response1.getEntity().toString();
        if (response2.hasEntity()) body2 = response2.getEntity().toString();
        log.info("[" + response1 + "]");
        log.info("[" + response2 + "]");
        Assert.assertTrue(response1.getStatus() == 200);
        Assert.assertTrue(response2.getStatus() == 400);
        Assert.assertTrue(body2.equals("Already logined"));
    }

    @Test
    public void loginWithWrongPass() throws Exception {
        Response response1 = services.register(username, typicalPassword);
        Response response2 = services.login(username, typicalPassword + "1");
        log.info("[" + response1 + "]");
        log.info("[" + response2 + "]");
        String body2 = "";
        if (response2.hasEntity()) body2 = response2.getEntity().toString();

        Assert.assertTrue(response1.getStatus() == 200);
        Assert.assertTrue(response2.getStatus() == 400);
        Assert.assertTrue(body2.equals("Wrong password"));
    }

    @Test
    public void loginAfterLogout() throws Exception {
        Response responseRegister = services.register(username, typicalPassword);
        Response responseLoginBefore = services.login(username, typicalPassword);
        Response responseLogout = services.logout("Bearer "
                + responseLoginBefore.getEntity().toString()
        );
        Response responseLoginAfter = services.login(username, typicalPassword);

        log.info("[" + responseRegister + "]");
        log.info("[" + responseLoginBefore + "]");
        log.info("[" + responseLogout + "]");
        log.info("[" + responseLoginAfter + "]");

        Assert.assertTrue(responseRegister.getStatus() == 200);
        Assert.assertTrue(responseLogout.getStatus() == 200);
        Assert.assertTrue(responseLoginBefore.getStatus() == 200);
        Assert.assertTrue(responseLoginAfter.getStatus() == 200);
    }

    @Test
    public void logoutWithoutRegistration() throws Exception {
        String emptyToken = "";
        String incorrectToken = "odinnadtsatiklassnitsa";
        Response response1 = services.logout("Bearer " + emptyToken);
        Response response2 = services.logout("Bearer " + incorrectToken);
        String body1 = "";
        if (response1.hasEntity()) body1 = response1.getEntity().toString();
        String body2 = "";
        if (response2.hasEntity()) body2 = response2.getEntity().toString();

        log.info("[" + response1 + "]");
        log.info("[" + response2 + "]");

        Assert.assertTrue(response1.getStatus() == 400);
        Assert.assertTrue(response2.getStatus() == 400);
        Assert.assertTrue(body1.equals("Not logined"));
        Assert.assertTrue(body2.equals("Not logined"));
    }
}
