package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;


public class SoHard {

    @Test
    public void test() throws Exception {
        AuthServer.start();

        // попытка регистрация с кортоким логином
        Response response = Client.register("u", "password");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Too short name, sorry :(", is(equalTo(body)));

        // попытка регистрация с длинным логином
        response = Client.register("veryVeryLongUserNameOneMoreTimeVeryVeryLongUserName", "password");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Too long name, sorry :(", is(equalTo(body)));

        // попытка регистрация с некорректным логином
        response = Client.register("123user", "password");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        String resp = "Login must stats with a letter and then contains " +
                      "only letters, numbers or '_'.";
        assertThat(resp, is(equalTo(body)));

        // попытка регистрация с запрещённым логином
        response = Client.register("GitleR", "password");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Gitler not allowed, sorry :(", is(equalTo(body)));

        // попытка регистрация с коротким, небезопасным паролем
        response = Client.register("user", "pass");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Too short password", is(equalTo(body)));

        // попытка регистрация со слишком длинным паролем
        response = Client.register("user", "longPassword1234567890password1234567890");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Too long password", is(equalTo(body)));

        // попытка регистрация с некорректным паролем
        response = Client.register("user", "bad_password@");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Password must be only from letters and / or numbers", is(equalTo(body)));

        // попытка регистрация с некорректным паролем
        response = Client.register("user", "{bad_password}");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Password must be only from letters and / or numbers", is(equalTo(body)));

        // усешная регистрация
        response = Client.register("user1", "PaSsWoRd1");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(200, is(response.code()));
        assertThat("You've successfully registered", is(equalTo(body)));

        // усешная регистрация
        response = Client.register("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(200, is(response.code()));
        assertThat("You've successfully registered", is(equalTo(body)));

        // попытка регистрации с неправильными параметрами запроса
        response = Client.badRegister("user3", "PaSsWoRd3");
        System.out.println("[" + response + "]");
        assertThat(400, is(response.code()));

        // попытка регистрации без параметров запроса
        response = Client.emptyRegister();
        System.out.println("[" + response + "]");
        assertThat(400, is(response.code()));

        // попытка повторной регистрации
        response = Client.register("user1", "PaSsWoRd11");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Already registered", is(equalTo(body)));

        // попытка повторной регистрации
        response = Client.register("user2", "PaSsWoRd12");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(400, is(response.code()));
        assertThat("Already registered", is(equalTo(body)));

        // попытка авторизоваться с неправильными параметрами запроса
        response = Client.badLogin("user0", "PaSsWoRd0");
        System.out.println("[" + response + "]");
        assertThat(400, is(response.code()));

        // попытка авторизоваться без параметров запроса
        response = Client.emptyLogin();
        System.out.println("[" + response + "]");
        assertThat(400, is(response.code()));

        // попытка авторизации незарегистрированным пользователем
        response = Client.login("user0", "PaSsWoRd0");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(403, is(response.code()));
        assertThat("Bad name and/or password", is(equalTo(body)));

        // попытка авторизации с неправильным поролем
        response = Client.login("user1", "PaSsWoRd0");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(403, is(response.code()));
        assertThat("Bad name and/or password", is(equalTo(body)));

        // успешная авторизация
        response = Client.login("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        String token1 = response.body().string();
        System.out.println(token1);
        assertThat(200, is(response.code()));

        // повторная успешная авторизация
        response = Client.login("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        String token2 = response.body().string();
        System.out.println(token2);
        assertThat(200, is(response.code()));

        // при повторной регистрации токены совпадают
        System.out.println(token1 + " == " + token2);
        assertThat(token1, is(equalTo(token2)));

        // разлогиниваемся по токену
        response = Client.logout(token2);
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(200, is(response.code()));
        assertThat("You've successfully logged out", is(equalTo(body)));

        // пытаемся разлогиниться по неавторизованному токену
        response = Client.logout(token2);
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(401, is(response.code()));
        assertThat("First you need to login", is(equalTo(body)));

        // пытаемся разлогиниться без токена
        response = Client.emptyLogout();
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(401, is(response.code()));
        assertThat("First you need to login", is(equalTo(body)));

        // повторная успешная авторизация
        response = Client.login("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        token2 = response.body().string();
        System.out.println(token2);
        assertThat(200, is(response.code()));

        // при повторной регистрации токены совпадают
        assertThat(token1, is(equalTo(token2)));

        AuthServer.stop();
        AuthServer.start();

        // различные токены при повторных регистрации и авторизации поле перезапуска сервера
        response = Client.register("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(200, is(response.code()));
        assertThat("You've successfully registered", is(equalTo(body)));

        response = Client.login("user2", "PaSsWoRd2");
        System.out.println("[" + response + "]");
        token2 = response.body().string();
        System.out.println(token2);
        assertThat(200, is(response.code()));

        System.out.println(token1 + " != " + token2);
        assertThat(token1, is(not(equalTo(token2))));

        // зпрашиваем список авторизованных пользователей
        response = Client.users();
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertThat(200, is(response.code()));

        AuthServer.stop();
    }
}
