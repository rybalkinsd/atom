package ru.atom.rk1.server;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;


public class Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    public static Response register(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    public static Response badRegister(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    public static Response emptyRegister() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    public static Response login(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    public static Response badLogin1(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + name + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    public static Response badLogin2(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name + "&pass=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    public static Response emptyLogin() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .addHeader(HttpHeaders.AUTHORIZATION, token)
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .build();

        return client.newCall(request).execute();
    }

    public static Response emptyLogout() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .addHeader(HttpHeaders.AUTHORIZATION, "")
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .build();

        return client.newCall(request).execute();
    }

    public static Response users() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }
}