package server;

import okhttp3.Response;
import org.junit.Assert;


import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Ignore;
import resources.User;
import token.Token;

import java.io.IOException;

/**
 * Created by Robin on 29.03.2017.
 */
public class HttpServerTest {

    private Token token;

    @Before
    public void serverInitializer() throws Exception {
        HttpServer.serverStart();
    }

    @Test
    public void register() throws IOException {
        String user = "TestUser";
        String password = "TestPassword";
        Response response = HttpClient.register(user, password);
        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.body().string().equals("New user registered"));

        Response response2 = HttpClient.register(user, password);
        Assert.assertTrue(response2.code() == 400);
        Assert.assertTrue(response2.body().string().equals("Already registered"));

        response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.body().string().equals("{\"users\":[]}"));

        response = HttpClient.login(user, password);
        Assert.assertTrue(response.code() == 200);
        token = new Token(new User(user, password));
        Assert.assertTrue(token.getToken() == -1384740496);
        Assert.assertTrue(response.body().string().equals("user logged-in with token Token{token=-1384740496}"));

        response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        String str = "{\"users\":[{\"name\":\"TestUser\",\"password\":\"TestPassword\"}]}";
        Assert.assertTrue(response.body().string().equals(str));

        response = HttpClient.logout(token.getToken().toString());
        Assert.assertEquals(200, response.code());
        Assert.assertTrue(response.body().string().equals("log out with token: Token{token=-1384740496}"));
    }

    @Ignore
    @Test
    public void users() throws IOException {

        Response response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.body().string().equals("{\"users\":[]}"));
    }

    @Ignore
    @Test
    public void login() throws IOException {
        register();
        String name = "TestUser";
        String password = "TestPassword";
        Response response = HttpClient.login(name, password);
        Assert.assertTrue(response.code() == 200);
        token = new Token(new User(name, password));
        Assert.assertTrue(token.getToken() == -1384740496);
        Assert.assertTrue(response.body().string().equals("user logged-in with token Token{token=-1384740496}"));
    }

    @Ignore
    @Test
    public void users2() throws IOException {
        register();
        login();
        Response response = HttpClient.users();
        Assert.assertTrue(response.code() == 200);
        //Assert.assertTrue(response.body().string().equals("{\
        //"users\":[{\"name\":\"TestUser\",\"password\":\"TestPassword\"}]}"));
    }

    @Ignore
    @Test
    public void logout() throws IOException {
        register();
        login();
        Response response = HttpClient.logout(token.getToken().toString());
        Assert.assertEquals(200, response.code());
        Assert.assertTrue(response.body().string().equals("log out with token: Token{token=-1384740496}"));
    }

    @After
    public void stopServer() throws Exception {
        HttpServer.serverStop();
    }
}
