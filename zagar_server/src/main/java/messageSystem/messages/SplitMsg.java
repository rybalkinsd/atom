package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.Command;
import protocol.CommandSplit;

/**
 * Created by Ольга on 24.11.2016.
 */
public class SplitMsg extends Message {
    private static final @NotNull Logger log = LogManager.getLogger(EjectMassMsg.class);
    CommandSplit cmdSplit;
    public SplitMsg(Address from, Command cmd) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        cmdSplit = (CommandSplit) cmd;
    }

    @Override
    public void exec(Abonent abonent) {
        log.info("The cell splited");
    }
}