package ru.atom.controller;

import org.junit.Before;
import org.junit.Test;
import ru.atom.SessionMock;
import ru.atom.network.Player;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by sergey on 2/6/17.
 */
public class GameSessionManagerTest {
    private GameSessionManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new GameSessionManager();
    }

    @Test
    public void newSession() throws Exception {
        Thread managerThread = new Thread(manager);
        assertThat(manager.getGameSessionsNumber()).isEqualTo(0);
        managerThread.start();
        assertThat(manager.getGameSessionsNumber()).isEqualTo(0);
        GameSessionManager.register(new Player("Leonardo", new SessionMock()));
        assertThat(manager.getGameSessionsNumber()).isEqualTo(0);
        GameSessionManager.register(new Player("Michelangelo", new SessionMock()));
        assertThat(manager.getGameSessionsNumber()).isEqualTo(0);
        GameSessionManager.register(new Player("Donatello", new SessionMock()));
        assertThat(manager.getGameSessionsNumber()).isEqualTo(0);
        GameSessionManager.register(new Player("Rafael", new SessionMock()));
        Thread.sleep(10_000);
        assertThat(manager.getGameSessionsNumber()).isGreaterThan(0);
        //managerThread.interrupt();
        //managerThread.join();

    }
}