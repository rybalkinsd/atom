package ru.atom.mm;

import com.sun.org.apache.regexp.internal.RE;
import okhttp3.Response;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import ru.atom.client.AuthClient;
import ru.atom.client.MatchMakerClient;
import ru.atom.client.TestService;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.server.AuthResource;
import ru.atom.dbhackaton.server.AuthServer;
import ru.atom.mm.server.MatchMakerServer;
/**
 * Created by BBPax on 19.04.17.
 */

@Ignore
public class MatchMakerTest {

    private TestService ts = new TestService();
    private AuthResource ar = new AuthResource();
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @Before
    public void startUp() throws Exception {
        MatchMakerServer.startUp();
        user1 = ts.getUser("user1");
        user2 = ts.getUser("user2");
        user3 = ts.getUser("user3");
        user4 = ts.getUser("user4");

    }

    @Test
    public void joinTest() throws Exception {
        // TODO: 19.04.17   нихрена не работает
        AuthServer.startUp();

        System.out.println("id: " + user1.getId() + " login: " +
                user1.getLogin() + " password: " + user1.getPassword());

        Response response = AuthClient.login(user1);

        System.out.println(response.body().string());

        Assert.assertEquals(200, response.code());
        Token token1 = ts.getToken(Long.getLong(response.body().string()));
        Response joinResponse = MatchMakerClient.join(token1);
        Assert.assertEquals(200, joinResponse.code());
        Assert.assertEquals("localhost:8081/gs/" + user1.getLogin(), joinResponse.body().string());

        joinResponse = MatchMakerClient.join(token1.setToken(-1L));
        Assert.assertEquals(400, joinResponse.code());
        Assert.assertEquals("invalid user", joinResponse.body().string());

        AuthServer.shutdown();
    }

    @Test
    public void finishTest() throws Exception {

    }

    @After
    public void shutDown() throws Exception {
        MatchMakerServer.shutdown();
    }

}