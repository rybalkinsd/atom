package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//import sun.util.resources.cldr.ms.CalendarData_ms_MY;

import java.io.IOException;

public class RK1Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    // GET host:port/chat/online
    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder().get().url(PROTOCOL + HOST + PORT + "/chat/online")
            .build();

        return client.newCall(request).execute();
    }

    // POST host:port/chat/login?name=my_name
    public static Response login(String name, String pass) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder().post(RequestBody.create(mediaType, ""))
            .url(PROTOCOL + HOST + PORT + "/chat/login?name=" + name + "&pass=" + pass).build();

        return client.newCall(request).execute();
    }

    public static Response register(String name, String pass1, String pass2) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder().post(RequestBody.create(mediaType, "")).url(PROTOCOL
            + HOST + PORT + "/chat/register?name=" + name + "&pass1=" + pass1 + "&pass2=" + pass2)
            .build();

        return client.newCall(request).execute();
    }

    // POST host:port/chat/say?name=my_name
    // Body: "msg='my_message'"
    /*
     * public static Response say(String name, String msg) throws IOException {
     * MediaType mediaType =
     * MediaType.parse("application/x-www-form-urlencoded"); Request request =
     * new Request.Builder() .post(RequestBody.create(mediaType, ""))
     * .url(PROTOCOL + HOST + PORT + "/chat/say?name=" + name + "&msg=" + msg)
     * .build();
     * 
     * return client.newCall(request).execute(); }
     */

    // GET host:port/chat/chat
    public static Response viewChat() throws IOException {
        Request request = new Request.Builder().get().url(PROTOCOL + HOST + PORT + "/chat/chat")
            .build();

        return client.newCall(request).execute();
    }
}