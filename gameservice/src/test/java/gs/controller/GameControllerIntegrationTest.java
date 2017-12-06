package gs.controller;

import gs.GameServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(GameController.class)
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void startServer() {
        GameServer.startGs();
    }

    @Test
    public void list() throws Exception {
        this.mockMvc.perform(post("/game/create")
                .content("id=1&name=Alice")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
    }
}
