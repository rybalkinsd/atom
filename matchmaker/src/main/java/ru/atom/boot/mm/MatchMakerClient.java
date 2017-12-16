package ru.atom.boot.mm;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;


public class MatchMakerClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "192.168.99.100";
    private static final String PORTGS = ":8090"; // GameServer PORT
    private static final String PORTFE = ":8081"; //front-end PORT

    public static Response create(int count) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount=" + count))
                .url(PROTOCOL + HOST + PORTGS + "/game/create")
                .build();

        return client.newCall(request).execute();
    }

    public static Response start(long gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId=" + gameId))
                .url(PROTOCOL + HOST + PORTGS + "/game/start")
                .build();

        return client.newCall(request).execute();
    }

    public static Response toFrontEnd(long gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId=" + gameId))
                .url(PROTOCOL + HOST + PORTFE + "/")
                .build();

        return client.newCall(request).execute();
    }
}