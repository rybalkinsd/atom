package network;

import main.ApplicationContext;
import main.Service;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by User on 27.11.2016.
 */
public class ClientConnectionServer extends Service {
    @NotNull
    private static ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private final static Logger log = LogManager.getLogger(ClientConnectionServer.class);
    private final int port;

    public ClientConnectionServer(int port) {
        super("client_connection_serer");
        this.port = port;
    }

    @Override
    public void run() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        server.setHandler(context);

        ClientConnectionServlet clientConnectionServlet = new ClientConnectionServlet();
        context.addServlet(new ServletHolder(clientConnectionServlet), "/network");

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(getAddress() + " started on port " + port);

        while (true) {
            ApplicationContext.get(MessageSystem.class).executeForService(this);
        }
    }

    public static void main(@NotNull String[] args) throws InterruptedException {
        ClientConnectionServer clientConnectionServer = new ClientConnectionServer(7000);
        clientConnectionServer.start();
        clientConnectionServer.join();
    }
}
