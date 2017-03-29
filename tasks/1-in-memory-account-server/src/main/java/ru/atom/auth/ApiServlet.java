package ru.atom.auth;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.auth.AuthFilter;

/**
 * Created by vladfedorenko on 24.10.16.
 */

public class ApiServlet {
    public static void start(boolean isTest) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.auth"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        if (isTest) {
            jettyServer.start();
        } else {
            try {
                jettyServer.start();
                jettyServer.join();
            } finally {
                jettyServer.destroy();
            }
        }
    }

    private ApiServlet() {

    }
}
