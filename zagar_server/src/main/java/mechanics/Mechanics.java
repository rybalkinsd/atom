package mechanics;

import main.ApplicationContext;
import main.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import ticker.Tickable;

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
  public synchronized void start() {
    super.start();
    log.info(getName() + " started");
  }

  @Override
  public void tick(long elapsedNanos) {
    log.info("Mechanics tick() started");
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      log.error(e);
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    log.info("Start replication");
    ApplicationContext.instance().get(Replicator.class).replicate();

    log.info("Mechanics tick() finished");
  }
}
