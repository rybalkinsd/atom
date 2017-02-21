package messageSystem.messages;

import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Address;
import messageSystem.Message;
import model.Player;

/**
 * Created by User on 28.11.2016.
 */
public class MoveMsg extends Message {
    private Player player;
    private double dx, dy;

    public MoveMsg(Address from, Address to, Player player, double dx, double dy) {
        super(from, to);
        this.player = player;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(Service service) {
        ApplicationContext.get(MatchMaker.class).getSessionForPlayer(player).offerNewLocation(player, dx, dy);
    }
}
