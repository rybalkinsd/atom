package ru.atom.chat;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ChatClientTestIntegration {
    @Autowired
    MockMvc mockMvc;


    @Test
    public void logout() throws Exception {

        mockMvc.perform(post("/chat/login")
                .content("name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        mockMvc.perform(post("/chat/logout")
                .content("name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        mockMvc.perform(get("/chat/online"))
                .andDo(print())
                .andExpect(content().string(""));
    }

    
    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/chat/login")
                .content("name=a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());


        mockMvc.perform(get("/chat/online"))
                .andDo(print())
                .andExpect(content().string("a"));


    }
    

}