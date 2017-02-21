package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;
import protocol.CommandMove;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class MoveMsg extends Message {
    private CommandMove commandMove;
    Player player;
    public MoveMsg(Address from, CommandMove command,Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        commandMove = command;
        this.player=player;
    }

    @Override
    public void exec(Abonent abonent) {
        Mechanics mechanics = ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class);
        if(abonent.getAddress()== mechanics.getAddress())
        {
            mechanics.Move(player, commandMove);
        }
    }
}
