package messageSystem.messages;

import network.ClientConnectionServer;
import leaderboardReplicator.LeaderboardReplicator;
import main.ApplicationContext;
import main.Service;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;

/**
 * Created by User on 28.11.2016.
 */
public class ReplicateLeaderboardMsg extends Message {
    public ReplicateLeaderboardMsg(Address from) {
        super(from, ApplicationContext.get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void execute(Service service) {
        ApplicationContext.get(LeaderboardReplicator.class).replicate();
    }
}
