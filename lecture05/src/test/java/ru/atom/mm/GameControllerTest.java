package ru.atom.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.model.Connection;
import ru.atom.mm.model.GameSession;
import ru.atom.mm.service.ConnectionQueue;
import ru.atom.mm.service.GameRepository;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(Config.class)
public class GameControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GameRepository gameRepository;

    @Test
    public void list() throws Exception{
        Connection[] connections = new Connection[2];
        connections[0] = new Connection(1, "a");
        connections[1] = new Connection(2, "b");
        gameRepository.put(new GameSession(connections));
        assertEquals(mockMvc.perform(get("/game/list")).andReturn().getResponse().getContentAsString(),
                "[GameSession{connections=[Connection{playerId=1, name='a'}, Connection{playerId=2, name='b'}], id=0}]");
    }

}