import okhttp3.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    @Autowired
    private PlayersRepository playersRepository;

    @Before
    public void registerPlayers() {
        playersRepository.add(new Player("PlayerOne"));
        playersRepository.add(new Player("PlayerTwo"));
        playersRepository.add(new Player("PlayerThree"));
        playersRepository.add(new Player("PlayerFour"));
    }


    @Test
    public void OneGameTest() {
        for(int i = 0; i < 4; i++) {
            mockMvc.perform(get("/person/1")
        }
    }


