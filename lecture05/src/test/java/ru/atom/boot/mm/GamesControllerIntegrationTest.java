package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GamesControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void list() throws Exception {

        String str = mockMvc.perform(get("/game/list")).andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/game/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(str));


    }

}