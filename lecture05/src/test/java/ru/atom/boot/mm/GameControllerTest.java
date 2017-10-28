package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        GameController gameController = new GameController();

        assertEquals("[]", gameController.list());

        GameRepository.put(new GameSession(null));
        GameRepository.put(new GameSession(null));
        GameRepository.put(new GameSession(null));

        assertThat(gameController.list()).isNotEmpty();
    }

}