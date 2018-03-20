package ru.technosphere;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BullsNCowsGameTest {
    @Test
    public void testGetBullsNCows() {
        BullsNCowsGame bullsNCowsGame = new BullsNCowsGame();
        BullsNCowsGame.BullsNCowsResult result = bullsNCowsGame.getBullsNCows("1234", "4321");
        assertEquals(result.getBull(), 0);
        assertEquals(result.getCows(), 4);
        result = bullsNCowsGame.getBullsNCows("atom", "java");
        assertEquals(result.getCows(), 1);
        assertEquals(result.getBull(), 0);
        result = bullsNCowsGame.getBullsNCows("lava", "java");
        assertEquals(result.getCows(), 0);
        assertEquals(result.getBull(), 3);
        result = bullsNCowsGame.getBullsNCows("java", "java");
        assertEquals(result.getCows(), 0);
        assertEquals(result.getBull(), 4);
        result = bullsNCowsGame.getBullsNCows("lala", "aaaa");
        assertEquals(result.getBull(), 2);
        assertEquals(result.getCows(), 0);
        result = bullsNCowsGame.getBullsNCows("baaaa", "alala");
        assertEquals(result.getBull(), 2);
        assertEquals(result.getCows(), 1);
        result = bullsNCowsGame.getBullsNCows("aab", "aba");
        assertEquals(result.getBull(), 1);
        assertEquals(result.getCows(), 2);
    }
}
