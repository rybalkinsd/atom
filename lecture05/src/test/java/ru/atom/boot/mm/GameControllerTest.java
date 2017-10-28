package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.MatchMaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        ConnectionQueue.getInstance().clear();
        GameRepository.getAll().clear();

        ConnectionQueue.getInstance().offer(new Connection(1, "a"));
        ConnectionQueue.getInstance().offer(new Connection(2, "b"));
        ConnectionQueue.getInstance().offer(new Connection(3, "c"));
        ConnectionQueue.getInstance().offer(new Connection(4, "d"));
        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName(MatchMaker.class.getSimpleName());

        matchMaker.start();

        matchMaker.join(10_000);

        matchMaker.interrupt();

        assertEquals("[GameSession{connections=[Connection{playerId=1, name='a'}," +
                " Connection{playerId=2, name='b'}," +
                " Connection{playerId=3, name='c'}," +
                " Connection{playerId=4, name='d'}], id=0}]", new GameController().list());
    }

}