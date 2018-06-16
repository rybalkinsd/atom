package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.ConnectionController;
import ru.atom.mm.service.MatchMaker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(Config.class)
public class ConnectionControllerTest {

    @Autowired
    private ConnectionController connectionHandler;

    @Autowired
    private MatchMaker matchMaker;

    @Test
    public void connect() throws Exception {
        matchMaker.clearCandidates();

        assertTrue(connectionHandler.list().isEmpty());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        Thread.sleep(50);
        assertFalse(connectionHandler.list().isEmpty());
    }

    @Test
    public void list() throws Exception {
        matchMaker.clearCandidates();

        assertTrue(connectionHandler.list().isEmpty());

        connectionHandler.connect(11, "a");
        connectionHandler.connect(22, "b");
        connectionHandler.connect(33, "c");
        Thread.sleep(50);
        assertTrue(connectionHandler.list().contains("Connection{playerId=11, name='a'}"));
        assertTrue(connectionHandler.list().contains("Connection{playerId=22, name='b'}"));
        assertTrue(connectionHandler.list().contains("Connection{playerId=33, name='c'}"));
    }
}