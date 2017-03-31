package ru.atom;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.atom.server.User;
import ru.atom.server.UserContainer;
import ru.atom.server.AuthServer;



import java.io.IOException;

/**
 * Created by konstantin on 25.03.17.
 */
public class AuthServiceTest {
    
    @Before
    public void setUp() throws Exception {
        AuthServer.serverStart();
    }

    @Test
    public void registerTest() throws IOException {
        User user = new User("Mark", "Mark");
        User user1 = new User("Mark1", "Mark1");
        AuthClient.register(user.getName(), user.getPassword());
        AuthClient.register(user1.getName(), user1.getPassword());
        Assert.assertTrue(UserContainer.getRegisteredUsers().contains(user));
        Assert.assertTrue(UserContainer.getRegisteredUsers().contains(user1));
    }

    @Test
    public void failedRegisterTest() throws IOException {
        Response response = AuthClient.register("Mark2", "QMark");
        Response response1 = AuthClient.register("Mark2", "QMark1");
        Assert.assertNotEquals(200, response1.code());
    }

    @Test
    public void trueLoginTest() throws IOException {
        String user = "Max";
        String pass = "QMax";
        AuthClient.register(user, pass);
        Response response = AuthClient.login(user, pass);
        Assert.assertEquals(200,response.code());
    }

    @Test
    public void failedLoginTest() throws IOException {
        Response response = AuthClient.login("Lol", "QLol");
        Assert.assertNotEquals(200, response.code());
    }

    @Test
    public void logoutTest() throws IOException {
        String user = "Alex";
        String pass = "QAlex";
        AuthClient.register(user, pass);
        Response response = AuthClient.login(user, pass);
        String tocken = response.body().string();
        Response response1 = AuthClient.logout(tocken);
        Assert.assertEquals(200, response1.code());

    }

    @Test
    public void onlineTest() throws IOException {
        String user = "Max";
        String pass = "QMax";
        AuthClient.register(user, pass);
        AuthClient.login(user, pass);
        String user1 = "Max1";
        String pass1 = "QMax1";
        AuthClient.register(user1, pass1);
        AuthClient.login(user1, pass1);
        Response response = AuthClient.online();
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void usersTest() throws IOException {
        Response response = AuthClient.users();
        Assert.assertEquals(200, response.code());
    }

    @After
    public void setDown() throws Exception {
        AuthServer.serverStop();
    }
}
