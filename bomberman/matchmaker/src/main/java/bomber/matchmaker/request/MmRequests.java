package bomber.matchmaker.request;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;

import java.io.IOException;


public class MmRequests {

    static final OkHttpClient client = new OkHttpClient();
    public static final String HTTP_PROTOCOL = "http://";
    public static final String WEBS_PROTOCOL = "ws://";
    public static final String HOST = "localhost";
    public static final String PORT_GS = ":8090";
    public static final String PORT_MM = ":8080";


    public static Response create(final int playerCounter) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount=" + playerCounter))
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/create")
                .build();
        return client.newCall(request).execute();
    }

    public static Response start(final int gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId=" + gameId))
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/start")
                .build();
        return client.newCall(request).execute();
    }

    public static Response checkStatus() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(HTTP_PROTOCOL + HOST + PORT_GS + "/game/checkstatus")
                .addHeader("host", HOST + PORT_GS)
                .build();
        return client.newCall(request).execute();
    }


}
