package matchmaker;

import model.GameConstants;
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import model.GameSpace;

import java.util.ArrayList;
import java.util.List;

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
    try {
      GameSession newGameSession = createNewGame();
      activeGameSessions.add(newGameSession);
      newGameSession.join(player);
      if (log.isInfoEnabled()) {
        log.info(player + " joined " + newGameSession);
      }
    }
    catch (Throwable t){
      if (log.isInfoEnabled()){
        log.info("Error has been detected: \""+t.getMessage()+"\"");
      }
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
    return new GameSpace();
  }
}
