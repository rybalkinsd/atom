package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.service.AuthException;
import ru.atom.dbhackaton.server.service.AuthService;

import java.io.IOException;

/**
 * Created by allen on 18.04.2017.
 */
public class AuthTest {

    private static final String USER_1 = "Sasha";
    private static final String USER_2 = "Dima";
    private static final String USER_3 = "Luba";
    private static final String USER_4 = "Sccc";
    private static final String PASSWORD_1 = "Sashapass";
    private static final String PASSWORD_2 = "Dimapass";
    private static final String PASSWORD_3 = "Lubapass";
    private static final String PASSWORD_4 = "Scccpass";

    @Before
    public void setup() throws Exception{
        AuthServer.authStart();
    }

    @After
    public void stop() throws Exception{
        AuthServer.authStop();
    }

    @Test
    public void register() throws IOException {
        Response resp = AuthClient.register(USER_1,PASSWORD_1);
        Assert.assertEquals(200, resp.code());
    }

    @Test
    public void registerEmptyUsername() throws IOException {
        Response resp = AuthClient.register("",PASSWORD_1);
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void alreadyRegistered() throws IOException {
        AuthClient.register(USER_2, PASSWORD_2);
        Response resp = AuthClient.register(USER_2,PASSWORD_2);
        Assert.assertEquals(400, resp.code());
    }

    @Test
    public void notRegisteredLogin() throws IOException {
        Response resp = AuthClient.login(USER_3,PASSWORD_3);
        Assert.assertEquals(400, resp.code());
    }

    @Test
    public void login() throws IOException {
        Response resp = AuthClient.login(USER_2,PASSWORD_2);
        Assert.assertEquals(200, resp.code());
    }

    /*@Test
    public void alreadyLogined() throws IOException {
        String token = AuthClient.login(USER_1, PASSWORD_1).body().toString();
        Response resp = AuthClient.login(USER_1,PASSWORD_1);
        Assert.assertEquals(token, resp.body().toString());
    }*/

    @Test
    public void wrongPasswordLogin() throws IOException {
        AuthClient.register(USER_3,PASSWORD_3);
        Response resp = AuthClient.login(USER_2,PASSWORD_1);
        Assert.assertEquals(400, resp.code());
    }

    /*@Test
    public void logout() throws IOException {
        AuthClient.register(USER_4, PASSWORD_4);
        String token = AuthClient.login(USER_4,PASSWORD_4).body().toString();
        Response resp = AuthClient.logout(token);
        Assert.assertEquals(200, resp.code());
    }*/

    @Test
    public void registerCheckFalse() throws IOException, AuthException{
        AuthClient.register(USER_4,PASSWORD_4);
        Assert.assertEquals("False", AuthService.registerCheck(USER_4));
    }
}
