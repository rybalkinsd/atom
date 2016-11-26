package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;
import network.ClientConnectionServer;
import protocol.CommandEjectMass;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class EjectMassMsg extends Message {
    private CommandEjectMass commandEjectMass;
    Player player;
    public EjectMassMsg(Address from, CommandEjectMass command, Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        commandEjectMass=command;
        this.player=player;
    }
    @Override
    public void exec(Abonent abonent) {
        Mechanics mechanics = ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class);
        if(abonent.getAddress()== mechanics.getAddress())
        {
            mechanics.EjectMass(player,commandEjectMass);
        }
    }
}
