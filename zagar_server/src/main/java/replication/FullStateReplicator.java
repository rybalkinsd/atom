package replication;

import main.ApplicationContext;
import matchmaker.IMatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import model.Virus;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.IOException;
import java.util.Map;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class FullStateReplicator implements Replicator {
  @Override
  public void replicate() {
    for (GameSession gameSession : ApplicationContext.instance().get(IMatchMaker.class).getActiveGameSessions()) {
      int numberOfCellsInSession = 0;
      for (Player player : gameSession.getPlayers()) {
        numberOfCellsInSession += player.getCells().size();
      }
      Cell[] cells = new Cell[
              numberOfCellsInSession
              + gameSession.getField().getViruses().size()
              + gameSession.getField().getFreeCells().size()
              ];

      int i = 0;
      for (Player player : gameSession.getPlayers()) {
        for (PlayerCell playerCell : player.getCells()) {
          cells[i] = new Cell(playerCell.getCellId(), playerCell.getPlayerId(), false, playerCell.getMass(), playerCell.getX(), playerCell.getY());
          i++;
        }
      }
      for (Virus virus : gameSession.getField().getViruses()){
        cells[i] = new Cell(virus.getCellId(), -1, true, virus.getMass(), virus.getX(), virus.getY());
        i++;
      }
      for (model.Cell freeCell : gameSession.getField().getFreeCells()){
        cells[i] = new Cell(freeCell.getCellId(), -1, false, freeCell.getMass(), freeCell.getX(), freeCell.getY());
        i++;
      }

      i = 0;
      Food[] food = new Food[gameSession.getField().getFoods().size()];
      for (model.Food f : gameSession.getField().getFoods()){
        food[i] = new Food(f.getX(), f.getY());
        i++;
      }

      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey())) {
          try {
            new PacketLeaderBoard(gameSession.getLeaders()).write(connection.getValue());
            new PacketReplicate(cells, food).write(connection.getValue());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    /*ApplicationContext.instance().get(IMatchMaker.class).getActiveGameSessions().stream().flatMap(
        gameSession -> gameSession.getPlayers().stream().flatMap(
            player -> player.getCells().stream()
        )
    ).map(playerCell -> new Cell(playerCell.getPlayerId(), ))*/
  }
}
