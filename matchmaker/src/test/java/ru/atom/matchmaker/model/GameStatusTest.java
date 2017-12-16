package ru.atom.matchmaker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameStatusTest {
    GameStatus gameStatus;
    Set<Game> games;

    @Before
    public void init() {
        gameStatus = new GameStatus();
        gameStatus.setId(1);
        gameStatus.setName("finished");
        games = new HashSet<>();
        games.add(new Game());
        gameStatus.setGames(games);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(gameStatus.getId(), 1);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(gameStatus.getName(), "finished");
    }

    @Test
    public void getGames() throws Exception {
        assertEquals(gameStatus.getGames(), games);
    }

}