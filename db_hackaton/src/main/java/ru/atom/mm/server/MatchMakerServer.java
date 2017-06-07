package ru.atom.mm.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.mm.server.matchmaker.MatchMaker;
/*import sun.net.httpserver.AuthFilter;*/

/**
 * Created by Fella on 18.04.2017.
 */
public class MatchMakerServer {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/mm");

        Server jettyServer = new Server(8081);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.mm.server"
        );

        /*jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthFilter.class.getCanonicalName()
        );*/ //ПОДПРАВИТЬ ФИЛЬТР АВТОРИЗАЦИИ

        jettyServer.start();

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
    }
}
