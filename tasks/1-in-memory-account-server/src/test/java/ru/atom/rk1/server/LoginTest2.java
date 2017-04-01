package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class LoginTest2 {

    @BeforeClass
    public static void setUp() throws Exception {
        AuthServer.start();
    }

    @Test
    public void badLoginTest1() throws Exception {
        // попытка регистрации с некорректными параметрами запроса
        Response response = Client.badLogin1("user3", "PaSsWoRd3");
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(400));
    }

    @Test
    public void badLoginTest2() throws Exception {
        // попытка регистрации с некорректными параметрами запроса
        Response response = Client.badLogin2("user3", "PaSsWoRd3");
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(400));
    }

    @Test
    public void emptyLoginTest() throws Exception {
        // попытка регистрации без параметров запроса
        Response response = Client.emptyLogin();
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(400));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        AuthServer.stop();
    }
}
