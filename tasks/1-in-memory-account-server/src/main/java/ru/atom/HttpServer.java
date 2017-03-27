package ru.atom;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by pavel on 23.03.17.
 */
public class HttpServer {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jersyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*"
        );

        jersyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom"
        );

        jettyServer.start();
    }
}
