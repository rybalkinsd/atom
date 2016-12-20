package messageSystem.messages;

import mechanics.CollisionHandler;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import model.Cell;
import model.GameConstants;
import model.Player;
import model.PlayerCell;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandMove;

import java.awt.geom.Point2D;
import java.util.List;

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
        log.trace("Moving player {}: dx {} dy {}", player, command.getDx(), command.getDy());
        if (Math.abs(command.getDx()) > GameConstants.MAX_COORDINATE_DELTA_MODULE ||
                Math.abs(command.getDy()) > GameConstants.MAX_COORDINATE_DELTA_MODULE) {
            log.info("Player {} may be cheater", player);
            return;
        }
        boolean flagX = true;
        boolean flagY = true;
        for (PlayerCell cell : player.getCells()) {
            double newValidX = cell.getCoordinate().getX();
            double newX = cell.getCoordinate().getX() + command.getDx() * GameConstants.INITIAL_SPEED / cell.getMass();
            boolean inBoundsOnX = (newX + cell.getRadius() / 2 <= player.getField().getSize().getWidth()) &&
                    (newX - cell.getRadius() / 2 >= 0);
            if (!inBoundsOnX) flagX = false;
            if (inBoundsOnX && flagX) {
                newValidX = newX;
            }

            double newValidY = cell.getCoordinate().getY();
            double newY = cell.getCoordinate().getY() + command.getDy() * GameConstants.INITIAL_SPEED / cell.getMass();
            boolean inBoundsOnY = (newY + cell.getRadius() / 2 <= player.getField().getSize().getHeight()) &&
                    (newY - cell.getRadius() / 2 >= 0);
            if (!inBoundsOnY) flagY = false;
            if (inBoundsOnY && flagY && flagX) {
                newValidY = newY;
            }

            player.getField().moveCell(cell, new Point2D.Double(newValidX, newValidY));
            //find collisions
            List<Cell> intersected = player.getField().findIntersected(cell);
            intersected.forEach(cellToCheck -> CollisionHandler.handleCollision(cell, cellToCheck));
        }
    }
}
