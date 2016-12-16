package mechanics;

import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.LeaderboardMsg;
import messageSystem.messages.ReplicateMsg;
import model.GameConstants;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandEjectMass;
import protocol.commands.CommandMove;
import protocol.commands.CommandSplit;
import ticker.Tickable;
import ticker.Ticker;

import java.time.Duration;

/**
 * Created by apomosov on 14.05.16.
 * <p>
 * Game mechanics
 */
public class Mechanics extends Service implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(Mechanics.class);
    @NotNull
    private final Ticker ticker = new Ticker(this);

    public Mechanics() {
        super("mechanics");
    }

    @Override
    public void run() {
        log.info(getAddress() + " started");
        ticker.loop();
    }

    @Override
    public void tick(@NotNull Duration elapsed) {
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);

        //execute all messages from queue
        messageSystem.execForService(this);

        //execute game session ticks
        MatchMaker matchMaker = ApplicationContext.instance().get(MatchMaker.class);
        matchMaker.getActiveGameSessions().forEach(gameSession -> {
            gameSession.tickRemoveAfk();
            gameSession.tickGenerators(elapsed);
        });

        log.trace("Start replication");
        Message message = new ReplicateMsg(getAddress());
        Message lbMessage = new LeaderboardMsg(getAddress());
        messageSystem.sendMessage(message);
        messageSystem.sendMessage(lbMessage);

    /*System.out.println("Conns " +
            ApplicationContext.instance().get(ClientConnections.class).getConnections());*/

        log.trace("Mechanics tick() finished");
    }

    public void ejectMass(@NotNull Player player, @NotNull CommandEjectMass commandEjectMass) {
        log.debug("Mass ejected");
    }

    public void move(@NotNull Player player, @NotNull CommandMove commandMove) {
        log.trace("Moving player {}: dx {} dy {}", player, commandMove.getDx(), commandMove.getDy());
        if (Math.abs(commandMove.getDx()) > GameConstants.MAX_COORDINATE_DELTA_MODULE ||
                Math.abs(commandMove.getDy()) > GameConstants.MAX_COORDINATE_DELTA_MODULE) {
            log.info("Player {} may be cheater", player);
            return;
        }
        player.getCells().forEach(cell -> {
            int newValidX = cell.getX();
            float newX = cell.getX() + commandMove.getDx();
            boolean inBoundsOnX = (newX + cell.getRadius() / 2 <= player.getField().getWidth()) &&
                    (newX - cell.getRadius() / 2 >= 0);
            if (inBoundsOnX) {
                newValidX = (int) newX;
            }

            int newValidY = cell.getY();
            float newY = cell.getY() + commandMove.getDy();
            boolean inBoundsOnY = (newY + cell.getRadius() / 2 <= player.getField().getHeight()) &&
                    (newX - cell.getRadius() / 2 >= 0);
            if (inBoundsOnY) {
                newValidY = (int) newY;
            }

            player.getField().moveCell(cell, newValidX, newValidY);
        });
        //TODO handle collisions
    }

    public void split(@NotNull Player player, @NotNull CommandSplit commandSplit) {
        log.info("Split");
    }
}
