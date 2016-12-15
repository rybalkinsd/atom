package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.*;
import java.util.ArrayList;
import java.util.List;

public class MatchMakerImpl implements MatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerImpl.class);
  @NotNull
  private final List<GameSession> activeGameSessions = new ArrayList<>();

  @Override
  public void joinGame(@NotNull Player player) {
    GameSession newGameSession = createNewGame();
    activeGameSessions.add(newGameSession);
    newGameSession.join(player);
    if (log.isInfoEnabled()) {
      log.info(player + " joined " + newGameSession);
    }
  }

  @Override
  public void leaveGame(@NotNull Player player){
    getGameSession(player.getName()).leave(player);
  }

  @Override
  public GameSession getGameSession(String name){
    for (GameSession session : activeGameSessions){
      for (Player player : session.getPlayers()){
        if (player.getName().equals(name)) {
          return session;
        }
      }
    }
    return null;
  }

  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return new ArrayList<>(activeGameSessions);
  }

  private
  @NotNull
  GameSession createNewGame() {
    PlayerPlacer playerPlacer = new RandomPlayerPlacer();
    VirusGenerator virusGenerator = new RandomVirusGenerator(GameConstants.NUMBER_OF_VIRUSES);
    UniformFoodGenerator foodGenerator = new UniformFoodGenerator(GameConstants
            .FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
    return new GameSessionImpl(foodGenerator, playerPlacer, virusGenerator);
  }
}