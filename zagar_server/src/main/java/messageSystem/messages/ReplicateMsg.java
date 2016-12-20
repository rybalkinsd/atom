package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import network.ClientConnectionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;

/**
 * Created by alpie on 15.11.2016.
 */
public class ReplicateMsg extends Message {
    @NotNull
    private final static Logger log = LogManager.getLogger(ReplicateMsg.class);

    public ReplicateMsg(Address from) {
        super(from, Message.getMessageSystem().getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        log.trace("ReplicateMsg exec() call");
        ApplicationContext.instance().get(Replicator.class).replicate();
    }
}
