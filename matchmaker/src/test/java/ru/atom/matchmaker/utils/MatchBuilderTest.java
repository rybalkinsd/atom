package ru.atom.matchmaker.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MatchBuilderTest {

    @Test
    public void getGameId() throws Exception {
        MatchBuilder matchBuilder = new MatchBuilder(4, 1);
        assertEquals(matchBuilder.getGameId(), 1);
    }

    @Test
    public void putLogin() throws Exception {
        MatchBuilder matchBuilder = new MatchBuilder(1, 1);
        assertTrue(matchBuilder.putLogin("Admin"));
    }

    @Test
    public void isReady() throws Exception {
        MatchBuilder matchBuilder = new MatchBuilder(2, 1);
        matchBuilder.putLogin("user1");
        matchBuilder.putLogin("user2");
        assertTrue(matchBuilder.isReady());
    }

    @Test
    public void getLogins() throws Exception {
        MatchBuilder matchBuilder = new MatchBuilder(1, 1);
        matchBuilder.putLogin("user");
        assertEquals(matchBuilder.getLogins().toString(), "[user]");
    }

}