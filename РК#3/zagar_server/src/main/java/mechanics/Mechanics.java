package mechanics;

import main.ApplicationContext;
import main.Service;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateMsg;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import protocol.CommandSplit;
import ticker.Tickable;
import ticker.Ticker;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);

  public Mechanics() {
    super("mechanics");
  }

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

  public void EjectMass (@NotNull  Player player,@NotNull CommandEjectMass commandEjectMass)
  {
    log.info("{} wants to eject mass (in thread {})",player,Thread.currentThread());
  }

  public void Move (@NotNull Player player, @NotNull CommandMove commandMove)
  {
    log.info("{} wants to move <{},{}> (in thread {})",player,commandMove.getDx(),commandMove.getDy(),Thread.currentThread());
  }

  public void Split (@NotNull Player player, @NotNull CommandSplit commandSplit)
  {
    log.info("{} wants to split (in thread {})",player,Thread.currentThread());
  }
}
