package messageSystem.messages;

import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import model.Player;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandSplit;

/**
 * Created by Klissan on 28.11.2016.
 */
public class SplitMsg extends Message {

    @NotNull
    private CommandSplit command;
    @NotNull
    private Player player;

    public SplitMsg(@NotNull Player player, @NotNull CommandSplit command) {
        super(Message.getMessageSystem().getService(ClientConnectionServer.class).getAddress(),
                Message.getMessageSystem().getService(Mechanics.class).getAddress());
        this.command = command;
        this.player = player;
        log.trace("SplitMsg created");

    }

    @Override
    public void exec(Abonent abonent) {
        log.trace("SplitMsg exec() call");
        Message.getMessageSystem().getService(Mechanics.class).split(player, command);
    }
}
