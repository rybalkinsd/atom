package ru.atom;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.ComparisonFailure;


import java.io.IOException;

import static ru.atom.AuthServer.startServer;
import static ru.atom.AuthServer.stopServer;
import static ru.atom.AuthServer.tokens;

public class ClientTest {

    @Before
    public void setUp() throws Exception {
        startServer();
    }

    @After
    public void stop() throws Exception {
        stopServer();
    }

    @Test
    public void registerTest() throws IOException {
        Response response = Client.register("123", "123");
        String resp = response.body().string();
        Assert.assertEquals(resp, "You are signed up!");
    }

    @Test
    public void incorrectPassword() throws IOException {
        String user = "123";
        String password = "123";
        Client.register(user, password);
        Response response = Client.login(user, password + "1");
        String resp = response.body().string();
        Assert.assertEquals(resp.substring(0,resp.length() - 2), "Incorrect password");
    }

    @Test
    public void trueLoginTest() throws IOException, ComparisonFailure {
        String user = "123";
        String password = "123";
        Client.register(user, password);
        Response response = Client.login(user, password);
        String resp = response.body().string();
        Assert.assertEquals(resp.substring(0, resp.length() - 2),
                new String("You are logined" + tokens.getTokenByName("123").toString()));
    }

    @Test
    public void alreadyLoginedTest() throws IOException, ComparisonFailure {
        String user = "123";
        String password = "123";
        Client.register(user, password);
        Client.login(user, password);
        Response response = Client.login(user, password);
        String resp = response.body().string();
        Assert.assertEquals(resp.substring(0, resp.length() - 2),
                "You are already logined" + tokens.getTokenByName("123").toString());
    }

    @Test
    public void falseLoginTest() throws IOException {
        Response response = Client.login("123", "123");
        String resp = response.body().string();
        Assert.assertTrue(resp.substring(0, resp.length() - 2).equals("Not registred"));
    }

    @Test
    public void usersTest() throws IOException, ComparisonFailure {
        Client.register("123", "123");
        Response response = Client.users();
        String body = response.body().string();
        Assert.assertEquals(body.substring(0, body.length() - 2), "{\"users\":[\"123\"]}");
    }
}