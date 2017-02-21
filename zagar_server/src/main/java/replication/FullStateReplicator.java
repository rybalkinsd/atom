package replication;

import leaderboard.LeaderboardState;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.*;
import network.ClientConnections;
import network.handlers.PacketHandlerAuth;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Alpi
 * @since 31.10.16
 */
  public class FullStateReplicator implements Replicator {
  private final static Logger log = LogManager.getLogger(FullStateReplicator.class);
  @Override
  public void replicate() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {

      Set<model.Food> foodSet = new CopyOnWriteArraySet<>(gameSession.getField().getFoodSet());
      Set<SplitFood> splitFoodSet = new CopyOnWriteArraySet<>(gameSession.getField().getSplitFoodSet());
      List<Player> playerList = new CopyOnWriteArrayList<> (gameSession.getPlayers());
      List<model.Virus> virusList= new CopyOnWriteArrayList<>(gameSession.getField().getViruses());


      List<Food> food = new ArrayList<>();

      for (model.Food foods : foodSet)
        food.add(new Food(foods.getX(),foods.getY()));


      for (model.SplitFood foods : splitFoodSet)
        food.add(new Food(foods.getX(),foods.getY()));

      List<Cell> cells = new ArrayList<>();

      for (Player player : playerList)
        for (PlayerCell playerCell : player.getCells())
          cells.add(new Cell(player.getId(), player.getId(),
                  false, playerCell.getMass(), playerCell.getX(), playerCell.getY()));

      for (Virus virus : virusList)
        cells.add(new Cell(-1,-1,true,virus.getMass(),virus.getX(),virus.getY()));

      Food[] foodMas = new Food[food.size()];
      Cell[] cellMas = new Cell[cells.size()];

      int i = 0;

      for (Food food1 : food) {
        foodMas[i] = new Food(food1);
        i++;
      }

      i = 0;

      for (Cell cell1 : cells) {
        cellMas[i] = new Cell(cell1);
        i++;
      }

      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey()))
          new PacketReplicate(cellMas, foodMas).write(connection.getValue());
      }

    }
  }

  @Override
  public void sendLeaderboard()
  {
    String leaderboard[]= LeaderboardState.get();
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey())) {
          try {
            new PacketLeaderBoard(leaderboard).write(connection.getValue());
          } catch (IOException e) {
            log.error("Failed to send leaderboard packet",e);
          }
        }
      }
    }
  }


}
