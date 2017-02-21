package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import replication.Replicator;

/**
 * Created by svuatoslav on 11/28/16.
 */
public class SendLeaderboardMsg extends Message {
    public SendLeaderboardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        ApplicationContext.instance().get(Replicator.class).sendLeaderboard();
    }
}
