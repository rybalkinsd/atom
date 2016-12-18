package messageSystem.messages;

import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import model.Player;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandMove;

/**
 * Created by Klissan on 28.11.2016.
 */
public class MoveMsg extends Message {

    @NotNull
    private CommandMove command;
    @NotNull
    private Player player;

    public MoveMsg(@NotNull Player player, @NotNull CommandMove command) {
        super(Message.getMessageSystem().getService(ClientConnectionServer.class).getAddress(),
                Message.getMessageSystem().getService(Mechanics.class).getAddress());
        this.command = command;
        this.player = player;
        log.trace("MoveMsg created");

    }

    @Override
    public void exec(Abonent abonent) {
        log.trace("MoveMsg exec() call");
        Message.getMessageSystem().getService(Mechanics.class).move(player, command);
    }
}
