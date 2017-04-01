package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class LogoutTest {

    @BeforeClass
    public static void setUp() throws Exception {
        AuthServer.start();
    }

    @Test
    public void logoutTest1() throws Exception {
        Response response;
        String name = "User";
        String password = "PaSsWoRd";

        // регистрируем пользователя
        response = Client.register(name, password);
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(200));

        // логинимся
        response = Client.login(name, password);
        System.out.println("[" + response + "]");
        String token = response.body().string();
        System.out.println(token);
        assertThat(response.code(), is(200));

        // разлогиниваемся по токену
        response = Client.logout(token);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(200));
        assertThat(body, is(equalTo("You've successfully logged out")));

        // пытаемся разлогиниться по уже неавторизованному токену
        response = Client.logout(token);
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(401));
        assertThat(body, is(equalTo("First you need to login")));
    }

    @Test
    public void logoutTest2() throws Exception {
        // пытаемся разлогиниться без токена
        Response response = Client.emptyLogout();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(401));
        assertThat(body, is(equalTo("First you need to login")));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        AuthServer.stop();
    }
}
