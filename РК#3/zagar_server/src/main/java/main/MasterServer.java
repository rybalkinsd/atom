package main;

import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import utils.Configurations;
import utils.IDGenerator;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  private boolean ready;

  public void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");

    ready=false;

    MessageSystem messageSystem;

    try {
      Class<?> tempClass = Class.forName(Configurations.getStringProperty("matchMaker"));
      ApplicationContext.instance().put(MatchMaker.class, (MatchMaker) tempClass.newInstance());
      tempClass = Class.forName(Configurations.getStringProperty("replicator"));
      ApplicationContext.instance().put(Replicator.class, (Replicator) tempClass.newInstance());
      tempClass = Class.forName(Configurations.getStringProperty("clientConnections"));
      ApplicationContext.instance().put(ClientConnections.class, (ClientConnections) tempClass.newInstance());
      tempClass = Class.forName(Configurations.getStringProperty("idGenerator"));
      ApplicationContext.instance().put(IDGenerator.class, (IDGenerator) tempClass.newInstance());
      tempClass = Class.forName(Configurations.getStringProperty("messageSystem"));
      messageSystem = (MessageSystem) tempClass.newInstance();
      ApplicationContext.instance().put(MessageSystem.class, messageSystem);
    }
    catch (Exception e) {
      log.error("Failed to add an application context class",e);
      return;
    }

    List<String> services = Configurations.getListProperty("services");
    for (String service:services)
    {
      try {
        Class<?> serviceClass = Class.forName(service);
        messageSystem.registerService(serviceClass, (Service) serviceClass.newInstance());
      }
      catch (Exception e) {
        log.error("Failed to add service: "+ service,e);
      }
    }

    messageSystem.getServices().forEach(Service::start);

    ready=true;

    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public boolean isReady(){return ready;}

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
