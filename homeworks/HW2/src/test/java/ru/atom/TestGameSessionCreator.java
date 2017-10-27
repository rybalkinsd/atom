package ru.atom;

import ru.atom.model.*;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(0, 0));
        gameSession.addGameObject(new Bomb(10, 10));
        gameSession.addGameObject(new GameUnit(0, 0));
        gameSession.addGameObject(new GameUnit(5, 5));
        gameSession.addGameObject(new Wall(0, 0));
        gameSession.addGameObject(new Wall(20, 20));
        gameSession.addGameObject(new Field(1000, 1000));
        return gameSession;
    }
}
