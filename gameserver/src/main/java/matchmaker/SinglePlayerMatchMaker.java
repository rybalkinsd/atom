package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static model.GameConstants.DEFAULT_PLAYER_POSITION_X;
import static model.GameConstants.DEFAULT_PLAYER_POSITION_Y;
import static model.GameConstants.DEFAULT_RADIUS;

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
      log.info(player + " joined ");
    }
  }

  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return new ArrayList<>(activeGameSessions);
  }

  /**
   * TODO HOMEWORK 1. Implement new game creation. Instantiate GameSession state
   * Log every game instance creation
   *
   * @return new GameSession
   */
  @NotNull
  private GameSession createNewGame() {
    GameSession gameSession = new GameSession() {
      @Override
      public void join(@NotNull Player player) {
        log.info("joining the game...");
      }
    };
    try {
      BackGround backGround = new BackGround("background.jpg");
      Food food = new Food(DEFAULT_PLAYER_POSITION_X, DEFAULT_PLAYER_POSITION_Y, Color.BLUE);
      Bomb bomb = new Bomb(DEFAULT_PLAYER_POSITION_X, DEFAULT_PLAYER_POSITION_Y, DEFAULT_RADIUS);
    }
    catch (Exception e) {
      if (log.isInfoEnabled())
        log.info("some exception");
    }
    return gameSession;
  }
}
