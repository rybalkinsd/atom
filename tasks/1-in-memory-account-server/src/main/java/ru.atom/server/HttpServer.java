package ru.atom.server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by salvador on 01.04.17.
 */
public class HttpServer {
    private static Server jettyServer = new Server(8080);

    public static void ServStart() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

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
                Filther.class.getCanonicalName()
        );

        jettyServer.start();
    }

    public static void ServStop() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        ServStart();
    }

}
