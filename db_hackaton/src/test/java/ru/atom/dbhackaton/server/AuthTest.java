package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.server.service.AuthException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by allen on 18.04.2017.
 */
public class AuthTest {

    private static final String USER_1 = "Sasha";
    private static final String PASSWORD_1 = "Sashapass";
    Random random = new Random();

    @Before
    public void setup() throws Exception {
        AuthServer.authStart();
    }

    @After
    public void stop() throws Exception {
        AuthServer.authStop();
    }

    @Test
    public void register() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.register(user, pass);
        Assert.assertEquals(200, resp.code());
    }

    @Test
    public void registerLengthUser() throws IOException {
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.register("", pass);
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void registerLengthPassword() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.register(user, "");
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void loginLengthPassword() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.login(user, "");
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void loginLengthUser() throws IOException {
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.login("", pass);
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void registerCheckLengthUser() throws IOException {
        Response resp = AuthClient.registerCheck("");
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void registerEmptyUsername() throws IOException {
        Response resp = AuthClient.register("",PASSWORD_1);
        Assert.assertEquals(411, resp.code());
    }

    @Test
    public void alreadyRegistered() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        AuthClient.register(user, pass);
        Response resp = AuthClient.register(user, pass);
        Assert.assertEquals(400, resp.code());
    }

    @Test
    public void notRegisteredLogin() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        Response resp = AuthClient.login(user, pass);
        Assert.assertEquals(400, resp.code());
    }

    @Test
    public void login() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        AuthClient.register(user, pass);
        Response resp = AuthClient.login(user, pass);
        Assert.assertEquals(200, resp.code());
    }

    @Test
    public void alreadyLogined() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        String token = AuthClient.login(user, pass).body().string();
        Response resp = AuthClient.login(user, pass);
        Assert.assertEquals(token, resp.body().string());
    }

    @Test
    public void wrongPasswordLogin() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        AuthClient.register(user,pass);
        Response resp = AuthClient.login(user,PASSWORD_1);
        Assert.assertEquals(400, resp.code());
    }

    @Test
    public void logout() throws IOException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        AuthClient.register(user, pass);
        String token = AuthClient.login(user,pass).body().string();
        Response resp = AuthClient.logout(token);
        Assert.assertEquals(200, resp.code());
    }

    @Test
    public void registerCheckFalse() throws IOException, AuthException {
        String user = USER_1 + new Integer(random.nextInt(999)).toString();
        String pass = PASSWORD_1 + new Integer(random.nextInt(999)).toString();
        AuthClient.register(user, pass);
        Assert.assertEquals("False", AuthClient.registerCheck(user).body().string());
    }
}
