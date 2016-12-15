package network;

import main.ApplicationContext;
import main.MasterServer;
import main.Service;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import messageSystem.messages.MoveMsg;
import messageSystem.messages.ReplicateMsg;
import messageSystem.messages.SplitMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by apomosov on 13.06.16.
 */
public class ClientConnectionServer extends Service {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  private final int port;

  public ClientConnectionServer() {
    super("client_connection_service");
    FileInputStream fis;
    Properties property = new Properties();
    int por=0;
    try {
      fis = new FileInputStream("src/main/resources/config.properties");
      property.load(fis);
      por = Integer.parseInt(property.getProperty("clientConnectionPort"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.port=por;
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
      //execute all messages from queue
      ApplicationContext.instance().get(MessageSystem.class).execForService(this);
    }
  }

  public static void main(@NotNull String[] args) throws InterruptedException {
    ClientConnectionServer clientConnectionServer = new ClientConnectionServer();
    clientConnectionServer.start();
    clientConnectionServer.join();
  }
}
