package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        Connection[] connections = new Connection[3];
        connections[0] = new Connection(1, "a");
        connections[1] = new Connection(2, "b");
        connections[2] = new Connection(3, "c");
        GameRepository.put(new GameSession(connections));
        GameController gameController = new GameController();
        assertFalse(gameController.list().isEmpty());
    }

}