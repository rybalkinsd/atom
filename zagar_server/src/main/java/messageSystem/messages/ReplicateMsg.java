package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import replication.Replicator;

import java.io.IOException;

/**
 * Created by alpie on 15.11.2016.
 */
public class ReplicateMsg extends Message {
  public ReplicateMsg(Address from) {
    super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
  }

  @Override
  public void exec(Abonent abonent) {
    try {
      ApplicationContext.instance().get(Replicator.class).replicate();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
