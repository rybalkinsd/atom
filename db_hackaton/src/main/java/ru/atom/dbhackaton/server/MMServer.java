package ru.atom.dbhackaton.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.server.Dao.Database;

/**
 * Created by pavel on 17.04.17.
 */
public class MMServer {
    public static void main(String[] args) throws Exception {
        Database.setUp();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                createMMContext(),
                createResourceContext()
        });

        Server jettyServer = new Server(8081);
        jettyServer.setHandler(contexts);

        jettyServer.start();
    }

    private static ServletContextHandler createMMContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm/*");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerResponseFilters",
                CrossBrowserFilter.class.getCanonicalName()
        );

        return context;
    }

    private static ContextHandler createResourceContext() {
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        ResourceHandler handler = new ResourceHandler();
        handler.setWelcomeFiles(new String[]{"index.html"});

        String serverRoot = AuthServer.class.getResource("/static").toString();
        handler.setResourceBase(serverRoot);
        context.setHandler(handler);
        return context;
    }
}
