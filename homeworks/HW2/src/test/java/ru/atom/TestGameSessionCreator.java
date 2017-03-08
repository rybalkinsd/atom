package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Brick;
import ru.atom.model.Wall;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {}

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Girl(1, 1));
        gameSession.addGameObject(new Girl(2, 2));
        gameSession.addGameObject(new Bomb(2, 2));
        gameSession.addGameObject(new Bomb(3, 3));
        gameSession.addGameObject(new Wall(1, 2));
        gameSession.addGameObject(new Wall(2, 1));
        gameSession.addGameObject(new Brick(3, 2));
        gameSession.addGameObject(new Brick(2, 3));
        return gameSession;
    }
}
