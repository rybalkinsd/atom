package ru.atom.mm.server;

/**
 * Created by BBPax on 17.04.17.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.dbhackaton.dao.Database;

/**
 * Created by sergey on 3/15/17.
 */
public class MatchMakerServer {
    private static final Logger log = LogManager.getLogger(MatchMakerServer.class);

    private static Server jettyServer;

    public static void main(String[] args) throws Exception {
        startUp();
    }

    public static void startUp() throws Exception {
        Database.setUp();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                createMatchMakerContext(),
        });

        jettyServer = new Server(8081);
        jettyServer.setHandler(contexts);
        try {
            jettyServer.start();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private static ServletContextHandler createMatchMakerContext() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/mm/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "ru.atom.mm.server"
        );

        return context;
    }

    public static void shutdown() throws Exception {
        jettyServer.stop();
    }
}
