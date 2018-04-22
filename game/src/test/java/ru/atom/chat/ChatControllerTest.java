package ru.atom.chat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest
public class ChatControllerTest {

    private static NameGen gen = new NameGen();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void onlineTest() throws Exception {
        mockMvc.perform(get("/chat/online"))
                .andExpect(status().isOk());
    }

    @Test
    public void chatTest() throws Exception {

        mockMvc.perform(get("/chat/chat")
                .content("name=" + gen.generateName() + "&password=123&passCopy=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void registerTest() throws  Exception {
        mockMvc.perform(post("/chat/register")
                .content("name=" + gen.generateName() + "&password=123&passCopy=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws  Exception {
        String name = gen.generateName();
        // do registration
        mockMvc.perform(post("/chat/register")
                .content("name=" + name + "&password=123&passCopy=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        //logout
        mockMvc.perform(post("/chat/logout")
                .content("name=" + name)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        //only then we can perform login
        mockMvc.perform(post("/chat/login")
                .content("name=" + name + "&password=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        // it looks like integrated test, but not the unit test
        // but we have to register and logout before login
        // can we in such model do unit test for login?
    }

    @Test
    public void logoutTest() throws  Exception {
        String name = gen.generateName();
        // do registration
        mockMvc.perform(post("/chat/register")
                .content("name=" + name + "&password=123&passCopy=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        // logout
        mockMvc.perform(post("/chat/logout")
                .content("name=" + name)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void usersTest() throws Exception {
        mockMvc.perform(get("/chat/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void sayTest() throws Exception {
        String name = gen.generateName();
        // do registration
        mockMvc.perform(post("/chat/register")
                .content("name=" + name + "&password=123&passCopy=123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
        Thread.sleep(3000);
        //saying
        mockMvc.perform(post("/chat/say")
                .content("name=" + name + "&password=123&msg=allo")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());
    }



}
