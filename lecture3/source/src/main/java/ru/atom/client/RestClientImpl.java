package ru.atom.client;

import com.squareup.okhttp.*;
import ru.atom.model.Gender;
import ru.atom.model.Person;

import java.io.IOException;
import java.util.Collection;

public class RestClientImpl implements RestClient {
    private static final String PROTOCOL = "http";
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "8080";

    public boolean register(String user, String password) {
        String url = PROTOCOL + "://" + HOST + ":" + PORT + "/auth/register";
        return false;
    }

    public Long login(String user, String password) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,
                "login=admin" +
                "&" +
                "password=admin"
        );
        Request request = new Request.Builder()
                .url(PROTOCOL + "://" + HOST + ":" + PORT + "/auth/login")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return Long.parseLong(response.body().string());
        } catch (IOException e) {
            // log
            return null;
        }

    }

    @Override
    public Collection<? extends Person> getBatch(Gender gender) {
        return null;
    }
}
