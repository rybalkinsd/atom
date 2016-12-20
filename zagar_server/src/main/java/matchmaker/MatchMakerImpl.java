package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.RandomPlayerPlacer;
import utils.UniformFoodGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class MatchMakerImpl implements MatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerImpl.class);
  @NotNull
  private final List<GameSession> activeGameSessions = new CopyOnWriteArrayList<>();

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
   * @return new GameSession
   */
  private
  @NotNull
  GameSession createNewGame() {
    for (GameSession activeGameSession : activeGameSessions) {
      if (activeGameSession.getPlayers().size()<GameConstants.MAX_PLAYERS_IN_SESSION)
        return activeGameSession;
    }
    Field field = new Field();
    UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
    return new GameSessionImpl(field, foodGenerator, new RandomPlayerPlacer(field));
  }

  @Override
  public void checkUnactive(){
    for (GameSession activeGameSession : activeGameSessions) {
      if (activeGameSession.getPlayers().size() == 0)
        activeGameSessions.remove(activeGameSession);
    }
  }
}
