package ru.atom.chat;

import okhttp3.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatClientTest {
    private  final Logger log = LoggerFactory.getLogger(ChatClientTest.class);

    @Autowired
    private  ChatController controller;


    private  String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private  String MY_MESSAGE_TO_CHAT = "KILL_ME_SOMEONE";
    private  String MY_AWESOME_PASSWORD = "PLEASE!";

    @Autowired
    private ChatClient client;

    @Test
    public void signUp() throws IOException{
        Response response = client.signUp(MY_NAME_IN_CHAT,MY_AWESOME_PASSWORD);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("User with this name already exists!"));
    }

    @Test
    public void login() throws IOException {
        Response response = client.login(MY_NAME_IN_CHAT,MY_AWESOME_PASSWORD);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = client.viewChat();
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void viewOnline() throws IOException {
        Response response = client.viewOnline();
        log.info("[" + response + "]");
        log.info(response.body().toString());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws IOException {
        Response response = client.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        log.info("[" + response + "]");
        log.info(response.body().string());
        Assert.assertEquals(200, response.code());
    }
}
