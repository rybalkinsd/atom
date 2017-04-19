package ru.atom.mm;

import okhttp3.Response;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import ru.atom.client.AuthClient;
import ru.atom.client.MatchMakerClient;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.server.AuthResource;
import ru.atom.dbhackaton.server.AuthServer;
import ru.atom.mm.server.MatchMakerServer;

import java.util.Random;

/**
 * Created by BBPax on 19.04.17.
 */

@Ignore
public class MatchMakerTest {
    private User user1 = new User().setLogin("user1").setPassword("password1");
    private User user2 = new User().setLogin("user2").setPassword("password2");

    @Before
    public void startUp() throws Exception {
        MatchMakerServer.startUp();
        AuthServer.startUp();
    }

    @Test
    public void joinTest() throws Exception {
        // TODO: 19.04.17   падает второй сервер
        AuthClient.registration(user1);
        String token1 = AuthClient.login(user1).body().string();
        System.out.println(token1);
        Response resp = MatchMakerClient.join(token1);
        Assert.assertEquals(200, resp.code());
        Assert.assertEquals("localhost:8081/gs/" + user1.getLogin(), resp.body().string());
        resp = MatchMakerClient.join("-8236847623");
        Assert.assertEquals(400, resp.code());

    }

    @Test
    public void finishTest() throws Exception {
        AuthClient.registration(user1);
        AuthClient.registration(user2);
        Response resp = MatchMakerClient.finish(new Random().nextInt(99), user1, user2);
        Assert.assertEquals(200, resp.code());
    }

    @After
    public void shutDown() throws Exception {
        MatchMakerServer.shutdown();
        AuthServer.shutdown();
    }

}