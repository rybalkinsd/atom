package ru.atom.client;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Created by kinetik on 26.03.17.
 */
public class ClientImpl implements Client {
    private static final Logger log = LogManager.getLogger(ClientImpl.class);
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
                String.format("login=%s&password=%s", user, password)
        );

        String requestUrl = SERVICE_URL + "/registrate";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            return client.newCall(request).execute();
        } catch (IOException e) {
            log.warn("Something went wrong in register.", e);
            return null;
        }
    }

    public Response login(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s&password=%s", user, password)
        );
        String requestUrl = SERVICE_URL + "/login";
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
            log.warn("Something went wrong in login.", e);
            return null;
        }
    }

    @Override
    public Response logout(String name) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s", name)
        );
        String requestUrl = SERVICE_URL + "/logout";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            return client.newCall(request).execute();
        } catch (IOException e) {
            log.warn("Something went wrong in logout.", e);
            return null;
        }
    }

    @Override
    public Response getUsers(String user) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s", user)
        );
        String requestUrl = SERVICE_URL + "/getUsers";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return null;
        }
    }
}
