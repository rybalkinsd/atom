package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GamesControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void list() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        GameController gameController = new GameController();

        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        mockMvc.perform(post("/connection/connect")
                .content("id=2&name=b")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        mockMvc.perform(post("/connection/connect")
                .content("id=3&name=c")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        mockMvc.perform(post("/connection/connect")
                .content("id=4&name=d")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        GameSession session = new GameSession(ConnectionQueue.getInstance().toArray(new Connection[0]));
        GameRepository.put(session);

        String result = mockMvc.perform(get("/game/list")).andReturn().getResponse().getContentAsString();
        assertEquals(result, GameRepository.getAll().toString());
    }

}