package network;

import main.ServletContext;
import main.MasterServer;
import main.Service;
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

  public ClientConnectionServer(int port) {
    super("client_connection_service");
    this.port = port;
  }

  @Override
  public void run() {
    Server server = new Server();
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
      e.printStackTrace();
    }
    log.info(getName() + " started on port " + port);
  }

  public static void main(@NotNull String[] args) throws InterruptedException {
    ClientConnectionServer clientConnectionServer = new ClientConnectionServer(7001);
    clientConnectionServer.start();
    clientConnectionServer.join();
  }
}
