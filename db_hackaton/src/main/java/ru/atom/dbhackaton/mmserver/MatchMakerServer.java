package ru.atom.dbhackaton.mmserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.server.AuthFilter;
/**
 * Created by ikozin on 17.04.17.
 */

public class MatchMakerServer {
    public static void main(String[] args) throws Exception {
        Database.setUp();

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm/*");

        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.dbhackaton.mmserver"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );

        jettyServer.start();

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }
}
