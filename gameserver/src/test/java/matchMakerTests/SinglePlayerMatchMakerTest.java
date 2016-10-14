package matchMakerTests;

import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alpi
 */
public class SinglePlayerMatchMakerTest {
  @Test
  public void testSinglePlayerGameSessionCreated() {
    MatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
    Player player1 = new Player("Arkady");
    singlePlayerMatchMaker.joinGame(player1);

    Assert.assertEquals(1, singlePlayerMatchMaker.getActiveGameSessions().size());
    Assert.assertNotNull(singlePlayerMatchMaker.getActiveGameSessions().get(0));

  }
}



