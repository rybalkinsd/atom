package ru.atom.rk01;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by dmbragin on 3/29/17.
 */
public class TestClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    private static final String URL_REGISTER = "/auth/register";
    private static final String URL_LOGIN = "/auth/login";
    private static final String URL_LOGOUT = "/auth/logout";
    private static final String URL_USERS = "/data/users";


    public static Response viewOnline(String token) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + URL_USERS)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return client.newCall(request).execute();
    }

    public static Response register(String name, String passwd) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("login", name);
        formBuilder.add("passwd", passwd);
        Request request = new Request.Builder()
                .url(PROTOCOL + HOST + PORT + URL_REGISTER)
                .post(formBuilder.build())
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        return client.newCall(request).execute();
    }

    public static Response login(String name, String passwd) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("login", name);
        formBuilder.add("passwd", passwd);
        Request request = new Request.Builder()
                .url(PROTOCOL + HOST + PORT + URL_LOGIN)
                .post(formBuilder.build())
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .url(PROTOCOL + HOST + PORT + URL_LOGOUT)
                .addHeader("Authorization", "Bearer " + token)
                .post(RequestBody.create(mediaType, ""))
                .build();

        return client.newCall(request).execute();
    }

}
