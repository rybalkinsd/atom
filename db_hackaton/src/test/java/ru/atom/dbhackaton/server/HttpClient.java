package ru.atom.dbhackaton.server;

/**
 * Created by gammaker on 29.03.2017.
 */

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;


public class HttpClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String AUTH_PORT = ":8080";
    private static final String MM_PORT = ":8081";

    public static Response login(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + AUTH_PORT + "/auth/login?user=" + name + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    public static Response register(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + AUTH_PORT + "/auth/register?user=" + name + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(null, ""))
                .header("Authorization", "Bearer " + token)
                .url(PROTOCOL + HOST + AUTH_PORT + "/auth/logout")
                .build();

        return client.newCall(request).execute();
    }

    public static Response join(long token) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + MM_PORT + "/mm/join?token=" + token)
                .build();

        return client.newCall(request).execute();
    }

    public static Response finish(String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, json))
                .url(PROTOCOL + HOST + MM_PORT + "/mm/finish")
                .build();

        return client.newCall(request).execute();
    }
}
