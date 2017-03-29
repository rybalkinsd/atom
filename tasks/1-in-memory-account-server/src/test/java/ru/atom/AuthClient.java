package ru.atom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.storages.AccountStorage;

import java.io.IOException;


public class AuthClient {
    private static final Logger logger = LogManager.getLogger(AuthClient.class);

    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    //POST host:port/auth/register?user={}&password={}
    public static Response register(String userName, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/register?user=" + userName + "&password=" + password)
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

    //POST host:port/auth/login?user={}&password={}
    public static Response login(String userName, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/login?user=" + userName + "&password=" + password)
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

    //POST host:port/auth/logout
    public static Response logout(String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/auth/logout")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

    //GET host:port/data/users
    public static Response getData() throws IOException {
        Request request = new Request.Builder()
                .get().url(PROTOCOL + HOST + PORT + "/data/users")
                .build();
        logger.info(request.toString());

        return client.newCall(request).execute();
    }

}
