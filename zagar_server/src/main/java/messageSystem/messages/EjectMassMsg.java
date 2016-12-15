package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandEjectMass;

public class EjectMassMsg extends Message{
    private final static Logger log = LogManager.getLogger(EjectMassMsg.class);
    private CommandEjectMass commandEjectMass;

    public EjectMassMsg(Address from, CommandEjectMass commandEjectMass) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(Mechanics.class).getAddress());
        this.commandEjectMass = commandEjectMass;
    }

    @Override
    public void exec(Abonent abonent) {
        log.info("CommandEjectMass was received");
    }
}