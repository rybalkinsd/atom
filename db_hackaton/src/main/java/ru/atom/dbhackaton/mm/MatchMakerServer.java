package ru.atom.dbhackaton.mm;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.mm.MatchMaker;

/**
 * Created by ilysk on 16.04.17.
 */
public class MatchMakerServer {
    private static Server jettyServer;

    public static void start(boolean isTest) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm");

        jettyServer = new Server(8282);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.mm"
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

    public static void finish() {
        try {
            jettyServer.destroy();
        } catch (Exception e) {
            //nothing
        }
    }
}
