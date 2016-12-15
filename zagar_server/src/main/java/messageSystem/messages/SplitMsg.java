package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.GameSession;
import model.Player;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Alex on 27.11.2016.
 */
public class SplitMsg extends Message {
    @NotNull
    private final Player player;


    public SplitMsg(@NotNull Player player) {
        super(ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress(), ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.player = player;
    }


    @Override
    public void exec(Abonent abonent) {
        player.split();
    }
}
