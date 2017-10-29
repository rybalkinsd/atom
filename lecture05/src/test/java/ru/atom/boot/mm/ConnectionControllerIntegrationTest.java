package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.assertEquals;

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
        ConnectionController connectionHandler = new ConnectionController();
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        mockMvc.perform(post("/connection/connect")
                .content("id=2&name=b")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        String result = mockMvc.perform(get("/connection/list")).andReturn().getResponse().getContentAsString();
        assertEquals(result, ConnectionQueue.getInstance().toString());
    }
}