package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class UsersTest {

    @Test
    public void registerTest() throws Exception {
        AuthServer.start();

        Client.register("user1", "password1");
        Client.login("user1", "password1");

        Client.register("user2", "password2");
        Client.login("user2", "password2");

        // зпрашиваем список авторизованных пользователей
        Response response = Client.users();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(200));

        AuthServer.stop();
    }
}
