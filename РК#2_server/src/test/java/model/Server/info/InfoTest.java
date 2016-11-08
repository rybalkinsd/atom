package model.Server.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.*;
import model.data.LeaderBoardRecord;
import model.data.User;
import model.server.auth.Authentication;
import model.server.auth.Functional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by artem on 08.11.16.
 */
public class InfoTest {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;
    @NotNull
    private static Gson gson;
    private static final Logger log = LogManager.getLogger(InfoTest.class);

    private static final OkHttpClient client = new OkHttpClient();
    static {
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setWriteTimeout(1, TimeUnit.MINUTES);
        gson = new GsonBuilder().create();
    }

    @Test
    public void GetUserTest() {
        try {
        String requestUrl = SERVICE_URL + "/data/users";
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();


            Response response = client.newCall(request).execute();
            String json = response.body().string();
            User [] reslb = gson.fromJson(json,User[].class);
            assertEquals(1,reslb.length);
            for (int i =0; i!=reslb.length; i++){
                assertEquals(reslb[i].getId(),Functional.getAssertUser("test",null).getId());
            }


            ;


        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
    }
}
