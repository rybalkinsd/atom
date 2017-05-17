package ru.atom.dbhackaton.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.dao.Database;

import static ru.atom.WorkWithProperties.getProperties;

public class MainServerWithAuthAndMm {
    private static Server jettyServer;
    public static String PATH_TO_FRONTEND;

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            PATH_TO_FRONTEND = args[0];
        } else {
            PATH_TO_FRONTEND = getProperties().getProperty("PATH_TO_FRONTEND");
        }
        startUp();
    }

    public static void startUp() throws Exception {
        Database.setUp();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                createAuthContext(),
                createResourceContext(),
                createMatchMakerContext()
        });

        jettyServer = new Server(Integer.valueOf(getProperties().getProperty("port")));
        jettyServer.setHandler(contexts);

        jettyServer.start();
    }

    private static ServletContextHandler createAuthContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/auth/*");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.services.auth"
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

        String serverRoot = MainServerWithAuthAndMm.class.getResource("/static").toString();
        handler.setResourceBase(serverRoot);
        context.setHandler(handler);
        return context;
    }

    private static ServletContextHandler createMatchMakerContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm/*");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.services.mm"
        );
        return context;
    }

    public static void shutdown() throws Exception {
        jettyServer.stop();
    }
}