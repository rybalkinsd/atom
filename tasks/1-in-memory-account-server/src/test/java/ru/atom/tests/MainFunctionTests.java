package ru.atom.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import ru.atom.client.ClientImpl;
import tokens.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by kinetik on 26.03.17.
 */
public class MainFunctionTests {

    Server jettyServer;

    @Before
    public void serverInitializer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "http"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                TokenManager.class.getCanonicalName()
        );
        jettyServer.start();
    }

    @Test
    public void registreOneTest() throws IOException {
        ClientImpl client = new ClientImpl();
        Response response = client.register("admin", "admin");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void registreBadLoginTests() {
        ClientImpl client = new ClientImpl();
        Response responseLongName = client.register("ochenDlinnyImaOver30pointsThereYep", "");
        Response responseNullName = client.register("", "");
        Assert.assertEquals(400, responseLongName.code());
        Assert.assertEquals(400, responseNullName.code());
    }

    @Test
    public void registreMoreTest() {
        ClientImpl client = new ClientImpl();
        Response response = client.register("adminMore", "admin");
        Response responseTwo = client.register("adminMore", "admin");
        Assert.assertEquals(200, response.code());
        Assert.assertEquals(400, responseTwo.code());
    }

    @Test
    public void loginTests() throws IOException {
        ClientImpl client = new ClientImpl();
        Response response = client.register("loginTest", "loginTest");
        Assert.assertEquals(200, response.code());
        Response responseLogin = client.login("loginTest", "loginTest");
        Assert.assertEquals(200, responseLogin.code());
    }

    @Test
    public void loginBadInputTests() {
        ClientImpl clientTest = new ClientImpl();
        Response nullLogin = clientTest.login("", "");
        Response badLogin = clientTest.login("user", "passw");
        Response registration = clientTest.register("badLoginTest", "BadLoginTest");
        Response badPassword = clientTest.login("badLoginTest", "no");
        Assert.assertEquals(400, nullLogin.code());
        Assert.assertEquals(400, badLogin.code());
        Assert.assertEquals(200, registration.code());
        Assert.assertEquals(403, badPassword.code());
    }

    @Test
    public void loginManyTimes() throws IOException {
        ClientImpl client = new ClientImpl();
        Response registrate = client.register("userManyTimes", "pwd");
        Response loginOne = client.login("userManyTimes", "pwd");
        Long token = client.getToken();
        Response loginTwo = client.login("userManyTimes", "pwd");
        Long tokenTwo = client.getToken();
        Assert.assertEquals(200, registrate.code());
        Assert.assertEquals(200, loginOne.code());
        Assert.assertEquals(200, loginTwo.code());
        Assert.assertEquals(token, tokenTwo);
    }

    @Test
    public void logoutTests() {
        ClientImpl client = new ClientImpl();
        Response registrate = client.register("logoutTest", "");
        Response logout = client.logout("logoutTest");
        Response login = client.login("logoutTest", "");
        Response logoutGood = client.logout("logoutTest");
        Response logoutOneMore = client.logout("logoutTest");
        Response logoutEmpty = client.logout("");
        Assert.assertEquals(200, registrate.code());
        Assert.assertEquals(500, logout.code());
        Assert.assertEquals(200, login.code());
        Assert.assertEquals(200, logoutGood.code());
        Assert.assertEquals(500, logoutOneMore.code());
        Assert.assertEquals(500, logoutEmpty.code());
    }

    @Test
    public void getUsersTest() throws IOException {
        ClientImpl client = new ClientImpl();
        client.register("getUserTest1","pwd1");
        client.register("getUserTest2","pwd2");
        client.register("getUserTest3","pwd3");
        client.login("getUserTest1","pwd1");
        client.login("getUserTest2","pwd2");
        client.login("getUserTest3","pwd3");
        Response onlinesResp = client.getUsers("getUserTest3");
        String onlines = onlinesResp.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, ArrayList<String>> users = objectMapper.readValue(onlines, HashMap.class);
        Assert.assertTrue(users.get("Users").contains("getUserTest1"));
        Assert.assertTrue(users.get("Users").contains("getUserTest2"));
        Assert.assertTrue(users.get("Users").contains("getUserTest3"));
        client.logout("getUserTest3");
        client.login("getUserTest2","pwd2");
        Response onlinesRespTwo = client.getUsers("getUserTest3");
        String onlinesTwo = onlinesRespTwo.body().string();
        HashMap<String, ArrayList<String>> usersTwo = objectMapper.readValue(onlinesTwo, HashMap.class);
        Assert.assertTrue(usersTwo.get("Users").contains("getUserTest1"));
        Assert.assertTrue(usersTwo.get("Users").contains("getUserTest2"));
        Assert.assertFalse(usersTwo.get("Users").contains("getUserTest3"));
    }

    @After
    public void stopServer() throws Exception {
        this.jettyServer.stop();
    }
}