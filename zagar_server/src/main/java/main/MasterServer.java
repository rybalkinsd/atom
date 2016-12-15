package main;

import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import statistic.LeaderBoard;
import utils.IDGenerator;
import utils.SequentialIDGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
    Properties prop = new Properties();
    InputStream input = null;
    try {
      input = new FileInputStream("config.properties");
      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    try {
      ApplicationContext.instance().put(MatchMaker.class,
              Class.forName(prop.getProperty("matchMaker")).newInstance());
      ApplicationContext.instance().put(ClientConnections.class,
              new ClientConnections());
      ApplicationContext.instance().put(Replicator.class,
              Class.forName(prop.getProperty("replicator")).newInstance());
      ApplicationContext.instance().put(IDGenerator.class,
              new SequentialIDGenerator());
      ApplicationContext.instance().put(LeaderBoard.class,
              Class.forName(prop.getProperty("leaderBoard")).newInstance());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    MessageSystem messageSystem = new MessageSystem();
    ApplicationContext.instance().put(MessageSystem.class, messageSystem);
    try {
      String[] serviceNames = prop.getProperty("services").split(",");
      messageSystem.registerService(Class.forName(serviceNames[0]),
              (Service) Class.forName(serviceNames[0]).newInstance());
      messageSystem.registerService(Class.forName(serviceNames[1]),
              (Service) Class.forName(serviceNames[1]).getConstructor(Integer.class)
                      .newInstance(Integer.valueOf(prop.getProperty("accountServerPort"))));
      messageSystem.registerService(Class.forName(serviceNames[2]),
              (Service) Class.forName(serviceNames[2]).getConstructor(Integer.class)
                      .newInstance(Integer.valueOf(prop.getProperty("clientConnectionPort"))));
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
    messageSystem.getServices().forEach(Service::start);
    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args)
          throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}