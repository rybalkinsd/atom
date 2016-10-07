package ru.atom.client;

import com.squareup.okhttp.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.Gender;
import ru.atom.model.person.Person;
import ru.atom.model.person.PersonBatchHolder;

import java.io.IOException;
import java.util.Collection;

public class RestClientImpl implements RestClient {
    private static final Logger log = LogManager.getLogger(RestClientImpl.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();

    /***
     * Registers user
     *
     * !!NOTE!! Registrations expire on server shutdown.
     *
     * @param user - username
     * @param password
     * @return true if successfully registered
     */
    public boolean register(String user, String password) {
        MediaType mediaType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s&password=%s", user, password)
        );

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in register.", e);
            return false;
        }
    }

    /**
     * Your code here
     *
     * @param user - username
     * @param password
     * @return token
     */
    public Long login(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s&password=%s", user, password)
        );
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return Long.parseLong(response.body().string());
        } catch (IOException e) {
            log.warn("Something went wrong in login.", e);
            return null;
        }
    }

    /**
     * Your code here
     *
     * @param token - auth token
     * @param gender - expected gender of result persons
     * @return collection of persons
     */
    @Override
    public Collection<? extends Person> getBatch(Long token, Gender gender) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("gender=%s", Gender.FEMALE)
        );
        String requestUrl = SERVICE_URL + "/data/personsbatch";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String personsJson = response.body().string();
            return PersonBatchHolder.readJson(personsJson)
                    .getPersons();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return null;
        }
    }
}
