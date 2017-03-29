package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by Vlad on 26.03.2017.
 */
public class AuthServer {
    private static final Logger log = LogManager.getLogger(AuthServer.class);

    private static Server jettyServer;

    public static void startServer() throws Exception {
        int port = 8080;

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        log.info("Listening post: " + port);
        jettyServer.start();
    }

    public static void stopServer() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        startServer();
    }
}
