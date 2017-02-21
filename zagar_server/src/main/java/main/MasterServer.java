package main;

import accountserver.api.TokenContainer;
import leaderboard.LdrBrdMaker;
import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;

import ticker.Ticker;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.io.FileInputStream;
/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);
  private static int accountServerPort;
  private static int clientConnectionPort;
  private static String replicatorName;
  private static String matchmakerName;
  private static String leaderboardName;


  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
    Properties props = new Properties();
    try {
      props.load(new FileInputStream(new File("src/main/resources/init.properties")));
      accountServerPort = Integer.valueOf(props.getProperty("accountServerPort"));
      clientConnectionPort = Integer.valueOf(props.getProperty("clientConnectionPort"));
      replicatorName = props.getProperty("replicator");
      matchmakerName = props.getProperty("matchMaker");
      leaderboardName = props.getProperty("leaderboardMaker");
      ApplicationContext.instance().put(Replicator.class, Class.forName(replicatorName).newInstance());
      ApplicationContext.instance().put(MatchMaker.class, Class.forName(matchmakerName).newInstance());
      ApplicationContext.instance().put(LdrBrdMaker.class, Class.forName(leaderboardName).newInstance());
      ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
      ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());
      ApplicationContext.instance().put(Ticker.class, new Ticker(1));

      try {
        TokenContainer.init();
      }
      catch (Exception e){
        log.info("DB problems");
        log.error(e);
      }
      String[] services = props.getProperty("services").split(",");

      MessageSystem messageSystem = new MessageSystem();
      ApplicationContext.instance().put(MessageSystem.class, messageSystem);

      Constructor constrAccSer = Class.forName(services[1]).getConstructor(Integer.class);
      Constructor constrCliConSer = Class.forName(services[2]).getConstructor(Integer.class);

      messageSystem.registerService(Class.forName(services[0]), (Service) Class.forName(services[0]).newInstance());
      messageSystem.registerService(Class.forName(services[1]), (Service) constrAccSer.newInstance(accountServerPort));
      messageSystem.registerService(Class.forName(services[2]),(Service) constrCliConSer.newInstance(clientConnectionPort));
      messageSystem.getServices().forEach(Service::start);

      for (Service service : messageSystem.getServices()) {
        service.join();
      }

    } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | ClassNotFoundException | IOException | InvocationTargetException e) {
      log.error(e);
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
