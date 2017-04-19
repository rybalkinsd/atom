package ru.atom.dbhackaton.server;


import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MatchMakerTest {
    private static final Logger logger = LogManager.getLogger(MatchMakerTest.class);

    @Before
    public void init() throws IOException {
        try {
            MatchMakerServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void stop() throws Exception {
        MatchMakerServer.stop();
    }

    @Test
    public void join() throws IOException, InterruptedException {
        for (int j = 0; j < 4; j++) {
            Response response = MmClient.join(String.valueOf(j));
            String body = response.body().string();
            Assert.assertTrue(200 == response.code());
            Assert.assertTrue("Please, wait :)".equals(body));
        }
        Thread.sleep(100);
        String url = null;
        for (int i = 0; i < 4; i++) {
            Response response = MmClient.join(String.valueOf(i));
            Assert.assertTrue(200 == response.code());
            String body = response.body().string();
            if (url == null) {
                url = body;
            } else {
                Assert.assertTrue(url.equals(body));
            }
        }

    }

    @Test
    public void finishGood() throws IOException {
        String json = "{id='94', 'result':{'12345':10, 'luba':15, 'russiancold':10, '123':15}}";
        Response response = MmClient.finish(json);
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void finishBad() throws IOException {
        String json = "{id='0', 'result':{'12345':10, 'luba':15, 'russiancold':10, '123':15}}";
        Response response = MmClient.finish(json);
        Assert.assertTrue(200 != response.code());
    }
}
