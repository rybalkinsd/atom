package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;


/**
 * Created by pavel on 17.04.17.
 */
@Ignore
public class MatchMakerResourceTest {

    @Before
    public void setUp() throws Exception {
        AuthServer.startServer();
        MatchMakerServer.startServer();
    }

    @Test
    public void joinTest() throws  Exception {
        String user = "123";
        String password = "123";
        AuthClient.register(user, password);
        Response response = AuthClient.login(user, password);
        String token = response.body().string();
        Response response1 = MatchMakerClient.join("name=\\{" + user + "}" + "token={" + token + "}");
        String bodyResponse = response1.body().string();
        System.out.println(bodyResponse);

        Assert.assertTrue(response1.code() == 200);
    }

    @Test
    public void finishTest() throws  Exception {

        Response response = MatchMakerClient.finish();
        Assert.assertTrue(response.code() == 200);
    }

    @After
    public void setDown() throws Exception {
        MatchMakerServer.stopServer();
        AuthServer.stopServer();
    }
}
