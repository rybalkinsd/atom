package network;

import main.ApplicationContext;
import main.MasterServer;
import main.Service;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import utils.Configurations;

import java.io.IOException;

/**
 * Created by apomosov on 13.06.16.
 */
public class ClientConnectionServer extends Service {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  private final int port;

  public ClientConnectionServer(){
    super("client_connection_service");
    port = Configurations.getIntProperty("clientConnectionPort");
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

    log.info(getAddress() + " started on port " + port);

    while (!Thread.interrupted()) {
      ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
  }

  public static void main(@NotNull String[] args) throws Exception {
    ClientConnectionServer clientConnectionServer = new ClientConnectionServer();
    clientConnectionServer.start();
    clientConnectionServer.join();
  }
}
