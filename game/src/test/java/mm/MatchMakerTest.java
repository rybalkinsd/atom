package mm;

import mm.MatchMaker;
import mm.Player;
import mm.PlayersRepository;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MatchMakerTest {

    MockMvc mockMvc;
    private static int ThreadID = 0;
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    private String names[] = {"PlayerOne", "PlayerTwo", "PlayerThree", "PlayerFour",
            "PlayerFive","PlayerSix","PlayerSeven","PlayerEight"};

    @Before
    public void registerPlayers() throws IOException{
        for(int i = 0; i < 8; i++) {
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
            Request request = new Request.Builder()
                    .post(RequestBody.create(mediaType, "name=" + names[i]))
                    .url(PROTOCOL + HOST + PORT + "/register/player")
                    .build();
            client.newCall(request).execute();
        }
    }

    @Test
    public void OneGameTest() throws IOException {
        for (int i = 0; i < 4; i++) {
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
            Request request = new Request.Builder()
                    .post(RequestBody.create(mediaType, "name=" + names[i]))
                    .url(PROTOCOL + HOST + PORT + "/matchmaker/join")
                    .build();
            Response response = client.newCall(request).execute();
            Assert.assertEquals(200, response.code());
            log.info("Matchmaker responded with body: " + response.body().string());
        }
    }

    @Test
    public void TwoGameTest() throws IOException {
        for (int i = 0; i < 8; i++) {
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
            Request request = new Request.Builder()
                    .post(RequestBody.create(mediaType, "name=" + names[i]))
                    .url(PROTOCOL + HOST + PORT + "/matchmaker/join")
                    .build();
            Response response = client.newCall(request).execute();
            Assert.assertEquals(200, response.code());
            log.info("Matchmaker responded with body: " + response.body().string());

        }
    }

}
