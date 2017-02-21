package matchMakerTests;

import matchmaker.MatchMaker;
import matchmaker.MatchMakerImpl;
import model.Player;
import org.junit.Test;

import org.junit.Assert;

/**
 * @author Alpi
 */
public class SinglePlayerMatchMakerTest {
  @Test
  public void testSinglePlayerGameSessionCreated() {
    MatchMaker singlePlayerMatchMaker = new MatchMakerImpl();
    Player player = new Player("Arkady", 1);
    singlePlayerMatchMaker.joinGame(player);

    Assert.assertEquals(1, singlePlayerMatchMaker.getActiveGameSessions().size());
    Assert.assertNotNull(singlePlayerMatchMaker.getActiveGameSessions().get(0));
  }
}
