package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;

/**
 * Created by ivan on 25.11.16.
 */
public class MoveMsg extends Message {
    private Player player;
    private float dx;
    private float dy;

    public MoveMsg(Player player, float dx, float dy) {
        super(null, ApplicationContext.instance()
                .get(MessageSystem.class)
                .getService(Mechanics.class)
                .getAddress());
        this.player = player;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void exec(Abonent abonent) {
        ((Mechanics) abonent).move(player,dx,dy);
    }
}
