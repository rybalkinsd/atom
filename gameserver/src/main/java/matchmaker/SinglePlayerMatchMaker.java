package matchmaker;

import model.GameConstants;
import model.GameSession;
import model.GameSessionImpl;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
   * Serches available game sessions,
   * if there is no available session,
   * creates new session
   *
   * @param player single player
   */
  @Override
  public void joinGame(@NotNull Player player) {
    for (GameSession session : activeGameSessions) {
      if (session.getPlayersCount() < GameConstants.MAX_PLAYERS_IN_SESSION) {
        session.join(player);
        return;
      }
    }
    GameSession newGameSession = createNewGame();
    activeGameSessions.add(newGameSession);
    newGameSession.join(player);
    if (log.isInfoEnabled()) {
      log.info(player + " joined " + newGameSession);
    }
  }

  /**
   * Returns all active game sessions
   * @return List of active game sessions
   */
  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return activeGameSessions;
  }

  /**
   * Creates new Game Session
   *
   * @return new GameSession
   */
  @NotNull
  private GameSession createNewGame() {
    GameSession session = new GameSessionImpl();
    return session;
  }
}
