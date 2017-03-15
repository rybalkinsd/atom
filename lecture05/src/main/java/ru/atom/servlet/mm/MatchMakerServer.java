package ru.atom.servlet.mm;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.atom.thread.mm.MatchMaker;

/**
 * Created by sergey on 3/15/17.
 */
public class MatchMakerServer {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        context.addServlet(ConnectionHandler.class, "/connect");
        context.addServlet(GamesView.class, "/games");
        jettyServer.start();

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }
}
