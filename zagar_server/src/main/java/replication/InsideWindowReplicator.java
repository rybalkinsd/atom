package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Cell;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.model.EjectedMass;
import protocol.model.Food;
import protocol.model.Virus;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by xakep666 on 16.11.16.
 * <p>
 * Replicates only cells which fits in player window
 * Window size stored in {@link Player} object
 */
public class InsideWindowReplicator implements Replicator {
    @NotNull
    private static final Logger log = LogManager.getLogger(InsideWindowReplicator.class);
    private static final int widthDelta = 10;
    private static final int heightDelta = 10;
    private static final double widthFactor = 1;
    private static final double heightFactor = 1;

    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            gameSession.getPlayers().forEach(player -> {
                List<PlayerCell> playerCells = player.getCells();
                Point2D center = calculateCenter(playerCells);
                int windowWidth = player.getWindowWidth();
                int windowHeight = player.getWindowHeight();

                final Rectangle2D border = new Rectangle2D.Double(
                        center.getX() - (windowWidth * widthFactor / 2) - widthDelta,
                        center.getY() + (windowHeight * heightFactor / 2) + heightDelta,
                        center.getX() + (windowWidth * widthFactor / 2) + widthDelta,
                        center.getY() - (windowHeight * heightFactor / 2) - heightDelta);

                List<Cell> cellsToSend = gameSession.getField().getCells(Cell.class);
                sendToPlayer(player, cellsToSend, border);
            });
        }
    }

    @NotNull
    private Point2D calculateCenter(@NotNull List<PlayerCell> cells) {
        double x = 0;
        double y = 0;
        for (Cell cell : cells) {
            x += cell.getCoordinate().getX();
            y += cell.getCoordinate().getY();
        }
        x /= cells.size();
        y /= cells.size();
        return new Point2D.Double(x, y);
    }

    private void sendToPlayer(@NotNull Player player,
                              @NotNull List<Cell> cellsToSend,
                              @NotNull Rectangle2D border) {
        Session session = ApplicationContext.instance().get(ClientConnections.class).getSessionByPlayer(player);
        if (session == null) return;
        List<protocol.model.Cell> fitsInScreen = cellsToSend.stream()
                .filter(cell->border.contains(cell.getCoordinate()))
                .map(cell->{
                    if (cell instanceof model.PlayerCell) {
                        model.PlayerCell c = ((model.PlayerCell) cell);
                        return new protocol.model.PlayerCell(
                                c.getId(),
                                c.getMass(),
                                c.getCoordinate(),
                                c.getRadius(),
                                c.getOwner().getUser().getName()
                        );
                    }
                    if (cell instanceof model.EjectedMass) {
                        model.EjectedMass c = ((model.EjectedMass) cell);
                        return new EjectedMass(c.getMass(), c.getCoordinate(), c.getRadius());
                    }
                    if (cell instanceof model.Food) {
                        model.Food c = ((model.Food) cell);
                        return new Food(c.getMass(), c.getCoordinate(), c.getRadius());
                    }
                    if (cell instanceof model.Virus) {
                        model.Virus c = ((model.Virus) cell);
                        return new Virus(c.getMass(), c.getCoordinate(), c.getRadius());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        try {
            new PacketReplicate(fitsInScreen).write(session);
        } catch (IOException e) {
            log.fatal(e.getMessage());
        }
    }

}
