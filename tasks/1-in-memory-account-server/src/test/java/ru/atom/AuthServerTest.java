package ru.atom;

import okhttp3.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by ilysk on 28.03.17.
 */
@FixMethodOrder(MethodSorters.JVM)
public class AuthServerTest {
    private static String user_1 = "tester1";
    private static String password_1 = "qwe123";
    private static String user_2 = "user";
    private static String password_2 = "qwe111";
    private static String wrongToken = "123123";
    private Server jettyServer;

    @Before
    public void serverInit() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom"
        );

        jettyServer.start();
    }

    @Test
    public void viewOnlineWhenEmpty() throws IOException {
        Response response = AuthServerClient.viewOnline();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        Assert.assertTrue(response.code() == 200 && body.equals("{\"users\":[]}"));
    }

    @Test
    public void loginWithoutRegistration() throws IOException {
        Response response = AuthServerClient.login(user_1, password_1);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 400 && body.equals("You aren't registered."));
    }

    @Test
    public void logoutWithoutToken() throws IOException {
        Response response = AuthServerClient.logout(null);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 401 && body.equals("You aren't logined."));
    }

    @Test
    public void logoutWithWrongToken() throws IOException {
        Response response = AuthServerClient.logout(wrongToken);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 401 && body.equals("You aren't logined."));
    }

    @Test
    public void registrationUserOne() throws IOException {
        Response response = AuthServerClient.register(user_1, password_1);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void registrationUserOneAgain() throws IOException {
        Response response = AuthServerClient.register(user_1, password_1);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 400 && body.equals("User with this name has already registered."));
    }

    @Test
    public void loginUserOneAndAgainUserOne() throws IOException {
        Response response1 = AuthServerClient.login(user_1, password_1);
        System.out.println("[" + response1 + "]");
        String body1 = response1.body().string();
        System.out.println();
        Assert.assertTrue(response1.code() == 200 && body1.length() != 0);
        Response response2 = AuthServerClient.login(user_1, password_1);
        System.out.println("[" + response2 + "]");
        String body2 = response2.body().string();
        System.out.println();
        String token1 = body1;
        System.out.println();
        Assert.assertTrue(response2.code() == 200 && body2.equals(token1));
    }

    @Test
    public void registrationUserTwo() throws IOException {
        Response response = AuthServerClient.register(user_2, password_2);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void loginUserTwo() throws IOException {
        Response response = AuthServerClient.login(user_2, password_2);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 && body.length() != 0);
    }

    @Test
    public void viewOnlineTwoUsers() throws IOException {
        Response response = AuthServerClient.viewOnline();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        Assert.assertTrue(response.code() == 200
                & (body.equals("{\"users\":[\"" + user_1 + "\",\"" + user_2 + "\"]}")
                || body.equals("{\"users\":[\"" + user_2 + "\",\"" + user_1 + "\"]}")));
    }

    @Test
    public void logoutUserOne() throws IOException {
        // JUnit не поддерживает передачу значений между тестами в одном классе.
        String token1 = AuthServerClient.login(user_1, password_1).body().string();
        Response response = AuthServerClient.logout(token1);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 && body.equals("Succeed logout."));
    }

    @Test
    public void viewOnlineUserTwo() throws IOException {
        Response response = AuthServerClient.viewOnline();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 && body.equals("{\"users\":[\"" + user_2 + "\"]}"));
    }

    @Test
    public void logoutUserTwoAndAgainUserTwo() throws IOException {
        String token2 = AuthServerClient.login(user_2, password_2).body().string();
        Response response = AuthServerClient.logout(token2);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 && body.equals("Succeed logout."));
    }

    @Test
    public void viewOnlineEmptyAgain() throws IOException {
        Response response = AuthServerClient.viewOnline();
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 && body.equals("{\"users\":[]}"));
    }

    @After
    public void stopServer() throws Exception {
        jettyServer.stop();
    }
}