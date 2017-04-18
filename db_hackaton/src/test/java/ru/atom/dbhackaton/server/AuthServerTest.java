package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.hibernate.Session;
import org.junit.Test;
import ru.atom.dbhackaton.server.auth.AuthServer;
import ru.atom.dbhackaton.server.auth.Database;
import ru.atom.dbhackaton.server.auth.UserDao;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class AuthServerTest {
    @Test
    public void testServer() throws Exception {
        AuthServer.serverRun();

        Session session = Database.session();
        UserDao ud = UserDao.getInstance();
        ud.deleteByNameTxn(session, "TestNotExistingUser");
        ud.deleteByNameTxn(session, "TestUser");

        Response loginedNonExistingUser1 = HttpClient.login("TestNotExistingUser", "tneupass");
        assertEquals(403, loginedNonExistingUser1.code());

        Response registeredUser = HttpClient.register("TestUser", "tupass");
        assertEquals(200, registeredUser.code());


        Response registeredAgainUser = HttpClient.register("TestUser", "tupass");
        assertEquals(403, registeredAgainUser.code());

        Response registerUserWithInvalidName = HttpClient.register("invalid \"user\"", "password1");
        assertEquals(400, registerUserWithInvalidName.code());

        Response loginedUser = HttpClient.login("TestUser", "tupass");
        final String userToken = loginedUser.body().string();
        assertEquals(200, loginedUser.code());

        Response loginedAgainUser = HttpClient.login("TestUser", "tupass");
        assertEquals(200, loginedAgainUser.code());
        assertEquals(userToken, loginedAgainUser.body().string());

        Response invalidLogin = HttpClient.login("TestUser", "ssaput");
        assertEquals(403, invalidLogin.code());

        Response loggedOutUser = HttpClient.logout(userToken);
        assertEquals(200, loggedOutUser.code());

        Response loggedOutAgainUser = HttpClient.logout(userToken);
        assertNotEquals(200, loggedOutAgainUser.code());

        assertNotNull(ud.getByName(session, "TestUser"));
        ud.deleteByNameTxn(session, "TestUser");

        AuthServer.serverStop();
    }
}
