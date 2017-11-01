package ru.atom.boot.mm;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.MatchMaker;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        ConnectionQueue.getInstance().clear();
        assertThat(connectionHandler.list()).isEmpty();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        connectionHandler.connect(4, "d");
        connectionHandler.connect(5, "e");
        connectionHandler.connect(6, "f");
        connectionHandler.connect(7, "g");
        connectionHandler.connect(8, "h");

        GameController gameHandler = new GameController();
        assertEquals("[]", gameHandler.list());

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName("match-maker");
        matchMaker.start();
        matchMaker.join(1000);
        matchMaker.interrupt();

        assertEquals("[GameSession{connections=" +
                '[' + "Connection{playerId=1, name='a'}" + ", " +
                "Connection{playerId=2, name='b'}" + ", " +
                "Connection{playerId=3, name='c'}" + ", " +
                "Connection{playerId=4, name='d'}" + ']' +
                ", " + "id=0}" + ", " +
                "GameSession{connections=" +
                '[' + "Connection{playerId=5, name='e'}" + ", " +
                "Connection{playerId=6, name='f'}" + ", " +
                "Connection{playerId=7, name='g'}" + ", " +
                "Connection{playerId=8, name='h'}" + ']' +
                ", " + "id=1}]", gameHandler.list());
        ConnectionQueue.getInstance().clear();
    }

}