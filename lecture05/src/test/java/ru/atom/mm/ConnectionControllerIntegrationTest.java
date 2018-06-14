package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.mm.service.MatchMaker;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(Config.class)
public class ConnectionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchMaker matchMaker;

    @Test
    public void connect() throws Exception {
        matchMaker.clearCandidates();
        mockMvc.perform(post("/connection/connect")
                    .content("id=1&name=a")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        matchMaker.clearCandidates();

        mockMvc.perform(post("/connection/connect")
                .content("id=2&name=Alex")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        mockMvc.perform(get("/connection/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Connection{playerId=2, name='Alex'}")));
    }
}