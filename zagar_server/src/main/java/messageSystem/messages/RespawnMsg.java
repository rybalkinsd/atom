package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;

/**
 * Created by artem on 19.12.16.
 */
public class RespawnMsg extends Message {
    Player player;

    public RespawnMsg(Address from,Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        Mechanics mechanics = ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class);
        if(abonent.getAddress()== mechanics.getAddress())
        {
            mechanics.Respawn(player);
        }
    }
}
