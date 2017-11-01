package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.thread.mm.GameRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConnectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void connect() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        assertThat(connectionHandler.list()).isEmpty();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        connectionHandler.connect(4, "d");
        connectionHandler.connect(5, "e");

        //assertThat(connectionHandler.list()).isNotEmpty();
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(post("/connection/connect")
                .content("id=1&name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

}