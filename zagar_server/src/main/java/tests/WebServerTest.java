package tests;

import com.squareup.okhttp.*;
import main.MasterServer;
import org.jetbrains.annotations.Nullable;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by user on 07.11.16.
 * <p>
 * Superclass for all web API tests
 */
public class WebServerTest {
    private static final String SERVICE_URL = "http://localhost:" + 8080 + "/";
    private static OkHttpClient client = new OkHttpClient();
    private static MediaType mt = MediaType.parse("raw");

    protected static Response postRequest(@NotNull String urlPostfix, @NotNull String body, @Nullable String token)
            throws IOException {
        Request req = new com.squareup.okhttp.Request.Builder()
                .url(SERVICE_URL + urlPostfix)
                .post(RequestBody.create(mt, body))
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return client.newCall(req).execute();
    }

    protected static Response getRequest(@NotNull String urlPostfix, @NotNull String params, @Nullable String token)
            throws IOException {
        Request req = new com.squareup.okhttp.Request.Builder()
                .url(SERVICE_URL + urlPostfix + "?" + params)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return client.newCall(req).execute();
    }

    @BeforeClass
    public static void startServer() {
        try {
            MasterServer.main(new String[0]);
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @AfterClass
    public static void stopServer() {
        MasterServer.stop();
    }
}
