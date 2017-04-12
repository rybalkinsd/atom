package io.github.rentgen94;

import io.github.rentgen94.server.AuthFilter;
import okhttp3.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.github.rentgen94.WorkWithProperties.getProperties;
import static io.github.rentgen94.WorkWithProperties.getStrBundle;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class AuthTest {
    private String token;

    Server jettyServer;

    @Before
    public void serverInitializer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(Integer.parseInt(getProperties().getProperty("port")));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "io.github.rentgen94.server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        jettyServer.start();
    }

    @Test
    public void register() throws IOException {
        String user = "Test";
        String password = "Qwerty";
        Response response = AuthClient.register(user, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println(body);
        Assert.assertTrue(response.code() == 200
                || body.equals(getStrBundle().getString("already.registered")));
    }

    @Test
    public void login() throws IOException {
        String user = "Test";
        String password = "Qwerty";
        Response response = AuthClient.login(user, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        token = body;
        System.out.println(body);
        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void users() throws IOException {
        Response response = AuthClient.users();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void logout() throws IOException {
        register();
        login();
        Response response = AuthClient.logout(token);
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @After
    public void stopServer() throws Exception {
        this.jettyServer.stop();
    }
}
