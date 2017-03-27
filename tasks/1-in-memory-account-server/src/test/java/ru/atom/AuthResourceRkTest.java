package ru.atom;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by pavel on 27.03.17.
 */
@Ignore
public class AuthResourceRkTest {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    @Test
    public void registerTest() throws IOException {
        String user = "vasya";
        String password = "1234";
        Response response = HttpClientRk.register(user, password);
        String bodyResponse = response.body().string();
        Assert.assertTrue(bodyResponse.equals("Registration success!"));
    }

    @Test
    public void loginTest() throws IOException {
        String user = "vasya1";
        String password = "12341";
        HttpClientRk.register(user, password);
        Response response1 = HttpClientRk.login(user, password);
        Assert.assertTrue(response1.code() == 200);
    }

    @Test
    public void logoutTest() throws IOException {
        String user = "vasya11";
        String password = "123411";
        HttpClientRk.register(user, password);
        Response response1 = HttpClientRk.login(user, password);
        String tocken = response1.body().string();
        Response response2 = HttpClientRk.logout(tocken);
        Assert.assertTrue(response2.code() == 200);
    }
}
