package ru.atom.matchmaker.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MatchmakerComponent.class)
public class MatchmakerComponentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchmakerComponent matchmakerComponent;

    @Test
    public void signupTest() throws Exception {
        mockMvc.perform(post("/matchmaker/signup")
                .content("login=admin&password=1234")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(result -> "ok".equals(result.getResponse().getContentAsString()));
    }

    @Test
    public void joinTest() throws Exception {
        mockMvc.perform(post("/matchmaker/join")
                .content("login=admin&password=1234")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }
}