package messageSystem.messages;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Cell;
import model.GameConstants;
import model.Player;
import model.PlayerCell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandSplit;

import java.util.List;

/**
 * Created by s on 26.11.16.
 */
public class SplitMsg extends Message{
    private static final Logger logger = LogManager.getLogger(Mechanics.class);
    private final CommandSplit commandSplit;
    private final Player player;
    public SplitMsg(Address from, CommandSplit commandSplit, Player player) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
        this.commandSplit = commandSplit;
        this.player = player;
    }

    @Override
    public void exec(Abonent abonent) {
        List<PlayerCell> cells = player.getCells();
        for(Cell cell: cells){
            if(cell.getMass() < 30) continue;//if mass is not enough do nothing
            int x = (int) Math.round(cell.getX() - 2 * cell.getRadius() * Math.cos(player.getAngle()));
            int y = (int) Math.round(cell.getY() + 2 * cell.getRadius() * Math.sin(player.getAngle()));
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x > GameConstants.FIELD_WIDTH) x = GameConstants.FIELD_WIDTH;
            if (y > GameConstants.FIELD_HEIGHT) y = GameConstants.FIELD_HEIGHT;
            PlayerCell newcell = new PlayerCell(player.getId(), x, y);
            newcell.setMass(cell.getMass() / 2);
            player.addCell(newcell);//new cell
            cell.setMass(cell.getMass() / 2);//change mass
        }
    }
}
