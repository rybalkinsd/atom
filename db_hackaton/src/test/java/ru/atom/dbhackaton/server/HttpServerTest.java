package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HttpServerTest {
    @Test
    public void testServer() throws Exception {
        AuthServer.serverRun();

        Response loginedNonExistingUser1 = HttpClient.login("NotExistedUser", "NotExistedPassword");
        assertEquals(403, loginedNonExistingUser1.code());

        String login;
        String password;
        login = "Lolita " + new Random().nextInt(999999);
        password = "testPass";

        Response registeredUser1 = HttpClient.register(login, password);
        assertEquals(200, registeredUser1.code());


        Response registeredAgainUser1 = HttpClient.register("testUser", "testPassword");
        assertEquals(403, registeredAgainUser1.code());

        Response registerUserWithInvalidName = HttpClient.register("invalid \"user\"", "password1");
        assertEquals(400, registerUserWithInvalidName.code());

        Response loginedUser1 = HttpClient.login("testUser", "testPassword");
        String user1Token = loginedUser1.body().string();
        assertEquals(200, loginedUser1.code());

        /*Response loginedAgainUser1 = HttpClient.login("testUser", "testPassword");
        assertEquals(200, loginedAgainUser1.code());
        assertEquals(user1Token, loginedAgainUser1.body().string());*/

        Response invalidLogin = HttpClient.login("user1", "1drowssap");
        assertEquals(403, invalidLogin.code());

        /*Response loggedOutUser1 = HttpClient.logout(user1Token);
        assertEquals(200, loggedOutUser1.code());

        Response loginedAfterLogout = HttpClient.loginedUsers();
        assertEquals("{\"users\" : []}", loginedAfterLogout.body().string());

        Response loggedOutAgainUser1 = HttpClient.logout(user1Token);
        assertNotEquals(200, loggedOutAgainUser1.code());*/

        AuthServer.serverStop();
    }
}
