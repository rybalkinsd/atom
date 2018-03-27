package ru.atom.chat;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ChatClient {


    private OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8090";

    public Response login(String name,String passw) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&passw=" + passw))
                .url(PROTOCOL + HOST + PORT + "/chat/login")
                .build();
        return client.newCall(request).execute();
    }

    public Response viewChat() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/chat")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public Response viewOnline() throws IOException {
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/chat/online")
                .build();
        return client.newCall(request).execute();
    }

    public Response say(String name, String msg) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&msg=" + msg))
                .url(PROTOCOL + HOST + PORT + "/chat/say")
                .build();
        return client.newCall(request).execute();
    }

    public Response logout(String name) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name))
                .url(PROTOCOL + HOST + PORT + "/chat/logout")
                .build();
        return client.newCall(request).execute();
    }

    public Response signUp(String name,String passw) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&passw=" + passw))
                .url(PROTOCOL + HOST + PORT + "/chat/signUp")
                .build();
        return client.newCall(request).execute();
    }
}