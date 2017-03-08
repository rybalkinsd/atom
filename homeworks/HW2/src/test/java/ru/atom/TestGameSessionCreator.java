package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import ru.atom.model.PlayerObject;
import ru.atom.model.Wall;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession(0);
        gameSession.addGameObject(new PlayerObject(gameSession.getGameObjectId(), 5, new Point(10,10),
                0));
        gameSession.addGameObject(new PlayerObject(gameSession.getGameObjectId(), 17, new Point(12,13),
                0));
        gameSession.addGameObject(new Wall(gameSession.getGameObjectId(), 0, new Point(5,16),
                false));
        gameSession.addGameObject(new Wall(gameSession.getGameObjectId(), 0, new Point(7,7),
                true));
        gameSession.addGameObject(new Bomb(gameSession.getGameObjectId(), new Point(1,2), 7, 0));
        gameSession.addGameObject(new Bomb(gameSession.getGameObjectId(), new Point(2,7), 5, 0));
        return gameSession;
    }
}
