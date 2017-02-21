package messageSystem.messages;

import network.ClientConnectionServer;
import main.ApplicationContext;
import main.Service;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import replication.Replicator;

/**
 * Created by User on 28.11.2016.
 */
public class ReplicateMsg extends Message{
    public ReplicateMsg(Address from) {
        super(from, ApplicationContext.get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void execute(Service service) {
        ApplicationContext.get(Replicator.class).replicate();
    }
}
