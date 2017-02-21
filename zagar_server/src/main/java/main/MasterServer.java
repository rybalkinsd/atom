package main;

import accountserver.AccountServer;
import configuration.IConfiguration;
import configuration.IniConfiguration;
import configuration.NoConfiguration;
import matchmaker.IMatchMaker;
import matchmaker.MatchMakerMultiplayer;
import matchmaker.MatchMakerSingleplayer;
import mechanics.Mechanics;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import network.ClientConnections;
import network.Kicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.FullStateReplicator;
import replication.Replicator;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  private IConfiguration configuration;

  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  public MasterServer(IConfiguration configuration) {
    this.configuration = configuration;
  }

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
    //TODO RK3 configure server parameters
    ApplicationContext.instance().put(IMatchMaker.class, new MatchMakerSingleplayer());
    ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
    ApplicationContext.instance().put(Replicator.class, new FullStateReplicator());
    ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());

    //TODO Add custom stuff here
    ApplicationContext.instance().put(IMatchMaker.class, new MatchMakerMultiplayer());



    MessageSystem messageSystem = new MessageSystem();
    ApplicationContext.instance().put(MessageSystem.class, messageSystem);

    //TODO Add custom stuff here
    try {
      ApplicationContext.instance().put(IMatchMaker.class, Class.forName(configuration.getMatchMaker()).newInstance());
      ApplicationContext.instance().put(Replicator.class, Class.forName(configuration.getReplicator()).newInstance());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      log.fatal(e.getMessage());
      System.exit(1);
      return;
    }


    messageSystem.registerService(Kicker.class, new Kicker());
    try {
      messageSystem.registerService(Mechanics.class, (Service) Class.forName(configuration.getServices()[0]).newInstance());
      messageSystem.registerService(AccountServer.class,
              (Service) Class.forName(configuration.getServices()[1]).getConstructor(Integer.class).newInstance(configuration.getPort()));
      messageSystem.registerService(ClientConnectionServer.class,
              (Service) Class.forName(configuration.getServices()[2]).getConstructor(Integer.class).newInstance(configuration.getWSPort()));
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      log.fatal(e.getMessage());
      System.exit(1);
      return;
    }

    messageSystem.getServices().forEach(Service::start);

    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    IConfiguration configuration;
    try {
      if(args.length > 1) configuration = new IniConfiguration(args[1]);
      else configuration = new IniConfiguration("config.ini");
    }
    catch (IOException e) {
      configuration = new NoConfiguration();
    }

    MasterServer server = new MasterServer(configuration);
    server.start();
  }
}
