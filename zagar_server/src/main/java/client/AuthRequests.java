package client;

import com.squareup.okhttp.*;
import accountserver.authInfo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 06.11.2016.
 */
public class AuthRequests {
    private static final Logger log = LogManager.getLogger(AuthRequests.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8081";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();
    static {
        client.setReadTimeout(100, TimeUnit.SECONDS);
        client.setWriteTimeout(100, TimeUnit.SECONDS);
    }



    public boolean register(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body;
        if(password != null) {
            body = RequestBody.create(
                    mediaType,
                    String.format("user=%s&password=%s", user, password)
            );
        } else {
            body = RequestBody.create(
                    mediaType,
                    String.format("user=%s", user)
            );
        }

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



    public Long login(String user, String password) {
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
            return Long.parseLong(response.body().string());
        } catch (IOException e) {
            log.warn("Something went wrong in login.", e);
            return null;
        }
    }


    public boolean logout(Long token) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,""
        );
        String requestUrl = SERVICE_URL + "/auth/logout";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return false;
        }
    }

    public boolean changeName(Long token, String newName) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,"name=" + newName
        );
        String requestUrl = SERVICE_URL + "/profile/name";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return false;
        }
    }

    public boolean changeEmail(Long token, String newEmail) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,"email=" + newEmail
        );
        String requestUrl = SERVICE_URL + "/profile/email";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return false;
        }
    }

    public boolean changePassword(Long token, String newPassword) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,"password=" + newPassword
        );
        String requestUrl = SERVICE_URL + "/profile/password";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization", "Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return false;
        }
    }

    public String getUsers() {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String requestUrl = SERVICE_URL + "/data/users";
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            log.warn("Something went wrong in getBatch.", e);
            return null;
        }
    }

    public String getLeaders(int N){
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        String requestUrl = SERVICE_URL + "/data/leaderboard?N="+N;
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            System.out.println(response.isSuccessful());
            String result;
            try(ResponseBody body = response.body()){
                result =  body.string();
                return result;
            } catch (Exception e){
                return null;
            }
        } catch (IOException e) {
            return null;
            //log.warn("Something went wrong in register.", e);
        }
    }
}
