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

import static ru.atom.WorkWithProperties.getProperties;

/**
 * Created by BBPax on 24.03.17.
 */
public class MatchMakerClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = getProperties().getProperty("protocol");
    private static final String HOST = getProperties().getProperty("host");
    private static final String PORT = ":" + getProperties().getProperty("port");

    //POST host:port/mm/join
    public static Response join(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "token=" + token))
                .url(PROTOCOL + HOST + PORT + "/mm/join")
                .build();
        return client.newCall(request).execute();
    }

    //POST host:port/mm/finish
    public static Response finish(int gameId, User user1, User user2) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,
                        "{id='" + gameId + "', 'result':{'" + user1.getLogin() + "'=10, '"
                                + user2.getLogin() + "'=15}"))
                .url(PROTOCOL + HOST + PORT + "/mm/finish")
                .build();

        return client.newCall(request).execute();
    }
}
