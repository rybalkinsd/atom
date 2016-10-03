package matchmaker;

import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alpi
 * @since 03.10.16
 */
public class SinglePlayerMatchMaker implements MatchMaker {
  private final List<Game> activeGames = new ArrayList<Game>();
  @Override
  public void joinGame(Player player) {
    Game newGame = createNewGame();
    assert(newGame != null);//java.lang.AssertionError here, because createNewGame() is not implemented
    activeGames.add(newGame);
    newGame.join(player);//java.lang.NullPointerException if -ea (enable assertions) is off
  }

  @Override
  public List<Game> getActiveGames() {
    return new ArrayList<Game>(activeGames);
  }

  /**
   * TODO HOME WORK 1. Instantiate server game state
   * @return new Game
   */
  private Game createNewGame(){
    return null;
  }
}
