package ru.atom.chat;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@Ignore
public class ChatClientTest {
    private static final Logger log = LoggerFactory.getLogger(ChatClientTest.class);
    private static NameGen gen = new NameGen();

    private static String MY_NAME_IN_CHAT = "I_AM_THE_ONE_WHO_KNOK";
    private static String MY_MESSAGE_TO_CHAT = "KILL_ME_SOMEONE";
    private static String MY_PASSWORD = "1234";



    @Test
    public void loginFailIfWeDontRegister() throws IOException {
        Response response = ChatClient.login(gen.generateName(),MY_PASSWORD);
        String body = response.body().string();

        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("No such user registered:(",body);
    }

    @Test
    public void loginFailIfWeAlreadyLoginedIn() throws IOException {
        String name = gen.generateName();
        ChatClient.register(name,MY_PASSWORD,MY_PASSWORD);

        Response response = ChatClient.login(name,MY_PASSWORD);
        String body = response.body().string();

        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("Already logged in:(",body);
    }

    @Test
    public void loginOkIfWeRegisteredAndLogouted() throws IOException {
        String name = gen.generateName();
        ChatClient.register(name,MY_PASSWORD,MY_PASSWORD);
        ChatClient.logout(name);

        Response response = ChatClient.login(name,MY_PASSWORD);
        String body = response.body().string();

        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(200,response.code());
    }

    @Test
    public void register() throws IOException {
        String name = MY_NAME_IN_CHAT + gen.getGenId();
        Response response = ChatClient.register("", MY_PASSWORD, MY_PASSWORD);
        String body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("Too short name, sorry :(",body);

        // more then 30 letters
        response = ChatClient.register(name + name , MY_PASSWORD, MY_PASSWORD);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("Too long name, sorry :(",body);

        response = ChatClient.register(name, MY_PASSWORD, MY_PASSWORD + "2");
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("passwords does not equal:(",body);

        response = ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(200,response.code());

        response = ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400,response.code());
        Assert.assertEquals("Already registered :(",body);
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat(MY_NAME_IN_CHAT);
        //log.info("[" + response + "]");
        //log.info(response.body().string());
        Assert.assertEquals(200, response.code());

        // if we dont loggined also ok
        response = ChatClient.viewChat(gen.generateName());
        //log.info("[" + response + "]");
        //log.info(response.body().string());
        Assert.assertEquals(200, response.code());


    }

    @Test
    public void viewOnline() throws IOException {
        Response response = ChatClient.viewOnline();
        //log.info("[" + response + "]");
        //log.info(response.body().toString());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void say() throws Exception {
        String name = gen.generateName();
        Response response = ChatClient.say(name, MY_PASSWORD, MY_MESSAGE_TO_CHAT);
        String body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("No such user:(", body);


        name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        response = ChatClient.say(name, MY_PASSWORD + "2", MY_MESSAGE_TO_CHAT);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("Wrong password", body);

        name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        ChatClient.logout(name);
        response = ChatClient.say(name, MY_PASSWORD, MY_MESSAGE_TO_CHAT);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("User is logged out:(", body);

        name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        response = ChatClient.say(name, MY_PASSWORD, MY_MESSAGE_TO_CHAT);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("Spam", body);

        name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        Thread.sleep(3000);
        response = ChatClient.say(name, MY_PASSWORD, MY_MESSAGE_TO_CHAT);
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void logout() throws IOException {
        String name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        Response response = ChatClient.logout(name);
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(200, response.code());

        name = gen.generateName();
        response = ChatClient.logout(name);
        String body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("No such user:(", body);

        name = gen.generateName();
        ChatClient.register(name, MY_PASSWORD, MY_PASSWORD);
        response = ChatClient.logout(name);
        response = ChatClient.logout(name);
        body = response.body().string();
        //log.info("[" + response + "]");
        //log.info(body);
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("Already logged out", body);
    }
}
