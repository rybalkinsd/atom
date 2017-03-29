package ru.atom;

import com.google.gson.Gson;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by serega on 28.03.17.
 */
public class AuthResourcesTest {
    @Before
    public void setUp() throws Exception {
        AuthServer.serverStart();
    }

    @After
    public void setDown() throws Exception {
        AuthServer.serverStop();
    }

    @Test
    public void trueRegisterTest() throws IOException {
        Response response = AuthClient.register("Ivan", "Vano77");
        Assert.assertTrue(response.body().string().equals("Registration success!"));
    }

    @Test
    public void falseRegisterTest() throws IOException {
        Response response = AuthClient.register("kt;kgjtkeg/negregregrawegwfweferfr", "Vano77");
        Assert.assertTrue(response.body().string().equals("Invalid name!"));
    }

    @Test
    public void trueLoginTest() throws IOException {
        String user = "Dmitry";
        String password = "Dimon228";
        AuthClient.register(user, password);
        Response response = AuthClient.login(user, password);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void falseLoginTest() throws IOException {
        Response response = AuthClient.login("Vasya", "hello");
        Assert.assertTrue(response.body().string().equals("You are not registered"));
    }

    @Test
    public void logoutTest() throws IOException {
        String user = "Sergey";
        String password = "322228";
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
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> map = new HashMap<>();
        map.put("users",  UsersCache.getRegisteredUsers());
        Assert.assertEquals(gson.toJson(map), response.body().string());
    }

    @Test
    public void onlineTest() throws IOException {
        Response response = AuthClient.online();
        Assert.assertTrue(response.code() == 200);
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> map = new HashMap<>();
        map.put("users",  UsersCache.getLoginedUsers());
        Assert.assertEquals(gson.toJson(map), response.body().string());
    }
}
