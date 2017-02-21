package model.Server.Server.logout;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import model.server.auth.Authentication;
import model.server.auth.AuthenticationTest;
import model.server.auth.Functional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by artem on 08.11.16.
 */
public class LogoutTest {
    private static final Logger log = LogManager.getLogger(LogoutTest.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();
    static {
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setWriteTimeout(1, TimeUnit.MINUTES);
    }

    @Test
    public void LogoutTest() {
        try {
            log.info("!!!!!!!!!!!0");
            String tok;
            AuthenticationTest au = new AuthenticationTest();
            au.RegisterTest();
            tok =  au.miniLoginTest();
            log.info("!!!!!!1");
            int beforeToken = Functional.tokenDao.getAll().size();
            int beforeMatch = Functional.matchDao.getAll().size();
            miniLogoutTest(tok);
            assertEquals(beforeToken - 1, Functional.tokenDao.getAll().size());
            assertEquals(beforeMatch - 1, Functional.matchDao.getAll().size());

        }catch (Exception e) {System.out.println(e);}
    }

    private void miniLogoutTest(String tokencur)throws  Exception {
        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(
                    mediaType,
                    String.format("user=%s&password=%s", "test", "test")
            );
            String requestUrl = SERVICE_URL + "/auth/logout";
            log.info("!!!!!!!!!2");
            log.info(tokencur);
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(
                    mediaType,
                    String.format(""));
            Request request = new Request.Builder()
                    .post(body)
                    .url(requestUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer" + tokencur)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            log.info("!!!!!!!!3");
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            System.out.println(response.message());
            log.info("?????4");
        }catch (Exception e){System.out.println(e + "BBBBBBBBBBB");}
    }
}
