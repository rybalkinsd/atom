package replication;

import main.ApplicationContext;
import matchmaker.IMatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import model.Virus;
import network.ClientConnections;
import network.packets.PacketCellNames;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.CellsName;
import protocol.model.Food;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class FullStateReplicator implements Replicator {
  @NotNull
  public static final Logger log = LogManager.getLogger(FullStateReplicator.class);

  @Override
  public void replicate() {
    for (GameSession gameSession : ApplicationContext.instance().get(IMatchMaker.class).getActiveGameSessions()) {
      int numberOfPlayerCells = 0;
      for (Player player : gameSession.getPlayers()) {
        numberOfPlayerCells += player.getCells().size();
      }
      Cell[] cells = new Cell[numberOfPlayerCells + gameSession.getField().getViruses().size() + gameSession.getField().getFreeCells().size()];
      CellsName[] cellsNames = new CellsName[numberOfPlayerCells];


      int i = 0;
      for (Player player : gameSession.getPlayers()) {
        for (PlayerCell playerCell : player.getCells()) {
          cells[i] = new Cell(playerCell.getCellId(), playerCell.getPlayerId(), false, playerCell.getMass(), playerCell.getX(), playerCell.getY());
          cellsNames[i] = new CellsName(player.getName() ,playerCell.getCellId());
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
            new PacketCellNames(cellsNames).write(connection.getValue());
          } catch (IOException e) {
            log.warn(e.getMessage() + " occured during replication process.");
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
