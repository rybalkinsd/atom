package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ConnectionControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void connect() throws Exception {
        mockMvc.perform(post("/connection/connect")
                    .content("id=1&name=a")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=I")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=Like")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=Java")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
        mockMvc.perform(get("/connection/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("I, Like, Java"));
    }

}