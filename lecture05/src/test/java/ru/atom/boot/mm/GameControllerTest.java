package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.GameSession;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        connectionHandler.connect(4, "d");

        GameSession session = new GameSession(ConnectionQueue.getInstance().toArray(new Connection[0]));
        GameRepository.put(session);

        GameController gameController = new GameController();
        assertEquals(gameController.list(), GameRepository.getAll().toString());
    }

}