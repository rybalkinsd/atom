package network;

import main.ApplicationContext;
import main.MasterServer;
import main.Service;
import main.ServletContext;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 13.06.16.
 */
public class ClientConnectionServer extends Service {
    @NotNull
    private final static Logger log = LogManager.getLogger(MasterServer.class);
    private final int port;
    private Server server;

    public ClientConnectionServer(int port) {
        super("client_connection_service");
        this.port = port;
    }

    public static void main(@NotNull String[] args) throws InterruptedException {
        ClientConnectionServer clientConnectionServer = new ClientConnectionServer(7001);
        clientConnectionServer.start();
        clientConnectionServer.join();
    }

    @Override
    public void run() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        server.setHandler(ServletContext.getInstance());

        // Add a websocket to a specific path spec
        ClientConnectionServlet clientConnectionServlet = new ClientConnectionServlet();
        ServletContext.getInstance().addServlet(new ServletHolder(clientConnectionServlet), "/clientConnection");

        try {
            server.start();
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }

        log.info(getAddress() + " started on port " + port);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                MessageSystem ms = ApplicationContext.instance().get(MessageSystem.class);
                ms.execOneForService(this);
            }
        } catch (InterruptedException e) {
            log.fatal(e.getMessage());
        }
    }

    @Override
    public void interrupt() {
        try {
            server.stop();
        } catch (Exception ignored) {

        } finally {
            super.interrupt();
        }
    }
}
