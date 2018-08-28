package ru.atom.chat;

import java.io.IOException;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatClientTest {
    private static final Logger log = LoggerFactory.getLogger(ChatClientTest.class);

    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE_TO_CHAT = "KILL_ME_SOMEONE";

    @LocalServerPort
    private int port;

    @Before
    public void before() {
        ChatClient.init(port);
    }

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void viewOnline() throws IOException {
        Response response = ChatClient.viewOnline();
        log.info("[" + response + "]");
        log.info(response.body().toString());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT + "_t");
        Response response = ChatClient.say(MY_NAME_IN_CHAT + "_t", MY_MESSAGE_TO_CHAT);
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }
}
