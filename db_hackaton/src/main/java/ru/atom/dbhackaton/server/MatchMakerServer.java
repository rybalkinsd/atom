package ru.atom.dbhackaton.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.server.mm.MatchMaker;
import ru.atom.dbhackaton.server.resources.MatchMakerResource;
import ru.atom.dbhackaton.server.storages.Database;


public class MatchMakerServer {
    private static Server jettyServer = new Server(8090);

    public static void start() throws Exception {
        Database.setUp();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{
                createChatContext(),
                createResourceContext()
        });

        jettyServer = new Server(8090);
        jettyServer.setHandler(contexts);

        jettyServer.start();

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }

    public static void stop() throws Exception {
        jettyServer.stop();
    }

    public static void main(String[] args) throws Exception {
        start();
    }

    private static ServletContextHandler createChatContext() {
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

        String serverRoot = MatchMakerResource.class.getResource("/static").toString();
        handler.setResourceBase(serverRoot);
        context.setHandler(handler);
        return context;
    }
}

