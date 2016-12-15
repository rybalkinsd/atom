package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandSplit;

public class SplitMsg extends Message {
    private final static Logger log = LogManager.getLogger(MoveMsg.class);
    private CommandSplit commandSplit;

    public SplitMsg(Address from, CommandSplit commandSplit) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandSplit = commandSplit;
    }

    @Override
    public void exec(Abonent abonent) {
        log.info("CommandSplit was received");
    }
}