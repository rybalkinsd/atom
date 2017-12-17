/*
package bomber.matchmaker;

import bomber.matchmaker.WebSocketClient.*;
import bomber.matchmaker.controller.MmController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = MatchMakerApplication.class)
public class JoinMockTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void joinOnePlayer() throws Exception {
        mockMvc.perform(post("/matchmaker/join")
                .content("name=Кунька_Задунайский")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
        Assert.assertTrue(MmController.getGameId().equals(42));

    }
    @Test
    public void joinFourPlayer() throws Exception {
        mockMvc.perform(post("/matchmaker/join")
                .content("name=Поручик_Ржевский")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client1 = new WebSocketClient("Поручик_Ржевский","42");
        client1.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Кашалот_Евгенич")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client2 = new WebSocketClient("Кашалот_Евгенич","42");
        client2.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Изя_Шпайцман")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client3 = new WebSocketClient("Изя_Шпайцман","42");
        client3.startClient();

        mockMvc.perform(post("/matchmaker/join")
                .content("name=Адольф_Виссарионович")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        WebSocketClient client4 = new WebSocketClient("Адольф_Виссарионович","42");
        client4.startClient();

        wait(20000);

        Assert.assertNull(MmController.getGameId());
    }
}*/
