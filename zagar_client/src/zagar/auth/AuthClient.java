package zagar.auth;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static zagar.GameConstants.DEFAULT_ACCOUNT_SERVER_HOST;
import static zagar.GameConstants.DEFAULT_ACCOUNT_SERVER_PORT;

public class AuthClient {
  @NotNull
  private static final Logger log = LogManager.getLogger(AuthClient.class);
  @NotNull
  private static String serviceUrl = "http://" + DEFAULT_ACCOUNT_SERVER_HOST + ":" + DEFAULT_ACCOUNT_SERVER_PORT;
  @NotNull
  private final OkHttpClient client = new OkHttpClient();

  public boolean register(@NotNull String user, @NotNull String password) {
    log.info("Trying to register user=" + user);
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(
        mediaType,
        String.format("user=%s&password=%s", user, password)
    );

    try {
      String requestUrl = serviceUrl + "/auth/register";
      Request request = new Request.Builder()
          .url(requestUrl)
          .post(body)
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .build();

      Response response = client.newCall(request).execute();
      return response.isSuccessful();
    } catch (IOException e) {
      log.warn("Something went wrong in register.", e);
      return false;
    } catch (IllegalArgumentException e) {
      log.warn("Account server on " + serviceUrl + "/auth/login not detected", e);
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
    try {
      String requestUrl = serviceUrl + "/auth/login";
      Request request = new Request.Builder()
          .url(requestUrl)
          .post(body)
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        return response.body().string();
      } else return null;
    } catch (IOException e) {
      log.warn("Something went wrong in login.", e);
      return null;
    } catch (IllegalArgumentException e) {
      log.warn("Account server on " + serviceUrl + "/auth/login not detected", e);
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
  public static void setServiceUrl(String serviceUrl){
    AuthClient.serviceUrl=serviceUrl;
  }
}

