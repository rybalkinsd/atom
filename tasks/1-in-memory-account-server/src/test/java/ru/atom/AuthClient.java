package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Vlad on 29.03.2017.
 */
public class AuthClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    public static Response register(String user, String password) throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + user + "&password=" + password)
                .build();
        return client.newCall(request).execute();
    }

    public static Response login(String user, String password) throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + user + "&password=" + password)
                .build();
        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return client.newCall(request).execute();
    }

    public static Response users() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .build();
        return client.newCall(request).execute();
    }
}
