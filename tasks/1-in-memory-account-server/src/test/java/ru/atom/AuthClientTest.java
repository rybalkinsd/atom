package ru.atom;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class AuthClientTest {

    @Before
    public void init() throws IOException {
//        try {
//            AuthServer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        AuthClient.register("lubapoplavkova1", "12345678910");
        AuthClient.register("lubapoplavkova2", "12345678910");
        Response response = AuthClient.login("lubapoplavkova2", "12345678910");
        String body = response.body().string();
        System.out.println();
        AuthClient.setToken(body);
    }

    @Test
    public void registerBad() throws IOException {
        Response response = AuthClient.register("luba", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));

        response = AuthClient.register("lubapoplavkova", "12345");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат пароля!"));

        response = AuthClient.register("lubapoplavkova1", "123000011145");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Пользователь с таким именем уже зарегистрирован"));
    }

    @Test
    public void registerGood() throws IOException {
        Response response = AuthClient.register("lubapoplavkova3", "123005511145");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Пользователь с таким именем уже зарегистрирован"));
    }


    @Test
    public void loginBad() throws IOException {
        Response response = AuthClient.login("luba", "12345");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));

        response = AuthClient.login("lubapoplavkova", "12345");
        System.out.println("[" + response + "]");
        body = response.body().string();
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат пароля!"));
    }


    @Test
    public void loginGood() throws IOException {
        Response response = AuthClient.login("lubapoplavkova1", "12345678910");
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        AuthClient.setToken(body);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void logout() throws IOException {
        String token = AuthClient.getToken();
        Response response = AuthClient.logout(token);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        AuthClient.setToken(body);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void getData() throws IOException {
        Response response = AuthClient.getData();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200);
    }

}
