package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import model.Virus;
import network.ClientConnections;
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
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {

      int numberOfFoodsInSession =0;
      numberOfFoodsInSession += gameSession.getField().getFoodSet().size();
      numberOfFoodsInSession += gameSession.getField().getSplitFoodSet().size();
      Food[] food = new Food[numberOfFoodsInSession];

      int i = 0;
      for (model.Food foods : gameSession.getField().getFoodSet())  {
        food[i] = new Food(foods.getX(),foods.getY());
        i++;
      }

      for (model.SplitFood foods : gameSession.getField().getSplitFoodSet())  {
        food[i] = new Food(foods.getX(),foods.getY());
        i++;
      }

      int numberOfCellsInSession = 0;

      numberOfCellsInSession+=gameSession.getField().getViruses().size();

      for (Player player : gameSession.getPlayers()) {
        numberOfCellsInSession += player.getCells().size();
      }

      Cell[] cells = new Cell[numberOfCellsInSession];
      i=0;
      for (Player player : gameSession.getPlayers()) {
        for (PlayerCell playerCell : player.getCells()) {
          cells[i] = new Cell(player.getId(), player.getId(), false, playerCell.getMass(), playerCell.getX(), playerCell.getY());
          i++;
        }
      }


      for (Virus virus : gameSession.getField().getViruses()){
        cells[i] = new Cell(-1,-1,true,virus.getMass(),virus.getX(),virus.getY());
        i++;
      }

      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey()))
          new PacketReplicate(cells, food).write(connection.getValue());
      }
    }
  }


}
