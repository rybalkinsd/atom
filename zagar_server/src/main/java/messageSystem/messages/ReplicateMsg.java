package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import replication.Replicator;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by alpie on 15.11.2016.
 */
public class ReplicateMsg extends Message {
  public static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(ReplicateMsg.class);

  public ReplicateMsg(Address from) {
    super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
  }

  @Override
  public void exec(Abonent abonent) {
    log.debug("ReplicationMsg exec() call");
    ApplicationContext.instance().get(Replicator.class).replicate();
  }
}
