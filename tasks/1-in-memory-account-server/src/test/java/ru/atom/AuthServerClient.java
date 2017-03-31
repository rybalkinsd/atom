package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 * Created by ilysk on 28.03.17.
 */
public class AuthServerClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    //GET host:port/data/users
    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .addHeader("host", HOST + PORT)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/register
    //Body: user, password
    public static Response register(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + user
                        + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login
    //Body: user, password
    public static Response login(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + user
                        + "&password=" + password))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/logout
    //Header: Bearer [token]
    public static Response logout(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .build();

        return client.newCall(request).execute();
    }
}
