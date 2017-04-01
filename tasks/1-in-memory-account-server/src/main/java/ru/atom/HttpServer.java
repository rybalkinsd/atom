package ru.atom;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

//import ru.atom.AuthFilter;

public class HttpServer {
    public static Server newServer() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context
            .addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "ru.atom");

        /*
         * jerseyServlet.setInitParameter(
         * "com.sun.jersey.spi.container.ContainerRequestFilters",
         * AuthService.class.getCanonicalName() );
         */

        return jettyServer;
    }

    public static void main(String[] args) throws Exception {
        newServer().start();
    }
}
