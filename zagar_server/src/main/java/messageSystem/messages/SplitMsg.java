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

/**
 * Created by s on 26.11.16.
 */
public class SplitMsg extends Message{
    private static final Logger logger = LogManager.getLogger(Mechanics.class);
    private final CommandSplit commandSplit;

    public SplitMsg(Address from, CommandSplit commandSplit) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.commandSplit = commandSplit;
    }

    @Override
    public void exec(Abonent abonent) {
        logger.info("Spliting....");
    }
}
