package ru.atom.rk1.server;


import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


@RunWith(Parameterized.class)
public class RegisterTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // попытка регистрация с кортоким логином
                {"u", "password", 400, "Too short name, sorry :("},

                // попытка регистрация с длинным логином
                {"veryVeryLongUserNameOneMoreTimeVeryVeryLongUserName", "password", 400, "Too long name, sorry :("},

                // попытка регистрация с некорректным логином
                {"123user", "password", 400,
                    "Login must stats with a letter and then contains only letters, numbers or '_'."},

                // попытка регистрация с запрещённым логином
                {"GitleR", "password", 400, "Gitler not allowed, sorry :("},

                // попытка регистрация с коротким, небезопасным паролем
                {"user", "pass", 400, "Too short password"},

                // попытка регистрация со слишком длинным паролем
                {"user", "longPassword1234567890password1234567890", 400, "Too long password"},

                // попытка регистрация с некорректным паролем
                {"user", "bad_password@", 400, "Password must be only from letters and / or numbers"},
                {"user", "{bad_password}", 400, "Password must be only from letters and / or numbers"},

                // усешная регистрация
                {"user1", "PaSsWoRd1", 200, "You've successfully registered"},
                {"user2", "PaSsWoRd2", 200, "You've successfully registered"},

                // попытка повторной регистрации
                {"user1", "PaSsWoRd1", 400, "Already registered"},
                {"user2", "PaSsWoRd2", 400, "Already registered"}
        });
    }

    @Parameterized.Parameter
    public String name;

    @Parameterized.Parameter(value = 1)
    public String password;

    @Parameterized.Parameter(value = 2)
    public int responseCode;

    @Parameterized.Parameter(value = 3)
    public String responseBody;

    @BeforeClass
    public static void setUp() throws Exception {
        AuthServer.start();
    }

    @Test
    public void registerTest() throws Exception {
        Response response = Client.register(name, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(responseCode));
        assertThat(body, is(equalTo(responseBody)));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        AuthServer.stop();
    }
}
