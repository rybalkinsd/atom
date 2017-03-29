package ru.atom;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.persons.Token;
import ru.atom.persons.TokenStorage;
import ru.atom.persons.User;
import ru.atom.server.HttpServer;

/**
 * Created by BBPax on 23.03.17.
 */

public class AuthorizationTest {
    private User myUser = new User("Vladislav", "very11Complicated11Pass");
    private User myFriend = new User("Friend", "qwerty");
    private User exFriend = new User("asshole", "qwerty1245");
    private User newTestUser = new User("JustMen", "with pass");

    @Before
    public void registrationUsers() throws Exception {
        HttpServer.startup();
        ServerClient.registration(myUser);
        ServerClient.registration(myFriend);
        ServerClient.registration(exFriend);
    }

    @Test
    public void registrationTest() throws Exception {
        Response anotherTry = ServerClient.registration(myUser);
        Assert.assertEquals(400, anotherTry.code());

        anotherTry = ServerClient.registration(new User("Vladislav", "shouldn't work"));
        Assert.assertEquals(400, anotherTry.code());

        anotherTry = ServerClient.registration(new User("MeWithTheSamePass", "very11Complicated11Pass"));
        Assert.assertEquals(200, anotherTry.code());

        anotherTry = ServerClient.registration(newTestUser);
        Assert.assertEquals(200, anotherTry.code());
    }

    @Test
    public void loginTest() throws Exception {
        Response response = ServerClient.login(myUser);
        Assert.assertEquals(200, response.code());

        response = ServerClient.login(myUser);
        Assert.assertEquals(200, response.code());

        response = ServerClient.login(new User("MeWithXaTheSamePass", "very11Complicated11Pass"));
        Assert.assertEquals(400, response.code());

        Response response1 = ServerClient.login(new User("Vladislav", "notmyPass"));
        String message = response1.body().string();
        Assert.assertEquals(400, response1.code());
        Assert.assertEquals("Wrong Name or Password", message);
    }

    @Test
    public void logoutTest() throws Exception {
        String token = ServerClient.login(myUser).body().string();
        ServerClient.login(myFriend);
        System.out.println(token);
        Response resp = ServerClient.logout(token);
        Assert.assertEquals(200, resp.code());
        Assert.assertFalse(TokenStorage.hasToken(new Token(token.substring(7))));

        resp = ServerClient.logout(token);
        Assert.assertEquals(401, resp.code());
    }

    @Test
    public void onlineJsonTest() throws Exception {
        ServerClient.login(myUser);
        String json = ServerClient.viewOnline().body().string();
        System.out.println(json);
        Assert.assertEquals("{\"users\":[{\"userName\":\"Vladislav\"}]}", json);
    }

    @After
    public void turnOff() throws Exception {
        HttpServer.shutdown();
    }
}
