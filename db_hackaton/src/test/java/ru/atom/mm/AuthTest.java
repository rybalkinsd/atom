package ru.atom.mm;

import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import ru.atom.client.AuthClient;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.server.AuthServer;

import java.util.Random;

/**
 * Created by BBPax on 19.04.17.
 */
public class AuthTest {
    private User newUser = new User().setLogin("user" + new Random()
            .nextInt(999999))
            .setPassword("password");

    @Before
    public void setUp() throws Exception {
        AuthServer.startUp();
        AuthClient.registration(newUser);
    }
    
    @Test
    public void registrationTest() throws Exception {
        Response response = AuthClient.registration(newUser);
        Assert.assertEquals(400, response.code());
    }

    @Test
    public void loginTest() throws Exception {
        Response response = AuthClient.login(newUser);
        Assert.assertEquals(200, response.code());
        response = AuthClient.login(new User().setLogin("notExisted").setPassword("wrongpass"));
        Assert.assertEquals(400, response.code());
    }

    @Test
    public void logoutTest() throws Exception {
        Response response = AuthClient.login(newUser);
        String token = response.body().string();
        response = AuthClient.logout(token);
        Assert.assertEquals(200, response.code());
        response = AuthClient.logout(token);
        Assert.assertEquals(401, response.code());
    }

    @After
    public void shutdown() throws Exception {
        AuthServer.shutdown();
    }
}
