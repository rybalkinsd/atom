package ru.atom.dbhackaton;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.dbhackaton.server.MainServerWithAuthAndMm;

import java.io.IOException;

import static ru.atom.WorkWithProperties.getStrBundle;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class AuthTest {
    private String token;

    @Before
    public void serverInitializer() throws Exception {
        MainServerWithAuthAndMm.startUp();
    }

    @Test
    public void register() throws IOException {
        String user = "Test";
        String password = "Qwerty";
        Response response = AuthClient.register(user, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        Assert.assertTrue(response.code() == 200
                || body.equals(getStrBundle().getString("already.registered")));
    }

    @Test
    public void login() throws IOException {
        String user = "Test";
        String password = "Qwerty";
        Response response = AuthClient.login(user, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        token = body;
        System.out.println(body);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void logout() throws IOException {
        register();
        login();
        Response response = AuthClient.logout(token);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @After
    public void stopServer() throws Exception {
        MainServerWithAuthAndMm.shutdown();
    }
}
