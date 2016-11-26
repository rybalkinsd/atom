package mechanics;

import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import main.ApplicationContext;
import main.Service;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateMsg;
import model.Player;
import network.ClientConnectionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.Command;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import protocol.CommandSplit;
import replication.Replicator;
import ticker.Tickable;
import ticker.Ticker;
import utils.PropertiesReader;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);

  public Mechanics() {
    super("mechanics");
  }

  public Mechanics(PropertiesReader preader) {
    super("mechanics");
  } //для совместимости

  @Override
  public void run() {
    log.info(getAddress() + " started");
    Ticker ticker = new Ticker(this, 1);
    ticker.loop();
  }

  @Override
  public void tick(long elapsedNanos) {
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      log.error(e);
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    log.info("Start replication");
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new ReplicateMsg(this.getAddress());
    messageSystem.sendMessage(message);

    //execute all messages from queue
    messageSystem.execForService(this);
  }

  public void EjectMass (Player player, CommandEjectMass commandEjectMass)
  {
    log.info(player.toString() + " wants to eject mass: " + commandEjectMass.toString());
  }

  public void Move (Player player, CommandMove commandMove)
  {
    log.info(player.toString() + " wants to move: " + commandMove.toString());
  }

  public void Split (Player player, CommandSplit commandSplit)
  {
    log.info(player.toString() + " wants to split: " + commandSplit.toString());
  }
}
