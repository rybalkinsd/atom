package ru.atom.rk1.server;

import okhttp3.Response;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TokenTest {
    private String str = "22337203685477580";

    @Test
    public void equalsTokensTest1() {
        Token token1 = new Token(str);
        assertThat(token1, is(equalTo(token1)));
    }

    @Test
    public void notEqualsTokensTest1() {
        Token token1 = new Token(str);
        assertThat(token1, is(not(equalTo(str))));
    }

    @Test
    public void equalsTokensTest2() {
        Token token1 = new Token(str);
        Token token2 = new Token(str);
        assertThat(token1, is(equalTo(token2)));
    }

    @Test
    public void notEqualsTokensTest2() {
        Token token1 = new Token(str);
        Token token2 = new Token("1" + str);
        assertThat(token1, is(not(equalTo(token2))));
    }

    @Test
    public void putTest() {
        // тестируем коллизию с одинаковыми токенами для разных пользователей
        TokenStorage.init();

        String name = "User";

        // авторизовываем первого пользователя
        User user1 = new User(name, "PaSsWoRd1");
        assertThat(TokenStorage.put(user1), is(true));
        assertThat(TokenStorage.tokenStorage.containsValue(user1), is(true));

        // пытаемся авторизовать этого же пользователя
        assertThat(TokenStorage.put(user1), is(true));
        assertThat(TokenStorage.tokenStorage.containsValue(user1), is(true));

        // пытаемся авторизовать второго пользователя с таким токеном
        User user2 = new User(name, "PaSsWoRd2");
        assertThat(TokenStorage.put(user2), is(false));
    }

    @Test
    public void sameTokensTest() throws Exception {
        Response response;
        String user = "user";
        String password = "PaSsWoRd";

        AuthServer.start();

        // регистрируем пользователя
        response = Client.register(user, password);
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(200));

        // логинимся первый раз
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token1 = response.body().string();
        System.out.println(token1);
        assertThat(response.code(), is(200));

        // логинимся второй раз
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token2 = response.body().string();
        System.out.println(token2);
        assertThat(response.code(), is(200));


        // при повторной регистрации без перезапуска сервера токены должны совпадать
        assertThat(token2, is(equalTo(token1)));

        // логинимся третий раз
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token3 = response.body().string();
        System.out.println(token3);
        assertThat(response.code(), is(200));

        // при повторной регистрации без перезапуска сервера токены должны совпадать
        assertThat(token3, is(equalTo(token1)));

        AuthServer.stop();
    }

    @Test
    public void notSameTokensTest() throws Exception {
        Response response;
        String user = "user";
        String password = "PaSsWoRd";

        AuthServer.start();

        // регистрируем пользователя
        response = Client.register(user, password);
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(200));

        // логинимся
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token1 = response.body().string();
        System.out.println(token1);
        assertThat(response.code(), is(200));

        // перезапускаем сервер
        AuthServer.stop();
        AuthServer.start();

        // снова регистрируем пользователя
        response = Client.register(user, password);
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(200));

        // снова логинимся
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token2 = response.body().string();
        System.out.println(token2);
        assertThat(response.code(), is(200));

        // перезапускаем сервер
        AuthServer.stop();
        AuthServer.start();

        // снова регистрируем пользователя
        response = Client.register(user, password);
        System.out.println("[" + response + "]");
        assertThat(response.code(), is(200));

        // снова логинимся
        response = Client.login(user, password);
        System.out.println("[" + response + "]");
        String token3 = response.body().string();
        System.out.println(token3);
        assertThat(response.code(), is(200));

        AuthServer.stop();

        // после перезагрузки сервера пользователь должен получать разные токены
        assertThat(token2, is(not(equalTo(token1))));
        assertThat(token3, is(not(equalTo(token1))));
        assertThat(token3, is(not(equalTo(token2))));
    }
}
