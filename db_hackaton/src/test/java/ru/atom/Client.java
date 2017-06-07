package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.util.resources.cldr.ms.CalendarData_ms_MY;

import java.io.IOException;

/**
 * Created by ilnur on 23.04.17.
 */
public class Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    //POST host:port/chat/login?name=my_name
    public static Response register(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + name + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    public static Response login(String name, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + name + "&password=" + password)
                .build();

        return client.newCall(request).execute();
    }

    public static Response logout(String name) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout?user=" + name)
                .build();

        return client.newCall(request).execute();
    }

}