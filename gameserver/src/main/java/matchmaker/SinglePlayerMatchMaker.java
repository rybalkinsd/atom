package matchmaker;

import model.GameSession;
import model.ImplGameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
  @NotNull
  private final HashMap<Player, Boolean> ingame = new HashMap<>();

  /**
   * Creates new GameSession for single player
   *
   * @param player single player
   */
  @Override
  public void joinGame(@NotNull Player player) {
    try {
      if (!ingame.containsKey(player)) {
        GameSession newGameSession = createNewGame();
        //if(!activeGameSessions.contains(newGameSession))
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        ingame.put(player, true);
        if (log.isInfoEnabled()) {
          log.info(player + " joined " + newGameSession);
        }
      } else if (!ingame.get(player)) {
        GameSession newGameSession = createNewGame();
        //if(!activeGameSessions.contains(newGameSession))
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        ingame.replace(player,true);
        if (log.isInfoEnabled()) {
          log.info(player + " joined " + newGameSession);
        }
      }
      else
      log.info(player + "is already in game");
    } catch (Exception ex) {
      log.info("Something going wrogn");
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
   /* for(int i=0; i<activeGameSessions.size(); i++){
      if(activeGameSessions.get(i).FreePlace())
        return activeGameSessions.get(i);
    }*/

    GameSession newGameSession = new ImplGameSession();
    if (log.isInfoEnabled()) {
      log.info(newGameSession + "was created");
    }
    return newGameSession;
  }
  }

