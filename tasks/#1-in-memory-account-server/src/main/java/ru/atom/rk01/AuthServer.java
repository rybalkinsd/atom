package ru.atom.rk01;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by dmbragin on 3/28/17.
 */
public class AuthServer {
    public static void main(String[] args) throws Exception {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                createAuthServerContext(),
        });

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(contexts);

        jettyServer.start();
    }

    private static ServletContextHandler createAuthServerContext() {
        ServletContextHandler context = new ServletContextHandler();
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.rk01"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        return context;
    }
}
