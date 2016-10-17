package client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import model.Token;
import model.User;
import model.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestClient implements IRestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();

    /***
     * Registers user
     *
     *
     * @param user - username
     * @param password - password
     * @return true if successfully registered
     */
    @Override
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
            Response response = client.newCall(request).execute();
            boolean result = response.isSuccessful();
            if (result) {
                log.info("You have been registered.");
            } else {
                log.warn("You can't be registered with such values.");
            }
            return result;
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return false;
        }
    }

    /**
     *
     * @param user - username
     * @param password - password
     * @return token
     */
    @Override
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
            Long result = Long.parseLong(response.body().string());
            log.info("You have been logged in.");
            return result;
        } catch (NumberFormatException e) {
            log.warn("Login is not correct.");
            return null;
        } catch (IOException e) {
            log.warn("Something went wrong in login.", e);
            return null;
        }
    }

    /**
     *
     * @param token - token
     * @return boolean
     */
    @Override
    public boolean logout(Long token) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType, ""
        );
        String requestUrl = SERVICE_URL + "/auth/logout";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            boolean result = response.isSuccessful();
            if (result) {
                log.info("You have been logged out.");
            } else {
                log.warn("Your token is not valid.");
            }
            return result;
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return false;
        }
    }

    /**
     *
     * @param token - token
     * @param userName - user's name
     * @return boolean
     */
    @Override
    public boolean changeName(Long token, String userName) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("name=%s", userName)
        );
        String requestUrl = SERVICE_URL + "/auth/changeName";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            boolean result = response.isSuccessful();
            if (result) {
                log.info("The name was changed successfully!");
            } else {
                log.warn("Your token is not valid.");
            }
            return result;
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return false;
        }
    }

    /**
     *
     * @param token - token
     * @return userList
     */
    @Override
    public List getUsers(Long token) {

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,""
        );
        String requestUrl = SERVICE_URL + "/api/users";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String usersJson = response.body().string();
                log.info("Json string - {}", usersJson);
                return UserStore.readJsonNames(usersJson);
            } else {
                log.warn("Your token is not valid.");
                return null;
            }
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return null;
        }
    }
}
