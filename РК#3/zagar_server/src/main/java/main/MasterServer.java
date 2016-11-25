package main;

import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import ticker.Ticker;
import utils.IDGenerator;
import utils.PropertiesReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");

    PropertiesReader preader;

    try {
      preader = new PropertiesReader("src/main/resources/config.properties");
    } catch (IOException e) {
      log.error("Properties file unavailable",e);
      return;
    }

    MessageSystem messageSystem;

    try {
      Class<?> tempClass = Class.forName(preader.getStringProperty("matchMaker"));
      ApplicationContext.instance().put(MatchMaker.class, (MatchMaker) tempClass.newInstance());
      tempClass = Class.forName(preader.getStringProperty("replicator"));
      ApplicationContext.instance().put(Replicator.class, (Replicator) tempClass.newInstance());
      tempClass = Class.forName(preader.getStringProperty("clientConnections"));
      ApplicationContext.instance().put(ClientConnections.class, (ClientConnections) tempClass.newInstance());
      tempClass = Class.forName(preader.getStringProperty("idGenerator"));
      ApplicationContext.instance().put(IDGenerator.class, (IDGenerator) tempClass.newInstance());
      tempClass = Class.forName(preader.getStringProperty("messageSystem"));
      messageSystem = (MessageSystem) tempClass.newInstance();
      ApplicationContext.instance().put(MessageSystem.class, messageSystem);
    }
    catch (Exception e) {
      log.error("Failed to add an application context class",e);
      return;
    }

    List<String> services = preader.getListProperty("services");
    for (String service:services)
    {
      try {
        Class<?> serviceClass = Class.forName(service);
        messageSystem.registerService(serviceClass, (Service) serviceClass.getDeclaredConstructor(String.class).newInstance("src/main/resources/config.properties"));
      }
      catch (Exception e) {
        log.error("Failed to add service: "+ service,e);
      }
    }

    messageSystem.getServices().forEach(Service::start);

    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
