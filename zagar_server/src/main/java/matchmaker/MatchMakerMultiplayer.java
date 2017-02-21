package matchmaker;

import model.GameConstants;
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eugene on 11/22/16.
 */
public class MatchMakerMultiplayer extends MatchMakerSingleplayer {
  @NotNull
  public static final Logger log = LogManager.getLogger(MatchMakerMultiplayer.class);

  @Override
  public void joinGame(@NotNull Player player) {
    GameSession availableSession = activeGameSessions.stream()
            .filter(gameSession -> gameSession.getPlayers().size() < GameConstants.MAX_PLAYERS_IN_SESSION)
            .findFirst().orElse(null);

    if (availableSession == null) {
      log.info("Create new GameSession");
      availableSession = createNewGame();
      activeGameSessions.add(availableSession);
    }

    availableSession.join(player);
    log.info(player + " joined " + availableSession);
  }
}
