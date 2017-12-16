package ru.atom.matchmaker.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PlayerStatusTest {
    PlayerStatus playerStatus;
    Set<Player> players;

    @Before
    public void init() {
        playerStatus = new PlayerStatus();
        playerStatus.setId(1);
        playerStatus.setName("online");
        players = new HashSet<>();
        players.add(new Player());
        playerStatus.setPlayers(players);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(playerStatus.getId(), 1);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(playerStatus.getName(), "online");
    }

    @Test
    public void getPlayers() throws Exception {
        assertEquals(playerStatus.getPlayers(), players);
    }

}