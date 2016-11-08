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
            String tok;
            AuthenticationTest au = new AuthenticationTest();
            au.RegisterTest();
            tok =  au.miniLoginTest();
            int beforeToken = Functional.tokenDao.getAll().size();
            int beforeMatch = Functional.matchDao.getAll().size();
            miniLogoutTest(tok);
            assertEquals(beforeToken - 1, Functional.tokenDao.getAll().size());
            assertEquals(beforeMatch - 1, Functional.matchDao.getAll().size());

        }catch (Exception e) {System.out.println(e);}
    }

    private void miniLogoutTest(String tokencur)throws  Exception {
        try {
            String requestUrl = SERVICE_URL + "/auth/logout";
            log.info(tokencur);
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .addHeader("Authorization", "Bearer" + tokencur)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            log.info("!!!!!!!!");
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            System.out.println(response.message());
            log.info("?????");
        }catch (Exception e){System.out.println(e + "BBBBBBBBBBB");}
    }
}
