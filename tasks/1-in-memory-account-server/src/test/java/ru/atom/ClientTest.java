package ru.atom;

/**
 * Created by user on 28.03.2017.
 */
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ClientTest {
    private static String LOGIN = "admin";
    private static String PASSWORD = "pass";
    private long token;

    @Test
    public void register() throws IOException {
        Response response = Client.register(LOGIN, PASSWORD);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void login() throws IOException {
        Response response = Client.login(LOGIN, PASSWORD);
        System.out.println("[" + response + "]");
        token = Long.parseLong(response.body().string());
        System.out.println(token);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void online() throws IOException {
        Response response = Client.online();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void logout() throws IOException {
        Response response1 = Client.login(LOGIN, PASSWORD);
        token = Long.parseLong(response1.body().string());
        Response response = Client.logout(token);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }
}
