package replication;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class FullStateReplicator implements Replicator {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  @Override
  public void replicate() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      int i = 0;
      HashSet<model.Food> foodSet = new HashSet<>(gameSession.getField().getFoods());
      Food[] food = new Food[foodSet.size()];

      for (model.Food e:foodSet){
        food[i] = new Food(e.getX(), e.getY());
        i++;
      }
      int numberOfCellsInSession = 0;
      for (Player player : gameSession.getPlayers()) {
        numberOfCellsInSession += player.getCells().size();
      }

      Cell[] cells = new Cell[numberOfCellsInSession];
      i = 0;
      for (Player player : gameSession.getPlayers()) {
        for (PlayerCell playerCell : player.getCells()) {
          cells[i] = new Cell(playerCell.getId(), player.getId(), false, playerCell.getMass(), playerCell.getX(), playerCell.getY());
          i++;
        }
      }
      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey())) {
          try {
            new PacketReplicate(cells, food).write(connection.getValue());
          } catch (IOException e) {
            log.error(e);
          }
        }
      }
    }

    /*ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions().stream().flatMap(
        gameSession -> gameSession.getPlayers().stream().flatMap(
            player -> player.getCells().stream()
        )
    ).map(playerCell -> new Cell(playerCell.getId(), ))*/
  }
}
