package ru.atom.http;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ChatClientTest {
  private String MY_NAME_IN_CHAT = "I_AM_STUPID";
  private String MY_MESSAGE_TO_CHAT = "SOMEONE_KILL_ME";
  @Test
  public void viewOnline() throws IOException {
    Response response = ChatClient.viewOnline();
    System.out.println("[" + response + "]");
    System.out.println(response.body().string());
    Assert.assertEquals(200, response.code());
  }

  @Test
  public void login() throws IOException {
    Response response = ChatClient.login(MY_NAME_IN_CHAT);
    System.out.println("[" + response + "]");
    System.out.println(response.body().string());
    Assert.assertTrue(response.code() == 200 || response.body().string().equals("Already logined"));
  }

  @Test
  public void viewChat() throws IOException {
    Response response = ChatClient.viewChat();
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
}
