package main;

import leaderboard.Leaderboard;
import matchmaker.MatchMaker;
import messageSystem.MessageSystem;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import utils.IDGenerator;
import utils.SequentialIDGenerator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.Properties;
import java.io.*;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
    Properties property = new Properties();
    MatchMaker m = null;
    Replicator r = null;
    Leaderboard l = null;
    LinkedList<Service> serv = new LinkedList<>();
    try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")){

      property.load(fis);
      int accountServerPort = Integer.parseInt(property.getProperty("accountServerPort"));
      int clientConnectionPort = Integer.parseInt(property.getProperty("clientConnectionPort"));
      String matchMaker = property.getProperty("matchMaker");
      String replicator = property.getProperty("replicator");
      String services = property.getProperty("services");
      String  leaderboard = property.getProperty("leaderboard");
      String[] ser = services.split(",");
      log.info("<<<Init params>>>");
      log.info("account server port: " + accountServerPort);
      log.info("client connection port: " + clientConnectionPort);
      log.info("matchmaker: " + matchMaker);
      log.info("replicator: " + replicator);
      log.info("Leaderboard: " + leaderboard);
      log.info("services: " + Arrays.asList(ser));

      for (String s : ser) {
        serv.add( (Service) Class.forName(s).newInstance());
      }
      l = (Leaderboard) Class.forName(leaderboard).newInstance();
      m = (MatchMaker) Class.forName(matchMaker).newInstance();
      r = (Replicator) Class.forName(replicator).newInstance();

      }
      catch (IllegalAccessException e) {
        log.error(e);
      System.exit(1);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IOException e){
        e.printStackTrace();
      }

    ApplicationContext.instance().put(MatchMaker.class, m);
    ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
    ApplicationContext.instance().put(Replicator.class, r);
    ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());
    ApplicationContext.instance().put(Leaderboard.class, l);

    MessageSystem messageSystem = new MessageSystem();
    ApplicationContext.instance().put(MessageSystem.class, messageSystem);

    serv.forEach(s -> messageSystem.registerService(s.getClass(), s));

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
