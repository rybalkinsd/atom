package ru.atom.dbhackaton;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static ru.atom.WorkWithProperties.getProperties;

public class AuthClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = getProperties().getProperty("protocol");
    private static final String HOST = getProperties().getProperty("host");
    private static final String PORT = ":" + getProperties().getProperty("port");

    //POST host:port/auth/register?user=my_name&password=my_password
    public static Response register(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT +
                        "/auth/register?user=" + user +
                        "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login?user=my_name&password=my_password
    public static Response login(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT +
                        "/auth/login?user=" + user +
                        "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    //POST host:port/auth/logout
    public static Response logout(String tocken) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                        .post(RequestBody.create(mediaType, ""))
                        .url(PROTOCOL + HOST + PORT + "/auth/logout")
                        .addHeader("Authorization", "Bearer " + tocken)
                        .build();
        return client.newCall(request).execute();
    }
}
