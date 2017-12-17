/*
package bomber.matchmaker;

import bomber.matchmaker.request.MmRequests;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;

public class RequestTest {

    private static final Logger log = LogManager.getLogger(MmRequests.class);
    private static String name = "Кунька_Задунайский";
    private static String gameId = "42";


    @Test
    public void create() throws IOException {
        Response response = MmRequests.create(4);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("42"));
    }

    @Test
    public void start() throws IOException {
        Response response = MmRequests.start(gameId);
        log.info("[" + response + "]");
        String body = response.body().string();
        log.info(body);
        Assert.assertTrue(response.code() == 200 || body.equals("42"));
    }
    @Test
    public void checkStatus() throws IOException {
        Response response = MmRequests.checkStatus();
        log.info("[" + response + "]");
        Assert.assertTrue(response.code() == 200);//работу данного метода лучше проверять в тестах GS
    }


}
*/
