package ru.atom.websocket.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EventServer {
    private static Server eventServer;

    public static void main(String[] args) {
        startEventServer();
    }

    public static void startEventServer() {
        eventServer = new Server();
        ServerConnector connector = new ServerConnector(eventServer);
        connector.setPort(8090);
        eventServer.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        eventServer.setHandler(context);

        // Add a websocket to a specific path spec
        ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
        context.addServlet(holderEvents, "/events/*");

        try {
            eventServer.start();
            eventServer.dump(System.err);
            eventServer.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public static void stopEventServer() {
        try {
            eventServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
