package ru.atom.rk1.server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class AuthServer {
    private static Server jettyServer;

    static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/*");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.rk1.server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        jettyServer.start();

        // всех зарегистрированых пользователей забываем
        AuthResource.registered.clear();

        // всех авторизоавнных пользователей разлогиниваем
        TokenStorage.clear();
        TokenStorage.init();
    }

    static void stop() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        start();
    }
}
