package ru.atom.dbhackaton.mmserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.server.AuthFilter;
import ru.atom.dbhackaton.server.AuthServer;
import ru.atom.dbhackaton.server.CrossBrowserFilter;

/**
 * Created by ikozin on 17.04.17.
 */

public class MatchMakerServer {
    public static void main(String[] args) throws Exception {
        Database.setUp();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                createMatchMakerContext(),
                createResourceContext()
        });

        Server jettyServer = new Server(8090);
        jettyServer.setHandler(contexts);

        jettyServer.start();

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }

    private static ServletContextHandler createMatchMakerContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm/*");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.mmserver"
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

        String serverRoot = MatchMakerServer.class.getResource("/static").toString();
        handler.setResourceBase(serverRoot);
        context.setHandler(handler);
        return context;
    }
}
