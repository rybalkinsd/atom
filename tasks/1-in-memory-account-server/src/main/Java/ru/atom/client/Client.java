package ru.atom.client;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Fella on 29.03.2017.
 */
public class Client {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST =  "localhost";
    private static final String PORT = ":8080";



    //POST host:port/auth/register
    public static Response newUserRegister(String login, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + login + "&password=" + password)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        /*?user=" + login +"&password=" + password*/
        return client.newCall(request).execute();
        /*mediaType,String.format("login=%s&password=%s", login, password)*/
    }

    //POST host:port/auth/login
    public static Response userLogin(String login, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + login + "&password=" + password)
                .post(RequestBody.create(mediaType,""))
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        return client.newCall(request).execute();
    }


}
