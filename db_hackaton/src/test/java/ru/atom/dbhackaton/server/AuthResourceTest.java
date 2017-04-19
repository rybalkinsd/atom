package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

/**
 * Created by pavel on 17.04.17.
 */
public class AuthResourceTest {
    @Before
    public void setUp() throws Exception {
        AuthServer.startServer();
    }

    @Test
    public void registerTest() throws IOException {
        String user = "123";
        String password = "123";
        Response response = AuthClient.register(user, password);
        String bodyResponse = response.body().string();
        Assert.assertTrue(bodyResponse.equals("Already registered"));  // Success registration
    }


    @Test
    public void loginTest() throws IOException {
        String user = "1234";
        String password = "1234";
        AuthClient.register(user, password);
        Response response1 = AuthClient.login(user, password);
        Assert.assertTrue(response1.code() == 200);
    }

    @Test
    public void logoutTest() throws IOException {
        String user = "123";
        String password = "123";
        AuthClient.register(user, password);
        Response response1 = AuthClient.login(user, password);
        String token = response1.body().string();
        Response response2 = AuthClient.logout(token);
        Assert.assertTrue(response2.code() == 200);
    }

    @After
    public void setDown() throws Exception {
        AuthServer.stopServer();
    }
}
