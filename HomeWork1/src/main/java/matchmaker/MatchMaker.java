package matchmaker;

import model.Game;
import model.Player;

import java.util.List;

/**
 * @author Alpi
 * @since 03.10.16
 */
public interface MatchMaker {
  void joinGame(Player player);
  List<Game> getActiveGames();
}
