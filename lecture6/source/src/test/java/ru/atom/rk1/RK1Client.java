package ru.atom.rk1;

import com.squareup.okhttp.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RK1Client {
  private static final Logger log = LogManager.getLogger(RK1Client.class);
  private static final String PROTOCOL = "http";
  private static final String HOST = "localhost";
  private static final String PORT = "8080";
  private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

  private static final OkHttpClient client = new OkHttpClient();

  public boolean register(String user, String password) {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
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

  public boolean logout(Long token) {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        ""
    );
    String requestUrl = SERVICE_URL + "/auth/logout";
    Request request = new Request.Builder()
        .url(requestUrl)
        .post(body)
        .addHeader("content-type", "application/x-www-form-urlencoded")
        .addHeader("authorization", "Bearer " + token)
        .build();

    try {
      Response response = client.newCall(request).execute();
      return response.isSuccessful();
    } catch (IOException e) {
      log.warn("Something went wrong in logout.", e);
      return false;
    }
  }

  public boolean rename(Long token, String newName) {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        String.format("name=%s", newName)
    );
    String requestUrl = SERVICE_URL + "/profile/name";
    Request request = new Request.Builder()
        .url(requestUrl)
        .post(body)
        .addHeader("content-type", "application/x-www-form-urlencoded")
        .addHeader("authorization", "Bearer " + token)
        .build();
    try {
      Response response = client.newCall(request).execute();
      return response.isSuccessful();
    } catch (IOException e) {
      log.warn("Something went wrong during name change.", e);
      return false;
    }
  }

  public String online() {
    String requestUrl = SERVICE_URL + "/data/users";
    Request request = new Request.Builder()
        .url(requestUrl)
        .get()
        .build();
    try {
      Response response = client.newCall(request).execute();
      return response.body().string();
    } catch (IOException e) {
      log.warn("Something went wrong during name users request.", e);
      return null;
    }
  }
}
