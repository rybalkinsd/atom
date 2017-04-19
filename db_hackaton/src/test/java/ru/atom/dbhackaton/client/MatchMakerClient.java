package ru.atom.dbhackaton.client;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by ilysk on 18.04.17.
 */
public class MatchMakerClient {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8282";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();

    public static Response join(String user, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "user=" + user
                        + "&token=" + token))
                .url(SERVICE_URL + "/mm/join")
                .build();

        return client.newCall(request).execute();
    }

    public static Response finish(String gameResultJson) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,
                        "gameresult=" + gameResultJson))
                .url(SERVICE_URL + "/mm/finish")
                .build();

        return client.newCall(request).execute();
    }
}
