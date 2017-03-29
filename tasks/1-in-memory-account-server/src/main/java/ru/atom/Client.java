package ru.atom;

/**
 * Created by user on 28.03.2017.
 */
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;


public class Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    public static Response register(String login, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?login=" + login + "&password=" + password)
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }

    public static Response login(String login, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?login=" + login + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(long token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout?token=" + token)
                .build();

        return client.newCall(request).execute();
    }

    public static Response online() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }
}