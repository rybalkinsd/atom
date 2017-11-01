package ru.atom.boot.mm;

import org.junit.Test;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.MatchMaker;

import static org.junit.Assert.assertEquals;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        ConnectionQueue.getInstance().clear();
        GameRepository.getAll().clear();
        Thread thread = new Thread(new MatchMaker());
        thread.start();

        ConnectionQueue.getInstance().offer(new Connection(1, "a"));
        ConnectionQueue.getInstance().offer(new Connection(2, "b"));
        ConnectionQueue.getInstance().offer(new Connection(3, "c"));
        ConnectionQueue.getInstance().offer(new Connection(4, "d"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("[GameSession{connections=[Connection{playerId=1, name='a'}," +
                " Connection{playerId=2, name='b'}," +
                " Connection{playerId=3, name='c'}," +
                " Connection{playerId=4, name='d'}], id=0}]", new GameController().list());
        thread.interrupt();
    }

}