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
import protocol.CommandEjectMass;

import java.util.List;


/**
 * Created by s on 26.11.16.
 */
public class EjectMassMsg extends Message {
    private static final Logger logger = LogManager.getLogger(Mechanics.class);
    private final CommandEjectMass commandEjectMass;
    private final Player player;

    public EjectMassMsg(Address from, CommandEjectMass commandEjectMass, Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.commandEjectMass = commandEjectMass;
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        Field field = null;
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Player player: gameSession.getPlayers()){
                if (this.player == player){
                    field = gameSession.getField();
                }
            }
        }

        List<PlayerCell> cells = player.getCells();
        for(Cell cell: cells){
            if(cell.getMass() < 30) continue;//if mass is not enough do nothing
            int x = (int) Math.round(cell.getX() - 2 * cell.getRadius() * Math.cos(player.getAngle()));
            int y = (int) Math.round(cell.getY() + 2 * cell.getRadius() * Math.sin(player.getAngle()));
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x > GameConstants.FIELD_WIDTH) x = GameConstants.FIELD_WIDTH;
            if (y > GameConstants.FIELD_HEIGHT) y = GameConstants.FIELD_HEIGHT;
            cell.setMass(cell.getMass() - GameConstants.FOOD_MASS);//change mass
            field.getFoods().add(new Food(x, y));//add food on field
        }

    }
}
