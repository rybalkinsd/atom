package ru.atom;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by pavel on 23.03.17.
 */
public class HttpServerRk {

    private static Server jettyServer;

    public static void startServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*"
        );

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilterRk.class.getCanonicalName()
        );

        jettyServer.start();
    }

    public static void stopServer() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        startServer();
    }
}
