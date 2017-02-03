package ru.atom;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by sergei-r on 07.01.17.
 */
public class GameServer {
    private final static Logger log = LogManager.getLogger(GameServer.class);

    private static final int PORT = 8090;
    private Server server;

    private void start() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder holderEvents = new ServletHolder("ws-events", new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ClientConnectionHandler.class);
            }
        });
        context.addServlet(holderEvents, "/events/*");

        try {
            server.start();
            server.dump(System.err);
            server.join();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        new GameServer().start();
    }

    private GameServer() { }
}
