package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.atom.persons.User;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * Created by BBPax on 24.03.17.
 */
public class ServerClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    //POST host:port/auth/register?user=userName&password=userPass
    public static Response registration(User user) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + user.getUserName()
                        + "&password=" + user.getPassword())
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login?user=userName&password=userPass
    public static Response login(User user) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + user.getUserName()
                        + "&password=" + user.getPassword())
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String token) throws IOException {
        //MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder().post(RequestBody.create(MediaType.parse(""),""))
                //.post(RequestBody.create(mediaType, ""))
                .addHeader("Authorization", "Bearer " + token)
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .build();
        return client.newCall(request).execute();
    }

    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/data/users")
                .build();

        return client.newCall(request).execute();
    }
}
