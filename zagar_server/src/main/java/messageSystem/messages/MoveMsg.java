package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandMove;

import static java.lang.Math.abs;

/**
 * Created by s on 26.11.16.
 */
public class MoveMsg extends Message{
    private static final Logger logger = LogManager.getLogger(Mechanics.class);
    private final CommandMove commandMove;
    private final Player player;
    public MoveMsg(Address from, CommandMove commandMove, Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.commandMove = commandMove;
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        GameSession s = null;
        for (GameSession session :ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions())
            if (session.getPlayers().contains(player)) { s = session; break;}

        if (s == null) return;
        for (PlayerCell cell_i: player.getCells()) {
            float x = commandMove.getDx() + cell_i.getX();
            float y = commandMove.getDy() + cell_i.getX();
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x > GameConstants.FIELD_WIDTH) x = GameConstants.FIELD_WIDTH;
            if (y > GameConstants.FIELD_HEIGHT) y = GameConstants.FIELD_HEIGHT;
            cell_i.setX((int)x);
            cell_i.setY((int) y);
            for(Food f :s.getField().getFoods()){
                if (abs(f.getX()-x) < cell_i.getRadius() && abs(f.getY()-y) < cell_i.getRadius()){
                    s.getField().getFoods().remove(f);
                    cell_i.setMass(cell_i.getMass() + f.getMass());
                }
            }
            for(Player p :s.getPlayers()) {
                if (p == player) continue;
                for (PlayerCell c : p.getCells()) {
                    int r = cell_i.getRadius() -  c.getRadius() / 2;
                    if (c.getRadius()>cell_i.getRadius()) r = 0;
                    if (abs(c.getX() - x) < r && abs(c.getY() - y) < r) {
                        p.removeCell(c);
                        cell_i.setMass(cell_i.getMass() + c.getMass());
                    }
                }
            }
        }
        player.setAngle(Math.atan2(commandMove.getDy(), commandMove.getDx())); // set angle for next actions
    }
}
