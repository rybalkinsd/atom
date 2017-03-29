package ru.atom;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/**
 * Created by Vlad on 29.03.2017.
 */
public class ServerTest {

    @Before
    public void startServer() throws Exception {
        AuthServer.startServer();
    }

    @Test
    public void registerTest() throws IOException {
        Response response;
        String body;
        response = AuthClient.register("user_john", "123");
        body = response.body().string();
        Assert.assertTrue(response.code() != 200 && body.equals("Too short password (less than 8 symbols)"));

        response = AuthClient.register("us", "123456789");
        body = response.body().string();
        Assert.assertTrue(response.code() != 200 && body.equals("Too short name (less than 3 symbols)"));

        response = AuthClient.register("useruseruseruseruseruser", "123456789");
        body = response.body().string();
        Assert.assertTrue(response.code() != 200 && body.equals("Too long name (more than 20 symbols)"));

        response = AuthClient.register("user_john", "123456789");
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void loginTest() throws IOException {
        String name = "user_steve";
        String password = "123456789";
        Response response;
        AuthClient.register(name, password);
        response = AuthClient.login(name, password);
        Assert.assertTrue(response.code() == 200);

        String body;
        response = AuthClient.login(name + "123", password);
        body = response.body().string();
        Assert.assertTrue(response.code() != 200 && body.equals("User not registered"));

        response = AuthClient.login(name, password + "12");
        body = response.body().string();
        Assert.assertTrue(response.code() != 200 && body.equals("Invalid password"));
    }

    @Test
    public void logoutTest() throws IOException {
        String name = "user_brown";
        String password = "123456789";
        Response response;
        String body;
        AuthClient.register(name, password);
        response = AuthClient.login(name, password);
        body = response.body().string(); // token in body
        response = AuthClient.logout("1");
        Assert.assertTrue(response.code() == 401);

        response = AuthClient.logout(body);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void usersTest() throws IOException {
        Response response;

        response = AuthClient.users();
        Assert.assertTrue(response.code() == 200);
    }

    @After
    public void stopServer() throws Exception {
        AuthServer.stopServer();
    }

}
