package ru.atom.client;

/**
 * Created by BBPax on 19.04.17.
 */
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import ru.atom.dbhackaton.resource.User;

import java.io.IOException;

/**
 * Created by BBPax on 24.03.17.
 */
public class AuthClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    //POST host:port/auth/register?user=userName&password=userPass
    public static Response registration(User user) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + user.getLogin()
                        + "&password=" + user.getPassword()))
                .url(PROTOCOL + HOST + PORT + "/auth/register")
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login?user=userName&password=userPass
    public static Response login(User user) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + user.getLogin()
                        + "&password=" + user.getPassword()))
                .url(PROTOCOL + HOST + PORT + "/auth/login")
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        Request request = new Request.Builder().post(RequestBody.create(MediaType.parse(""),""))
                .addHeader("Authorization", "Bearer " + token)
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .build();
        return client.newCall(request).execute();
    }
}
