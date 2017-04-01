package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class RegisterTest2 {

    @BeforeClass
    public static void setUp() throws Exception {
        AuthServer.start();
    }

    @Test
    public void badRegisterTest() throws Exception {
        // попытка регистрации с некорректными параметрами запроса
        Response response = Client.badRegister("user3", "PaSsWoRd3");
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(400));
    }

    @Test
    public void emptyRegisterTest() throws Exception {
        // попытка регистрации без параметров запроса
        Response response = Client.emptyRegister();
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(400));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        AuthServer.stop();
    }
}
