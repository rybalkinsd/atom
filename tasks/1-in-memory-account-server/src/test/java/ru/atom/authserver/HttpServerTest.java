package ru.atom.authserver;

import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HttpServerTest {
    @Test public void testServer() throws Exception {
        HttpServer.serverRun();

        Response initiallyLogined = HttpClient.loginedUsers();
        assertEquals("{\"users\" : []}", initiallyLogined.body().string());

        Response loginedNonExistingUser1 = HttpClient.login("user1", "password1");
        assertEquals(400, loginedNonExistingUser1.code());

        Response registeredUser1 = HttpClient.register("user1", "password1");
        assertEquals(200, registeredUser1.code());

        Response registeredAgainUser1 = HttpClient.register("user1", "password1");
        assertEquals(400, registeredAgainUser1.code());

        Response registerUserWithInvalidName = HttpClient.register("invalid \"user\"", "password1");
        assertEquals(400, registerUserWithInvalidName.code());

        Response loginedUser1 = HttpClient.login("user1", "password1");
        String user1Token = loginedUser1.body().string();
        assertEquals(200, loginedUser1.code());

        Response loginedAgainUser1 = HttpClient.login("user1", "password1");
        assertEquals(200, loginedAgainUser1.code());
        assertEquals(user1Token, loginedAgainUser1.body().string());

        Response invalidLogin = HttpClient.login("user1", "1drowssap");
        assertEquals(400, invalidLogin.code());

        Response loginedUsersAfterUser1 = HttpClient.loginedUsers();
        assertEquals(200, loginedUsersAfterUser1.code());
        assertEquals("{\"users\" : [{\"name\" : \"user1\"}]}", loginedUsersAfterUser1.body().string());

        Response loggedOutUser1 = HttpClient.logout(user1Token);
        assertEquals(200, loggedOutUser1.code());

        Response loginedAfterLogout = HttpClient.loginedUsers();
        assertEquals("{\"users\" : []}", loginedAfterLogout.body().string());

        Response loggedOutAgainUser1 = HttpClient.logout(user1Token);
        assertNotEquals(200, loggedOutAgainUser1.code());

        HttpServer.serverStop();
    }
}
