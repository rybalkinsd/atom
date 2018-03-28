package ru.atom.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import okhttp3.*;
import org.junit.*;
import org.junit.rules.Timeout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

@SpringBootApplication
public class ServerTest {
    private static final Logger log = LoggerFactory.getLogger(ChatClientTest.class);

    private static final OkHttpClient client = new OkHttpClient();

    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE = "LUL";

    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    private static boolean serverIsUp = false;

    private void buildServer() throws IOException{
        try{
            chat();
            log.info("Server is already up");
        } catch (ConnectException e) {
            SpringApplication.run(ChatApplication.class);
            serverIsUp = true;
            log.info("Started server");
        }
    }


    @Test
    public void loginTest() throws IOException {
        log.info("loginTest()");
        buildServer();
        //Ok login
        Response response = login(MY_NAME_IN_CHAT,"123");
        Assert.assertEquals(response.code(),200);
        //already logged
        response = login(MY_NAME_IN_CHAT,"123");
        Assert.assertEquals(response.code(),400);
        //
        logout(MY_NAME_IN_CHAT);
    }

    @Test
    public void sayTest() throws IOException {
        log.info("sayTest()");
        buildServer();
        login(MY_NAME_IN_CHAT,"123");
        Response response = say(MY_NAME_IN_CHAT,"123",MY_MESSAGE);
        Assert.assertEquals(response.code(),200);
        response = chat();
        Assert.assertTrue(response.body().string().contains(MY_MESSAGE));
        logout(MY_NAME_IN_CHAT);
    }

    @Test
    public void historyTest() throws IOException{
        log.info("historyTest()");
        buildServer();
        login(MY_NAME_IN_CHAT,"123");
        say(MY_NAME_IN_CHAT,"123",MY_MESSAGE + " for history");


        boolean historyContainsLogin= false;
        boolean historyContainsMsg = false;
        try(BufferedReader br = new BufferedReader(new FileReader(DatabaseHandler.class.getClassLoader()
                .getResource("History.txt").getPath()))) {
            String s;
            while((s = br.readLine()) != null) {
                if (s.contains(MY_NAME_IN_CHAT) && (s.contains("logged in")))
                    historyContainsLogin = true;
                if (s.contains(MY_NAME_IN_CHAT) && (s.contains(MY_MESSAGE + " for history")))
                    historyContainsMsg = true;
            }
        } catch(NullPointerException e) {
            log.warn("Unable to get resource 'History.txt'");
        }
        logout(MY_NAME_IN_CHAT);
        Assert.assertEquals(historyContainsLogin,true);
        Assert.assertEquals(historyContainsMsg,true);
    }



    @Test
    public void antispamTest() throws IOException {
        log.info("antipamTest()");
        buildServer();
        login(MY_NAME_IN_CHAT,"123");
        Response response;
        for(int i = 0; i < 10; i++) {
            response = say(MY_NAME_IN_CHAT,"123",MY_MESSAGE + i);
            log.info("I am spamming");
            if (i == 0)
                Assert.assertEquals(response.code(),200);
            else
                Assert.assertNotEquals(response.code(),200);
        }
        logout(MY_NAME_IN_CHAT);
    }


    @Test
    public void injectTest() throws IOException {
        log.info("injectTest");
        buildServer();
        login(MY_NAME_IN_CHAT,"123");
        String inject_text = "<script> function circle() { while(true) {} } circle() </script>";
        Response response = say(MY_NAME_IN_CHAT,"123",inject_text);
        Assert.assertEquals(200, response.code());
        logout(MY_NAME_IN_CHAT);
    }

    private static Response login(String name,String password ) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/chat/login")
                .build();
        return client.newCall(request).execute();
    }

    private static Response logout(String name) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name))
                .url(PROTOCOL + HOST + PORT + "/chat/logout")
                .build();
        return client.newCall(request).execute();
    }

    private static Response say(String name,String password,String msg) throws IOException{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request2 = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password + "&msg=" + msg))
                .url(PROTOCOL + HOST + PORT + "/chat/say")
                .build();
        return client.newCall(request2).execute();
    }

    private static Response chat() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/chat")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

}
