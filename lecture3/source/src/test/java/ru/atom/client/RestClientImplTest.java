package ru.atom.client;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s.rybalkin on 04.10.2016.
 */
public class RestClientImplTest {

    private RestClient client = new RestClientImpl();

    @Test
    public void register() throws Exception {
        String user = "test";
        String password = "test";
        assertTrue(client.register(user, password));
    }

    @Test
    public void login() throws Exception {
        assertEquals(Long.valueOf(1), client.login("admin", "admin"));
    }

    @Test
    public void getBatch() throws Exception {

    }

}