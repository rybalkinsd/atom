package model;

import matchmaker.MatchMakerMultiplayer;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivan on 22.11.16.
 */
public class GameSessionImplTest {

    @Test
    public void getLeaders() throws Exception {

        MatchMakerMultiplayer matchMaker = new MatchMakerMultiplayer();

        Player nagibator = new Player(99, "nagibator");
        nagibator.setScore(1000);
        matchMaker.joinGame(nagibator);

        Player looser = new Player(98, "looser");
        looser.setScore(1);
        matchMaker.joinGame(looser);

        IntStream.range(1, 3).forEach(each -> matchMaker.joinGame(new Player(each, Integer.toString(each))));

        Player middle = new Player(97, "middle");
        middle.setScore(700);
        matchMaker.joinGame(middle);

        GameSession session = matchMaker.getActiveGameSessions().get(0);
        assertEquals(session.getLeaders()[0], "nagibator");
        assertEquals(session.getLeaders()[1], "middle");
        assertEquals(session.getLeaders()[2], "looser");
    }

}