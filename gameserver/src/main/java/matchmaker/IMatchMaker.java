package matchmaker;

import model.GameSession;
import model.Player;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides (searches or creates) {@link GameSession} for {@link Player}
 *
 * @author Alpi
 */
public interface IMatchMaker {
  /**
   * Searches available game session or creates new one
   * @param user player to join the game session
   */
  GameSession joinGame(@NotNull User user);

  /**
   * Searches a player within game sessions and deletes him
   * @param user player to join the game session
   */
  boolean leaveGame(@NotNull User user);

  /**
   * @return Currently open game sessions
   */
  @NotNull
  List<GameSession> getActiveGameSessions();
}
