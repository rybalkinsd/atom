package ru.atom.bombergirl.gameserver;

/**
 * Created by dmitriy on 01.05.17.
 */
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.atom.bombergirl.mmserver.MatchMakerServer;

public class EventServer {
    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8085);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {
                //createResourceContext(),
                context
        });
        server.setHandler(contexts);

        // Add a websocket to a specific path spec
        ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
        context.addServlet(holderEvents, "/gs/*");

        try {
            server.start();
            server.dump(System.err);
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    private static ContextHandler createResourceContext() {
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        ResourceHandler handler = new ResourceHandler();
        handler.setWelcomeFiles(new String[]{"index.html"});

        String serverRoot = EventServer.class.getResource("bombergirl/frontend/src/main/webapp/static").toString();
        handler.setResourceBase(serverRoot);
        context.setHandler(handler);
        return context;
    }
}
