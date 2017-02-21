package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.GameSession;
import model.GameSessionImpl;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Alex on 27.11.2016.
 */
public class MoveMsg extends Message {
    private final float dx;
    private final float dy;
    @NotNull
    private final Player player;


    public MoveMsg(float dx, float dy, @NotNull Player player) {
        super(ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress(), ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.dx = dx;
        this.dy = dy;
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        player.move(dx,dy);
    }

}
