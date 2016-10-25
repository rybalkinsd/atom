package matchMakerTests;

import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import gamemodel.Player;
import org.junit.Assert;
import org.junit.Test;
import server.model.user.User;

/**
 * @author Alpi
 */
public class SinglePlayerMatchMakerTest {

    @Test
    public void testSinglePlayerGameSessionCreated() {
        MatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
        User user = new User("Arkady", "qdsa");
        Player player = new Player(user);
        singlePlayerMatchMaker.joinGame(player);
        Assert.assertEquals(1, singlePlayerMatchMaker.getActiveGameSessions().size());
        Assert.assertNotNull(singlePlayerMatchMaker.getActiveGameSessions().get(0));
    }

}
