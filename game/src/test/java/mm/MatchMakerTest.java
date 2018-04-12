package mm;

import mm.Service.MatchMaker;
import mm.playerdb.dao.PlayerDbDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MatchMakerApp.class)
public class MatchMakerTest {

    @Autowired
    private PlayerDbDao playerDbDao;

    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    private String[] names = {
        "PlayerOne",
        "PlayerTwo",
        "PlayerThree",
        "PlayerFour",
        "PlayerFive",
        "PlayerSix",
        "PlayerSeven",
        "PlayerEight"
    };

    @Before
    public void registerPlayers() throws IOException {
        for (int i = 0; i < 8; i++) {
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
            Request request = new Request.Builder()
                    .post(RequestBody.create(mediaType, "name=" + names[i] + "&pwd=123"))
                    .url(PROTOCOL + HOST + PORT + "/register/player")
                    .build();
            client.newCall(request).execute();
            try {
             Thread.currentThread().sleep(1000); }
             catch (InterruptedException e) {}
            playerDbDao.get(names[i]).changeRating(-200 + (int) (400*(Math.random())));
        }
    }

    @Test
    public void ratingGameTest() throws IOException {
        for (int i = 0; i < 8; i++) {
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
            Request request = new Request.Builder()
                    .post(RequestBody.create(mediaType, "name=" + names[i]))
                    .url(PROTOCOL + HOST + PORT + "/matchmaker/join")
                    .build();
            Response response = client.newCall(request).execute();
            log.info("Matchmaker responded with body: " + response.body().string());
            Assert.assertEquals(200, response.code());
        }
    }

}
