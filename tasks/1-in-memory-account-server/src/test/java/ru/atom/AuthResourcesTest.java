package ru.atom;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.IOException;

/**
 * Created by alex on 29.03.17.
 */
public class AuthResourcesTest {
    @Before
    public void setUp() throws Exception {
        HttpServer.serverStart();
    }

    @After
    public void setDown() throws Exception {
        HttpServer.serverStop();
    }

    @Test
    public void registerTest() throws IOException {
        Response response = AuthClient.register("qwerty", "12345");
        Assert.assertTrue(response.body().string().equals("Registration success!"));
    }

    @Test
    public void trueLoginTest() throws IOException {
        String user = "abcde";
        String password = "0377";
        AuthClient.register(user, password);
        Response response = AuthClient.login(user, password);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void falseLoginTest() throws IOException {
        Response response = AuthClient.login("alex", "hello");
        Assert.assertTrue(response.body().string().equals("Problems with login"));
    }

    @Test
    public void logoutTest() throws IOException {
        String user = "a1b2";
        String password = "88005553535";
        AuthClient.register(user, password);
        Response response1 = AuthClient.login(user, password);
        String token = response1.body().string();
        Response response2 = AuthClient.logout(token);
        Assert.assertTrue(response2.code() == 200);
    }

    @Test
    public void usersTest() throws IOException {
        Response response = AuthClient.users();
        Assert.assertTrue(response.code() == 200);
    }

}
