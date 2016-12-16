package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.PlayerCell;
import network.ClientConnections;
import network.packets.PacketReplicate;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alpi
 * @since 31.10.16
 *
 * Replicates full session state to clients
 */
public class FullStateReplicator implements Replicator {
    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            List<Food> food = gameSession.getField().getCells(model.Food.class).stream()
                    .map(f -> new Food(f.getX(), f.getY()))
                    .collect(Collectors.toList());
            List<Cell> cells = new ArrayList<>();
            gameSession.getField()
                    .getCells(PlayerCell.class)
                    .forEach(cell -> cells.add(
                            new Cell(cell.getId(),
                                    cell.getOwner().getId(),
                                    false,
                                    cell.getMass(),
                                    cell.getX(),
                                    cell.getY()))
                    );
            cells.addAll(
                    gameSession.getField().getCells(model.Virus.class).stream()
                            .map(virus ->
                                    //negative IDs shows that cell not belongs to player
                                    new Cell(-1, -1, true, virus.getMass(), virus.getX(), virus.getY()))
                            .collect(Collectors.toList())
            );
            ApplicationContext.instance().get(ClientConnections.class).getConnections().forEach(connection -> {
                if (gameSession.getPlayers().contains(connection.getKey())
                        && connection.getValue().isOpen()) {
                    try {
                        new PacketReplicate(cells, food).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
