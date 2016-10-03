package matchMakerTests;

import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.Player;
import org.junit.Test;

import org.junit.Assert;

/**
 * @author Alpi
 * @since 03.10.16
 */
public class SinglePlayerMatchMakerTest {
  @Test
  public void testSinglePlayerMatchMaker(){
    MatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
    Player player = new Player();
    singlePlayerMatchMaker.joinGame(player);

    Assert.assertEquals(1, singlePlayerMatchMaker.getActiveGames().size());
    Assert.assertNotNull(singlePlayerMatchMaker.getActiveGames().get(0));
  }
}
