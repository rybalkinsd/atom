package matchmaker;

import model.GameSession;
import model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides (searches or creates) {@link GameSession} for {@link Player}
 *
 * @author Alpi
 */
public interface MatchMaker {
  void joinGame(@NotNull Player player);

  @NotNull
  List<GameSession> getActiveGameSessions();
}
