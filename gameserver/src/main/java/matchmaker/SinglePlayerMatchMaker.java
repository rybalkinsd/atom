package matchmaker;

import model.GameSession;
import model.Player;
import model.SingleGameSession;
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
//Ввиду того, что не совсем пока ясно точное разделения данных на те, что на сервере и у игрока,
//не разделил на соответствующие подгруппы
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
      log.info(player + " joined " + newGameSession);
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
    /*for(int i=0; i<activeGameSessions.size(); i++){
      if(activeGameSessions.get(i).FreePlace())
       return activeGameSessions.get(i);
    }*/  //для многопользовательской игры

    //Создаем игровую сессию для нового игрока
    GameSession newGameSession = new SingleGameSession();
    if (log.isInfoEnabled()) {
      log.info(newGameSession + "was created");
    }
    return newGameSession;
  }
}
