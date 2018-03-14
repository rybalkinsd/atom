package ru.atom.boot.hw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HelloControllerTest {


    /**
     * This is Dependency Injection usage
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void hiTest() throws Exception {
        mockMvc.perform(get("/hello/world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Spring-boot World!"));
    }

}