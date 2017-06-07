package ru.atom;

import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;


public class AuthClientTest {
    private static final Logger logger = LogManager.getLogger(AuthClientTest.class);

    @Before
    public void init() throws IOException {
        try {
            AuthServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mainTest() throws IOException {
        HashMap<String, String> logined = new HashMap<>();


        for (int i = 0; i < 10; i++) {
            Response response = AuthClient.register("boom_user" + i, "12345678910" + i);
            logger.info("register response: " + System.lineSeparator()
                    + response.toString());
            String body = response.body().string();
            logger.info("body: " + System.lineSeparator()
                    + body);
            System.out.println("***********************************************");
            Assert.assertTrue(response.code() == 200 || body.equals("Пользователь с таким именем уже зарегистрирован"));
        }

        for (int i = 5; i < 9; i++) {
            Response response = AuthClient.register("boom_user" + i, "12345678910" + i);
            logger.info("register response: " + System.lineSeparator()
                    + response.toString());
            String body = response.body().string();
            logger.info("body: " + System.lineSeparator()
                    + body);
            System.out.println("***********************************************");
            Assert.assertTrue(body.equals("Пользователь с таким именем уже зарегистрирован"));
        }

        for (int i = 2; i < 7; i++) {
            Response response = AuthClient.login("boom_user" + i, "12345678910" + i);
            String body = response.body().string();
            logined.put("boom_user" + i, body);
            Assert.assertTrue(response.code() == 200);
        }
        for (int i = 2; i < 5; i++) {
            String token = logined.get("boom_user" + i);
            Response response = AuthClient.logout(token);
            logger.info("logout response: " + System.lineSeparator()
                    + response.toString());

            System.out.println("***********************************************");
            System.out.println();

            Assert.assertTrue(response.code() == 200);
            logined.remove("boom_user" + i);
            response = AuthClient.logout(token);
            logger.info("logout response: " + System.lineSeparator()
                    + response.toString());
            System.out.println("***********************************************");
            System.out.println();

            Assert.assertTrue(response.code() == 401);
        }
        {
            Response response = AuthClient.getData();
            logger.info("logout getData: " + System.lineSeparator()
                    + response.toString());
            String body = response.body().string();
            logger.info("body: " + System.lineSeparator()
                    + body);
            Assert.assertTrue(response.code() == 200);
        }
    }

    @Test
    public void registerBad() throws IOException {
        Response response = AuthClient.register("luba", "12345");
        logger.info("register response: " + System.lineSeparator()
                + response.toString());
        System.out.println("[" + response + "]");
        String body = response.body().string();
        logger.info("body: " + System.lineSeparator()
                + body);
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));

        response = AuthClient.register("lubapoplavkova", "12345");
        System.out.println("[" + response + "]");
        body = response.body().string();
        logger.info("register response: " + System.lineSeparator()
                + response.toString());
        logger.info("body: " + System.lineSeparator()
                + body);
        System.out.println();

        Assert.assertTrue(body.equals("Неверный формат пароля!"));

        AuthClient.register("lubapoplavkova1", "123000011145");
        response = AuthClient.register("lubapoplavkova1", "123000011145");
        System.out.println("[" + response + "]");
        body = response.body().string();
        logger.info("register response: " + System.lineSeparator()
                + response.toString());
        logger.info("body: " + System.lineSeparator()
                + body);
        System.out.println();

        Assert.assertTrue(body.equals("Пользователь с таким именем уже зарегистрирован"));
    }

    @Test
    public void registerGood() throws IOException {
        Response response = AuthClient.register("lubapoplavkova3", "123005511145");
        logger.info("register response: " + System.lineSeparator()
                + response.toString());
        String body = response.body().string();
        logger.info("body: " + System.lineSeparator()
                + body);
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Пользователь с таким именем уже зарегистрирован"));
    }


    @Test
    public void loginBad() throws IOException {
        Response response = AuthClient.login("luba", "12345");
        logger.info("login response: " + System.lineSeparator()
                + response.toString());
        String body = response.body().string();

        Assert.assertTrue(body.equals("Неверный формат имени пользователя!"));

        response = AuthClient.login("lubapoplavkova", "12345");
        logger.info("login response: " + System.lineSeparator()
                + response.toString());
        body = response.body().string();
        logger.info("body: " + System.lineSeparator()
                + body);
        System.out.println();
        Assert.assertTrue(body.equals("Неверный формат пароля!"));
    }

    @After
    public void stopServer() throws Exception {
        AuthServer.stop();
    }

}
