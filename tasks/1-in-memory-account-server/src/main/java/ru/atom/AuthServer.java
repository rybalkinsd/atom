package ru.atom;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.Random;

public class AuthServer {
    public static TokenStorage tokens = new TokenStorage();
    public static UserStorage users = new UserStorage();
    public static Random random = new Random();

    private static Server jettyServer;

    public static void startServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        // http://localhost:8080/auth/register
        context.addServlet(RegisterServlet.class, "/auth/register");

        // http://localhost:8080/auth/login
        context.addServlet(LoginServlet.class, "/auth/login");

        // http://localhost:8080/auth/logout
        context.addServlet(LogoutServlet.class, "/auth/logout");

        // http://localhost:8080/data/users
        context.addServlet(DataServlet.class, "/data/users");
        jettyServer.start();
    }

    public static void stopServer() throws Exception {
        jettyServer.stop();
        tokens = null;
        users = null;
    }

    public static void main(String[] args) throws Exception {
        startServer();
    }
}
