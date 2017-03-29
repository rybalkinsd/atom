package ru.atom.authserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
/**
 * Created by ikozin on 28.03.17.
 */

public class AuthServer {
    public static void main(String[] args) throws Exception {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                authContextHandler(),
                dataContextHandler()
        });

        Server server = new Server(8080);
        server.setHandler(contexts);

        server.start();
    }

    private static ServletContextHandler authContextHandler() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/auth");
        ServletHolder servlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
                "/*");
        servlet.setInitOrder(0);

        servlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.authserver"
        );
        servlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilter",
                AuthFilter.class.getCanonicalName()
        );

        return context;
    }

    private static ServletContextHandler dataContextHandler() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/data");
        ServletHolder servlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
                "/*");
        servlet.setInitOrder(0);

        servlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.authserver"
        );

        return context;
    }
}
