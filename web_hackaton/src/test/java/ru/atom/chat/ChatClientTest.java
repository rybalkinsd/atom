package ru.atom.chat;

import okhttp3.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore
public class ChatClientTest {
    private static final Logger log = LogManager.getLogger(ChatClient.class);

    private static String MY_NAME_IN_CHAT = "sasha";
    private static String MY_MESSAGE_TO_CHAT = "Всем привет в этом чатике!";

    @Test
    public void viewOnline() throws IOException {
        Response response = ChatClient.viewOnline();
        log.info("[" + response + "]");
        log.info(response.body());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in"));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws IOException {
        Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }
}
