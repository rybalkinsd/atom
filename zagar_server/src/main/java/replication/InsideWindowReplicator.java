package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.*;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xakep666 on 16.11.16.
 * <p>
 * Replicates only cells which fits in player window
 * Window size stored in {@link Player} object
 */
public class InsideWindowReplicator implements Replicator {
    private static final int widthDelta = 10;
    private static final int heightDelta = 10;
    private static final double widthFactor = 1;
    private static final double heightFactor = 1;

    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            gameSession.getPlayers().forEach(player -> {
                List<PlayerCell> playerCells = player.getCells();
                Point center = calculateCenter(playerCells);
                int windowWidth = player.getWindowWidth();
                int windowHeight = player.getWindowHeight();

                final Rectangle2D border = new Rectangle2D.Double(
                        center.x - (windowWidth * widthFactor / 2) - widthDelta,
                        center.y + (windowHeight * heightFactor / 2) + heightDelta,
                        center.x + (windowWidth * widthFactor / 2) + widthDelta,
                        center.y - (windowHeight * heightFactor / 2) - heightDelta);

                List<PlayerCell> playerCellsToSend = playerCells.stream()
                        .filter(cell -> border.contains(cell.getX(), cell.getY()))
                        .collect(Collectors.toList());
                List<Food> foodsToSend = gameSession.getField()
                        .getCells(Food.class)
                        .stream()
                        .filter(cell -> border.contains(cell.getX(), cell.getY()))
                        .collect(Collectors.toList());
                List<Virus> virusesToSend = gameSession.getField()
                        .getCells(Virus.class)
                        .stream()
                        .filter(cell -> border.contains(cell.getX(), cell.getY()))
                        .collect(Collectors.toList());
                sendToPlayer(player, playerCellsToSend, foodsToSend, virusesToSend);
            });
        }
    }

    @NotNull
    private Point calculateCenter(@NotNull List<PlayerCell> cells) {
        int x = 0;
        int y = 0;
        for (Cell cell : cells) {
            x += cell.getX();
            y += cell.getY();
        }
        x /= cells.size();
        y /= cells.size();
        return new Point(x, y);
    }

    private void sendToPlayer(@NotNull Player player,
                              @NotNull List<PlayerCell> playerCells,
                              @NotNull List<Food> foods,
                              @NotNull List<Virus> viruses) {
        Session session = ApplicationContext.instance().get(ClientConnections.class).getSessionByPlayer(player);
        if (session == null) return;
        List<protocol.model.Cell> playerCellsToSend = playerCells.stream()
                .map(cell -> new protocol.model.Cell(
                        cell.getId(),
                        player.getId(),
                        false,
                        cell.getRadius(),
                        cell.getX(),
                        cell.getY()))
                .collect(Collectors.toList());

        List<protocol.model.Food> foodsToSend = foods.stream()
                .map(f -> new protocol.model.Food(f.getX(), f.getY()))
                .collect(Collectors.toList());
        playerCellsToSend.addAll(
                viruses.stream()
                        .map(virus ->
                                //negative IDs shows that cell not belongs to player
                                new protocol.model.Cell(
                                        -1,
                                        -1,
                                        true,
                                        virus.getMass(),
                                        virus.getX(),
                                        virus.getY()))
                        .collect(Collectors.toList())
        );
        try {
            new PacketReplicate(playerCellsToSend, foodsToSend).write(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
