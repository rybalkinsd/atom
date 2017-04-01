package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//import sun.util.resources.cldr.ms.CalendarData_ms_MY;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;

public class RK1Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    // GET host:port/chat/online
    public static Response viewOnline() throws IOException {
        Request request = new Request.Builder().get().url(PROTOCOL + HOST + PORT + "/data/users")
            .build();

        return client.newCall(request).execute();
    }

    // POST host:port/chat/login?name=my_name
    public static Response login(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder().post(RequestBody.create(mediaType, ""))
            .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + user + "&password=" + password)
            .build();

        return client.newCall(request).execute();
    }

    /*
     * public static Response logout() throws IOException { Request request =
     * new Request.Builder() .get() .addHeader(HttpHeaders.AUTHORIZATION,
     * "12345678910") .url(PROTOCOL + HOST + PORT + "/auth/logout") .build();
     * 
     * return client.newCall(request).execute(); }
     */

    public static Response register(String user, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder().post(RequestBody.create(mediaType, ""))
            .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + user + "&password=" + password)
            .build();

        return client.newCall(request).execute();
    }
}