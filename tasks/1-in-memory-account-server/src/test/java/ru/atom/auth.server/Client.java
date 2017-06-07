package ru.atom.auth.server;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.util.resources.cldr.ms.CalendarData_ms_MY;

import java.io.IOException;

public class Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8095";

    //POST host:port/auth/register
    //     body: name=&password=
    public static Response register(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password + ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login
    //     body: name=&password=
    public static Response login(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password + ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/logout
    //     header: Authorization: Bearer {token}
    public static Response logout(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return client.newCall(request).execute();
    }

    //GET host:port/data/users
    public static Response viewUsers(String token) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return client.newCall(request).execute();
    }

    /*
    //GET host:port/chat/online
    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/online")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/chat/login?name=my_name
    public static Response login(String name) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/chat/login?name=" + name)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/chat/say?name=my_name
    //Body: "msg='my_message'"
    public static Response say(String name, String msg) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "msg=" + msg + ""))
                .url(PROTOCOL + HOST + PORT + "/chat/say?name=" + name)
                .build();
        return client.newCall(request).execute();
    }

    //GET host:port/chat/chat
    public static Response viewChat() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/chat")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }*/
}