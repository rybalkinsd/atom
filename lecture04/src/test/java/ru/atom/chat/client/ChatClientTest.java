package ru.atom.chat.client;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.chat.client.ChatClient;

import java.io.IOException;


public class ChatClientTest {
    private static String MY_NAME_IN_CHAT = "OBAMA_CHMO";
    private static String MY_MESSAGE_TO_CHAT = "ooooo vy is England";

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }


    @Test
    public void viewOnline() throws IOException {
        Response response = ChatClient.viewOnline();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws IOException {
        Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void logout() throws IOException {
        Response response = ChatClient.logout(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged out:("));


    }

}
