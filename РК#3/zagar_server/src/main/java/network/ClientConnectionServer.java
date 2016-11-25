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
import utils.PropertiesReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by apomosov on 13.06.16.
 */
public class ClientConnectionServer extends Service {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  private final int port;

  public ClientConnectionServer(PropertiesReader preader) throws IOException {
    super("client_connection_service");
    port = preader.getIntProperty("clientConnectionPort");
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

    while (true) {
      ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
  }

  public static void main(@NotNull String[] args) throws Exception {
    ClientConnectionServer clientConnectionServer = new ClientConnectionServer(new PropertiesReader("src/main/resources/config.properties"));
    clientConnectionServer.start();
    clientConnectionServer.join();
  }
}
