package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Response;

import java.io.IOException;

/**
 * Created by mkai on 3/26/17.
 */
public class AuthClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static String token;

    public static void setToken(String tokenValue) {
        token = tokenValue;
    }

    //POST host:port/auth/register?user={}&password={}
    public static Response register(String userName, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + userName + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login?user={}&password={}
    public static Response login(String userName, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + userName + "&password=" + password)
                .build();
        return client.newCall(request).execute();
    }

    //POST host:port/auth/logout
    public static Response logout() throws IOException {

        return null;
    }

    //GET host:port/data/users
    public static Response getData() throws IOException {

        return null;
    }

}
