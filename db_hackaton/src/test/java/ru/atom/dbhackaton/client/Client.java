package ru.atom.dbhackaton.client;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by kinetik on 17.04.17.
 */
public class Client {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();
    private Long token;

    public Long getToken() {
        return this.token;
    }

    public Response register(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", user, password)
        );

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            return client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
    }

    public Response login(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", user, password)
        );
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            try {
                this.token = Long.parseLong(response.body().string());
            } catch (NumberFormatException ex) {
                //nothing
            }
            return response;
        } catch (IOException e) {
            return null;
        }
    }

    public Response logout() {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestUrl = SERVICE_URL + "/logout";
        RequestBody body = RequestBody.create(null, new byte[]{});
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            return client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
    }

}