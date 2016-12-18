package messageSystem.messages;

import main.Service;
import mechanics.Mechanics;
import messageSystem.Address;
import messageSystem.Message;
import model.Player;

/**
 * Created by User on 28.11.2016.
 */
public class EjectMassMsg extends Message {
    private Player player;

    public EjectMassMsg(Address from, Address to, Player player){
        super(from, to);
        this.player = player;
    }

    @Override
    public void execute(Service service) {
        ((Mechanics)service).ejectMass(player);
    }

}
