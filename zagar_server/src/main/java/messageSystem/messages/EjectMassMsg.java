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
import protocol.CommandEjectMass;

/**
 * Created by Ольга on 24.11.2016.
 */
public class EjectMassMsg extends Message {
    private static final @NotNull Logger log = LogManager.getLogger(EjectMassMsg.class);
    CommandEjectMass cmdEjectMass;

    public EjectMassMsg(Address from, Command cmd) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        cmdEjectMass = (CommandEjectMass) cmd;
    }

    @Override
    public void exec(Abonent abonent) {
        log.info("Mass was ejected; " + "command:" + cmdEjectMass.NAME);
    }
}
