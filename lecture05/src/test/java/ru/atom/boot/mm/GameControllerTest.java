package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class GameControllerTest {

    @Test
    //@Ignore
    public void list() throws Exception {
        Connection[] connections = new Connection[2];
        connections[0] = new Connection(1,"x");
        connections[1] = new Connection(2,"y");
        GameController connectionHandler = new GameController();
        GameRepository.put(new GameSession(connections));
        assertThat(connectionHandler.list()).isNotEmpty();
    }

}