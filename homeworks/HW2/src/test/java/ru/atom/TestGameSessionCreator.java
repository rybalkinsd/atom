package ru.atom;

import ru.atom.model.*;
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
        gameSession.addGameObject(new Girl(6, 6));
        gameSession.addGameObject(new Bomb(7, 7));
        gameSession.addGameObject(new Fire(5, 5));
        gameSession.addGameObject(new Block(3, 3));
        gameSession.addGameObject(new EverlastingBox(2, 2));
        return gameSession;
    }
}
