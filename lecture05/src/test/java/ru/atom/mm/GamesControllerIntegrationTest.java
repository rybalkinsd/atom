package ru.atom.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.mm.model.Connection;
import ru.atom.mm.model.GameSession;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.GameRepository;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Some annotations here
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@Import(Config.class)
public class GamesControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GameRepository repository;
    @Autowired
    ConnectionQueue queue;
    @Test
    public void list() throws Exception {
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        mockMvc.perform(post("/connection/connect")
                .content("id=2&name=b")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        Connection[] connections = new Connection[2];
        connections[0] = queue.getQueue().take();
        connections[1] = queue.getQueue().take();
        repository.put(new GameSession(connections));
        assertEquals(mockMvc.perform(get("/game/list")).andReturn().getResponse().getContentAsString(),
                "[GameSession{connections=[Connection{playerId=1, name='a'}, Connection{playerId=2, name='b'}], id=0}]");
    }

}