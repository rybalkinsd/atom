package io.github.rentgen94.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static io.github.rentgen94.WorkWithProperties.getProperties;

/**
 * Create server with in-memory authentications
 * @author Western-Co
 */
public class AuthServer {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        Server jettyServer = new Server(Integer.parseInt(getProperties().getProperty("port")));
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
}
