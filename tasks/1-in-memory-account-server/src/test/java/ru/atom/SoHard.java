package ru.atom;


import okhttp3.Response;

import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class SoHard {
    private String name0 = "user0";
    private String password0 = "PaSsWoRd0";
    private String name1 = "user1";
    private String password1 = "PaSsWoRd1";
    private String name2 = "user2";
    private String password2 = "PaSsWoRd2";

    @Test
    public void registerTest1() throws Exception {
        Response response = Client.register("u", "password");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Too short name, sorry :("));
    }

    @Test
    public void registerTest2() throws Exception {
        Response response = Client.register("veryVeryLongUserNameOneMoreTimeVeryVeryLongUserName", "password");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Too long name, sorry :("));
    }

    @Test
    public void registerTest3() throws Exception {
        Response response = Client.register("123user", "password");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        String resp = "Login must stats with a letter and then contains " +
                      "only letters, numbers or '_'.";
        assertTrue(body.equals(resp));
    }

    @Test
    public void registerTest4() throws Exception {
        Response response = Client.register("GitleR", "password");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Gitler not allowed, sorry :("));
    }

    @Test
    public void registerTest5() throws Exception {
        Response response = Client.register("user", "pass");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Too short password"));
    }

    @Test
    public void registerTest6() throws Exception {
        Response response = Client.register("user", "longPassword1234567890password1234567890");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Too long password"));
    }

    @Test
    public void registerTest7() throws Exception {
        Response response = Client.register("user", "bad_password@");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Password must be only from letters and / or numbers"));

        response = Client.register("user", "{bad_password}");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Password must be only from letters and / or numbers"));
    }

    @Test
    public void registerTest8() throws Exception {
        Response response = Client.register(name1, password1);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 200);
        assertTrue(body.equals("You've successfully registed"));

        response = Client.register(name2, password2);
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 200);
        assertTrue(body.equals("You've successfully registed"));

        response = Client.register(name1, password1 + "1");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Already registed"));

        response = Client.register(name2, password1 + "2");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 400);
        assertTrue(body.equals("Already registed"));
    }

    @Test
    public void loginTest1() throws Exception {
        Response response = Client.login(name0, password0);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 403);
        assertTrue(body.equals("Bad name and/or password"));
    }

    @Test
    public void loginTest2() throws Exception {
        Response response = Client.login(name1, password0);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 403);
        assertTrue(body.equals("Bad name and/or password"));
    }

    @Test
    public void testLoginAndLogout() throws Exception {
        Response response = Client.login(name2, password2);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 200);

        response = Client.logout(body);
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println(body);
        assertTrue(response.code() == 200);
        assertTrue(body.equals("You've successfully logged out"));
    }
}
