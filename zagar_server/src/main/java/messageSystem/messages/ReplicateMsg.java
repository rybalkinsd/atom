package messageSystem.messages;

import main.ApplicationContext;
import main.MasterServer;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;

import java.io.IOException;

/**
 * Created by alpie on 15.11.2016.
 */
public class ReplicateMsg extends Message {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  public ReplicateMsg(Address from) {
    super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
  }

  @Override
  public void exec(Abonent abonent) {
    try {
      ApplicationContext.instance().get(Replicator.class).replicate();
    } catch (IOException e) {
      log.error(e);
    }
  }
}
