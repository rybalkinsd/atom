package ru.atom.matchmaker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player;
    PlayerStatus playerStatus;
    Set<Game> games;

    @Before
    public void init() {
        player = new Player();
        player.setId(1);
        player.setLogin("user");
        player.setPassword("1234");
        player.setWins(5);
        playerStatus = new PlayerStatus();
        player.setStatus(playerStatus);
        games = new HashSet<>();
        player.setGames(games);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(player.getId(), 1);
    }

    @Test
    public void getLogin() throws Exception {
        assertEquals(player.getLogin(), "user");
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(player.getPassword(), "1234");
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(player.getStatus(), playerStatus);
    }

    @Test
    public void getWins() throws Exception {
        assertEquals(player.getWins(), 5);
    }

    @Test
    public void getGames() throws Exception {
        assertEquals(player.getGames(), games);
    }

}