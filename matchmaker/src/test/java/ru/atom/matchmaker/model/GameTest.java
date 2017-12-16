package ru.atom.matchmaker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameTest {
    Game game;
    GameStatus gameStatus;
    Set<Player> players;

    @Before
    public void init() {
        game = new Game();
        game.setId(1);
        gameStatus = new GameStatus();
        game.setStatus(gameStatus);
        players = new HashSet<>();
        game.setPlayers(players);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(game.getId(), 1);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(game.getStatus(), gameStatus);
    }

    @Test
    public void getPlayers() throws Exception {
        assertEquals(game.getPlayers(), players);
    }

}