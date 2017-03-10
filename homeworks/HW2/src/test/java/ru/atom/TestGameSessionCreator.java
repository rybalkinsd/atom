package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        Bomb bomb = new Bomb();
        gameSession.addGameObject(bomb);
        return gameSession;
    }
}
