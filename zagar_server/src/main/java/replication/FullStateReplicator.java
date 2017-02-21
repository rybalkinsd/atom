package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Alpi
 * @since 31.10.16
 *
 * Replicates full session state to clients
 */
public class FullStateReplicator implements Replicator {
    @NotNull
    private static final Logger log = LogManager.getLogger(FullStateReplicator.class);
    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            List<Cell> replicateCells = gameSession.getField().getAllCells().stream()
                    .map(cell->{
                        if (cell instanceof model.PlayerCell) {
                            model.PlayerCell c = ((model.PlayerCell) cell);
                            return new PlayerCell(
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

            ApplicationContext.instance().get(ClientConnections.class).getConnections().forEach(connection -> {
                if (gameSession.getPlayers().contains(connection.getKey())
                        && connection.getValue().isOpen()) {
                    try {
                        new PacketReplicate(replicateCells).write(connection.getValue());
                    } catch (IOException e) {
                        log.fatal(e.getMessage());
                    }
                }
            });
        }
    }
}
