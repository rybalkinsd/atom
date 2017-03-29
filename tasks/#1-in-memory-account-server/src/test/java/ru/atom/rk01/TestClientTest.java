package ru.atom.rk01;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by dmbragin on 3/29/17.
 */

public class TestClientTest {
    private String testToken;
    private final String testName = "test";
    private final String testPasswd = "test";

    @Before
    public void setUp() throws Exception {
        AuthServer.start();
        Response response = TestClient.register(testName, testPasswd);
        System.out.println("[" + response + "]");
        Response responseLogin = TestClient.login(testName, testPasswd);
        String body = responseLogin.body().string();
        testToken = body;
        System.out.println("[" + responseLogin + "]");
        Response responseRegister = TestClient.register("Lolita", testPasswd);
        System.out.println("[" + responseRegister + "]");
    }

    @After
    public void tearDown() throws Exception {
        AuthServer.stop();
    }

    @Test
    public void viewOnline() throws Exception {
        Response response = TestClient.login(testName, testPasswd);
        String body = response.body().string();
        Response responseUsers = TestClient.viewOnline(body);
        assertEquals(responseUsers.code(), 200);
        Response responseUsersError = TestClient.viewOnline("");
        assertEquals(responseUsersError.code(), 401);

    }

    @Test
    public void register() throws Exception {
        Response responseError = TestClient.register(testName, testPasswd);
        assertEquals(responseError.code(), 400);
    }

    @Test
    public void login() throws Exception {
        Response responseLogin = TestClient.login(testName, testPasswd);
        String body = responseLogin.body().string();
        assertEquals(body, testToken);
        Response response = TestClient.login(testName, testPasswd);
        assertEquals(response.code(), 200);
    }

    @Test
    public void logout() throws Exception {
        Response response = TestClient.login("Lolita", testPasswd);
        String body = response.body().string();
        System.out.println("[" + response + "]");
        Response responseLogout = TestClient.logout(body);
        System.out.println("[" + responseLogout + "]");
        System.out.println();
        Assert.assertTrue(responseLogout.code() == 200);
        Response responseLogoutError = TestClient.logout(body);
        assertEquals(responseLogoutError.code(), 401);
    }

}