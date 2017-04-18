package ru.atom.dbhackaton.server;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class MmClient {
    private static final Logger logger = LogManager.getLogger(MmClient.class);

    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8090";

    public static Response join(String userName, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/mm/join?name=" + userName + "&token=" + token)
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

    public static Response finish(String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(PROTOCOL + HOST + PORT + "/mm/finish")
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

}
