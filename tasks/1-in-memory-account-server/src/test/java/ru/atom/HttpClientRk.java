package ru.atom;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by pavel on 27.03.17.
 */
public class HttpClientRk {
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

    public static Response logout(String tocken) throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .addHeader("Authorization", tocken)
                .build();
        return client.newCall(request).execute();
    }

    public static Response users() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL
                        + HOST
                        + PORT
                        + "/data/users")
                .build();
        return client.newCall(request).execute();
    }
}
