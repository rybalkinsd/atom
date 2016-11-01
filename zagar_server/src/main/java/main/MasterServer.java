package main;

import accountserver.AccountServer;
import matchmaker.MatchMaker;
import matchmaker.MatchMakerImpl;
import network.ClientConnectionServer;
import mechanics.Mechanics;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.FullStateReplicator;
import replication.Replicator;
import ticker.Ticker;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  @NotNull
  private final List<Service> services = new ArrayList<>();


  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
    ApplicationContext.instance().put(MatchMaker.class, new MatchMakerImpl());
    ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
    ApplicationContext.instance().put(Replicator.class, new FullStateReplicator());
    ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());
    Ticker ticker = new Ticker(1);
    ApplicationContext.instance().put(Ticker.class, ticker);

    Mechanics mechanics = new Mechanics();
    ticker.registerTickable(mechanics);

    services.add(new AccountServer(8080));
    services.add(new ClientConnectionServer(7000));
    services.add(mechanics);
    services.add(ticker);
    services.forEach(Service::start);

    for (Service service : services) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
