package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class FullStateReplicator implements Replicator {
  @Override
  public void replicate() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      Food[] food = new Food[0];//TODO food and viruses
      int numberOfCellsInSession = 0;
      for (Player player : gameSession.getPlayers()) {
        numberOfCellsInSession += player.getCells().size();
      }
      Cell[] cells = new Cell[numberOfCellsInSession];
      int i = 0;
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
            e.printStackTrace();
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
