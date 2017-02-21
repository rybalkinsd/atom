package main.java.zagar.auth;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class AuthClient {
  @NotNull
  private static final Logger log = LogManager.getLogger(AuthClient.class);
  @NotNull
  private String serviceUrl = "";
  @NotNull
  private final OkHttpClient client = new OkHttpClient();

  public AuthClient(@NotNull String accountServerHost,int accountServerPort) {
    serviceUrl = "http://" + accountServerHost + ":" + accountServerPort;
  }

  public boolean register(@NotNull String user, @NotNull String password) {
    log.info("Trying to register user=" + user);
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        String.format("user=%s&password=%s", user, password)
    );

    String requestUrl = serviceUrl + "/auth/register";
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

  @Nullable
  public String login(@NotNull String user, @NotNull String password) {
    log.info("Trying to login user=" + user);
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        String.format("user=%s&password=%s", user, password)
    );
    String requestUrl = serviceUrl + "/auth/login";
    Request request = new Request.Builder()
        .url(requestUrl)
        .post(body)
        .addHeader("content-type", "application/x-www-form-urlencoded")
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        return response.body().string();
      } else return null;
    } catch (IOException e) {
      log.warn("Something went wrong in login.", e);
      return null;
    }
  }

  public boolean logout(@NotNull Long token) {
    log.info("Trying to logout user with token " + token);
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        ""
    );
    String requestUrl = serviceUrl + "/auth/logout";
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
}

