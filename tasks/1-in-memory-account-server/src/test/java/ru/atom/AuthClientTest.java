package ru.atom;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by mkai on 3/26/17.
 */
public class AuthClientTest {

    @Before
    public void init() throws IOException {
        AuthClient.register("lubapoplavkova1", "12345678910");
        AuthClient.register("lubapoplavkova2", "12345678910");
        AuthClient.login("lubapoplavkova2", "12345678910");
    }

    @Test
    public void registerBad1() throws IOException {
        Response response = AuthClient.register("luba", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));
    }

    @Test
    public void registerBad2() throws IOException {
        Response response = AuthClient.register("lubapoplavkova", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат пароля!"));
    }

    @Test
    public void registerBad3() throws IOException {
        Response response = AuthClient.register("lubapoplavkova1", "123000011145");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Пользователь с таким именем уже зарегистрирован"));
    }

    @Test
    public void registerGood1() throws IOException {
        Response response = AuthClient.register("lubapoplavkova3", "123005511145");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Пользователь с таким именем уже зарегистрирован"));
    }


    @Test
    public void loginBad1() throws IOException {
        Response response = AuthClient.login("luba", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));
    }

    @Test
    public void loginBad2() throws IOException {
        Response response = AuthClient.login("lubapoplavkova", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат пароля!"));
    }

    @Test
    public void loginGood1() throws IOException {
        Response response = AuthClient.login("lubapoplavkova1", "12345678910");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        AuthClient.setToken(body);
        Assert.assertTrue(response.code() == 200);
    }

}
