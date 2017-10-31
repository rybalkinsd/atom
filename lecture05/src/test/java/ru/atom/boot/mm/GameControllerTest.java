package ru.atom.boot.mm;


import org.junit.Test;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameRepository;
import ru.atom.thread.mm.GameSession;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class GameControllerTest {

    @Test
    public void list() throws Exception {
        ConnectionQueue.getInstance().clear();
        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        candidates.add(new Connection(1, "a"));
        candidates.add(new Connection(2, "b"));
        candidates.add(new Connection(3, "c"));
        candidates.add(new Connection(4, "d"));
        GameSession session = new GameSession(candidates.toArray(new Connection[0]));
        GameRepository.put(session);
        assertEquals("[" + session + "]", new GameController().list().toString());

    }

}