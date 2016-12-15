package matchmaker;

import model.GameSession;
import model.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface MatchMaker {
  void joinGame(@NotNull Player player);
  @NotNull
  List<GameSession> getActiveGameSessions();
  void leaveGame(@NotNull Player player);
  GameSession getGameSession(String name);
}