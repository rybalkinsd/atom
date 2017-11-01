package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        GameRepository.put(new GameSession(null));


        ConnectionController connectionHandler = new ConnectionController();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        connectionHandler.connect(4, "d");

        ConnectionController connectionController = new ConnectionController();
        GameController gameController = new GameController();

        for (int i = 0; i < 4; i++) {
            System.out.println(ConnectionQueue.getInstance().poll().toString());
        }
        System.out.println(gameController.list());

        //assertTrue(/*connectionController.list() == */true);
        assertFalse(gameController.list().isEmpty());

    }

}