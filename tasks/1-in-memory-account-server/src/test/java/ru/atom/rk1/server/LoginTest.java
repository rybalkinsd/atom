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
public class LoginTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // попытка авторизации незарегистрированным пользователем
                {"user0", "PaSsWoRd0", 403, "Bad name and/or password"},

                // попытка авторизации с неправильным поролем
                {"user1", "PaSsWoRd0", 403, "Bad name and/or password"},

                // успешная авторизация
                {"user1", "PaSsWoRd1", 200, ""},
                {"user2", "PaSsWoRd2", 200, ""},

                // повторная успешная авторизация
                {"user1", "PaSsWoRd1", 200, ""},
                {"user2", "PaSsWoRd2", 200, ""},
                {"user2", "PaSsWoRd2", 200, ""}
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
        Client.register("user1", "PaSsWoRd1");
        Client.register("user2", "PaSsWoRd2");
    }

    @Test
    public void loginTest() throws Exception {
        // попытка авторизации незарегистрированным пользователем
        Response response = Client.login(name, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertThat(response.code(), is(responseCode));
        if (response.code() != 200)
            assertThat(body, is(equalTo(responseBody)));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        AuthServer.stop();
    }
}
