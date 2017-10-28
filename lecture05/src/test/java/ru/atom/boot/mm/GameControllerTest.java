package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    @Ignore
    public void list() throws Exception {
        ConnectionQueue.getInstance().clear();
        GameRepository.getAll().clear();
        ConnectionQueue.getInstance().offer(new Connection(1, "a"));
        ConnectionQueue.getInstance().offer(new Connection(1, "b"));
        ConnectionQueue.getInstance().offer(new Connection(1, "c"));
        ConnectionQueue.getInstance().offer(new Connection(1, "d"));

        assertEquals("[GameSession{connections = [Connection{playerId=1, name='a'}," +
                "Connection[playerId=2, name='b'},"  + "Connection[playerId=3, name='c'}," +
                "Connection[playerId=4, name='d'}], id=0]}", new GameController().list());
    }

}