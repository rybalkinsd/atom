package ru.atom;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private class Request implements Callable {
        private String name;

        public Request(String name) {
            this.name = name;
        }

        @Override
        public MvcResult call() throws Exception {
            return mockMvc.perform(post("/matchmaker/join")
                    .content("name=" + name)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andReturn();
        }
    }

    @Ignore
    @Test
    public void connect() throws Exception {

        FutureTask<MvcResult> reqest = new FutureTask<MvcResult>(new Request("a"));
        FutureTask<MvcResult> req = new FutureTask<MvcResult>(new Request("b"));
        Thread thr = new Thread(req);
        Thread thread = new Thread(reqest);
        thr.start();
        thread.start();
        MvcResult mvcResult = req.get();
        MvcResult mvcResult1 = reqest.get();
        String asd = mvcResult.getResponse().getContentAsString();
        String asdf = mvcResult.getResponse().getContentAsString();
        assertTrue(mvcResult.getResponse().getStatus() == 200);
        assertTrue(mvcResult1.getResponse().getStatus() == 200);
        assertTrue(asd.equals(asdf));
        assertTrue(true);

    }
}


