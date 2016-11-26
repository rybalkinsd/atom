package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;
import protocol.CommandMove;
import protocol.CommandSplit;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class SplitMsg extends Message {
    private CommandSplit commandSplit;
    Player player;
    public SplitMsg(Address from, CommandSplit command, Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        commandSplit=command;
        this.player=player;
    }

    @Override
    public void exec(Abonent abonent) {
        Mechanics mechanics = ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class);
        if(abonent.getAddress()== mechanics.getAddress())
        {
            mechanics.Split(player,commandSplit);
        }
    }
}
