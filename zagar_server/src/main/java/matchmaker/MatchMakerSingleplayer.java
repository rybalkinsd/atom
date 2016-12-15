package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.RandomPlayerPlacer;
import utils.RandomVirusGenerator;
import utils.UniformFoodGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class MatchMakerSingleplayer implements IMatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerSingleplayer.class);
  @NotNull
  protected final List<GameSession> activeGameSessions = new ArrayList<>();

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

  @Override
  public GameSession getHostGameSession(Player player) {
    for (GameSession gameSession : activeGameSessions){
      if(gameSession.getPlayers().contains(player)){
        return gameSession;
      }
    }
    return null;
  }

  /**
   * @return new GameSession
   */
  protected
  @NotNull
  GameSession createNewGame() {
    Field field = new Field();
    //TODO
    //Ticker ticker = ApplicationContext.instance().get(Ticker.class);
    UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
    //ticker.registerTickable(foodGenerator);
    return new GameSessionImpl(field ,foodGenerator, new RandomPlayerPlacer(field), new RandomVirusGenerator(field, GameConstants.NUMBER_OF_VIRUSES));
  }
}
