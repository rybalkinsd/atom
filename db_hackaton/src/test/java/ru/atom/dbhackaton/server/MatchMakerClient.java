package ru.atom.dbhackaton.server;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * Created by salvador on 19.04.17.
 */
public class MatchMakerClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    public static Response join(String body) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/mm/join")
                .addHeader("Joining", body)
                .build();

        return client.newCall(request).execute();
    }

    public static Response finish() throws IOException {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/mm/finish")
                .build();

        return client.newCall(request).execute();
    }

}
