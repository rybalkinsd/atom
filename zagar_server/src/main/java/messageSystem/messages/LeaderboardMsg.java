package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.LeaderboardReplicator;

/**
 * Created by xakep666 on 28.11.16.
 */
public class LeaderboardMsg extends Message {
    @NotNull
    private final static Logger log = LogManager.getLogger(LeaderboardMsg.class);

    public LeaderboardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        log.trace("LeaderboardMsg exec() call");
        ApplicationContext.instance().get(LeaderboardReplicator.class).replicate();
    }
}
