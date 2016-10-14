package matchmaker;

import com.sun.istack.internal.NotNull;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class SinglePlayerMatchMaker implements MatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(SinglePlayerMatchMaker.class);
  @NotNull
  private final List<GameSession> activeGameSessions = new ArrayList<>();

  /**
   * Creates new GameSession for single player
   *
   * @param player single player
   */
  @Override
  public void joinGame(@NotNull Player player) {
      GameSession newGameSession = createNewGame();
      activeGameSessions.add(newGameSession);
      newGameSession.join(player);
      if (log.isInfoEnabled()) {
          log.info(player + " joined " + newGameSession);
      }
  }

  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return new ArrayList<>(activeGameSessions);
  }

  /**
   *
   * Log every game instance creation
   *
   * @return new GameSession
   */
 // @NotNull

  GameSession currentSession = new GameSession() {

      Set<Player> setOfPlayers = new HashSet<>();

      List<Food> listOfFood = new ArrayList<>();

      List<Obstacle> listOfObstacles = new ArrayList<>();

      @Override
      public void join(Player player){ setOfPlayers.add(player); }

      @Override
      public void addFood(Food food) { listOfFood.add(food); }

      @Override
      public void addObstacle(Obstacle obstacle) { listOfObstacles.add(obstacle); }

  };

  private GameSession createNewGame() {
      Random random = new Random();
      int i;
      for(i = 0; i < GameConstants.STARTING_AMOUNT_OF_FOOD; ++i) {
          Food food = new Food(random.nextDouble(), random.nextDouble());
          currentSession.addFood(food);
      }

      for(i = 0; i < GameConstants.STARTING_AMOUNT_OF_OBSTACLES; ++i) {
          Obstacle obstacle = new Obstacle(random.nextDouble(), random.nextDouble(), random.nextDouble());
          currentSession.addObstacle(obstacle);
      }
      return currentSession;
  }
}
