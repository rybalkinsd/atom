package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.GameController;
import ru.atom.mm.model.Connection;
import ru.atom.mm.model.GameSession;
import ru.atom.mm.service.GameRepository;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(Config.class)
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void list() throws Exception {
        assertTrue(gameController.list().equals("[]"));
        Connection[] connections = {new Connection(1, "a"), new Connection(2, "b"),
                                    new Connection(3,"c"), new Connection(4, "d")};
        gameRepository.put(new GameSession(connections));
        assertTrue(gameController.list().contains("Connection{playerId=1, name='a'}"));
        assertTrue(gameController.list().contains("Connection{playerId=2, name='b'}"));
        assertTrue(gameController.list().contains("Connection{playerId=3, name='c'}"));
        assertTrue(gameController.list().contains("Connection{playerId=4, name='d'}"));
    }
}