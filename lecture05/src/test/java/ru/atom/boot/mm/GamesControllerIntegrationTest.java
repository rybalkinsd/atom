package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Some annotations here
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class GamesControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    // @Ignore
    public void list() throws Exception {
        mockMvc.perform(get("/game/list")
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk());
    }

}