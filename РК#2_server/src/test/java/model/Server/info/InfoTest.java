package model.Server.info;

import com.squareup.okhttp.*;
import model.server.auth.Functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by artem on 08.11.16.
 */
public class InfoTest {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();
    static {
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setWriteTimeout(1, TimeUnit.MINUTES);
    }

    public Collection<String> GetUserTest() {
        try {
        String requestUrl = SERVICE_URL + "/data/users";
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();


            Response response = client.newCall(request).execute();
            String personsJson = response.body().string();
            return null;

        } catch (Exception e) {
            System.out.println("Something went wrong in getBatch." + e);
            return null;
        }
    }
}
