package ru.atom;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Ignore;
import ru.atom.client.User;
import ru.atom.token.Token;
import java.io.IOException;

/**
 * Created by salvador on 03.04.17.
 */
public class ServTest {

    private Token token;

    @Before
    public void serverInitializer() throws Exception {
        ru.atom.server.HttpServer.ServStart();
    }

    @Test
    public void register() throws IOException {

        Response response = HttpClient.register("TestUser1", "TestPassword1");
        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.body().string().equals("New user registered"));

        Response response2 = HttpClient.register("TestUser", "TestPassword");
        Assert.assertTrue(response2.code() == 400);
        Assert.assertTrue(response2.body().string().equals("Already registered"));

        response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.body().string().equals("{\"users\":[]}"));

        response = HttpClient.login("TestUser", "TestPassword");
        Assert.assertTrue(response.code() == 200);
        token = new Token(new User("TestUser", "TestPassword"));
        Assert.assertTrue(token.getToken() == -1384740496);
        Assert.assertTrue(response.body().string().equals("user logged-in with token Token{token=-1384740496}"));

        response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        String str = "{\"users\":[\"TestUser\"]}";
        Assert.assertTrue(response.body().string().equals(str));

        response = HttpClient.logout(token.getToken().toString());
        Assert.assertEquals(200, response.code());
        Assert.assertTrue(response.body().string().equals("log out with token: Token{token=-1384740496}"));
    }

    @Test
    public void users() throws IOException {
        Response response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        String str = "{\"users\":[\"TestUser\"]}";
        Assert.assertTrue(response.body().string().equals(str));
    }

    @Test
    public void login() throws IOException {
        Response response = HttpClient.login("TestUser", "TestPassword");
        Assert.assertTrue(response.code() == 200);
        token = new Token(new User("TestUser", "TestPassword"));
        Assert.assertTrue(token.getToken() == -1384740496);
        Assert.assertTrue(response.body().string().equals("user logged-in with token Token{token=-1384740496}"));
    }

    @Test
    public void logout() throws IOException {
        HttpClient.register("TestUser", "TestPassword");
        HttpClient.login("TestUser", "TestPassword");
        token = new Token(new User("TestUser", "TestPassword"));
        Response response = HttpClient.logout(token.getToken().toString());
        Assert.assertEquals(200, response.code());
        Assert.assertTrue(response.body().string().equals("log out with token: Token{token=-1384740496}"));
    }


    @After
    public void stopServer() throws Exception {
        ru.atom.server.HttpServer.ServStop();
    }
}
