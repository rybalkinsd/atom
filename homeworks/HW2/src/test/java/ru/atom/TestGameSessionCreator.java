package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import ru.atom.model.GameUnit;
import ru.atom.model.Wall;

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
        return gameSession;
    }
}
