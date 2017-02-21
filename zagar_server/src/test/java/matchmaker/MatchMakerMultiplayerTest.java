package matchmaker;

import model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by eugene on 11/22/16.
 */
public class MatchMakerMultiplayerTest {
  @Test
  public void joinGame() throws Exception {
    MatchMakerMultiplayer matchMakerMultiplayer = new MatchMakerMultiplayer();

    Player player = new Player(0, "test1");
    Player player1 = new Player(1, "test2");

    assertEquals(matchMakerMultiplayer.getActiveGameSessions().size(), 0);

    matchMakerMultiplayer.joinGame(player);
    assertEquals(1, matchMakerMultiplayer.getActiveGameSessions().size());

    matchMakerMultiplayer.joinGame(player1);
    assertEquals(1, matchMakerMultiplayer.getActiveGameSessions().size());
  }

}