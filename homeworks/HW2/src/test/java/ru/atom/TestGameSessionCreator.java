package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Fire;
import ru.atom.model.Brick;
import ru.atom.model.Wall;
import ru.atom.model.GameSession;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Girl(gameSession, new Point(1, 1)));
        gameSession.addGameObject(new Girl(gameSession, new Point(3, 1)));
        gameSession.addGameObject(new Brick(gameSession, new Point(2, 2)));
        gameSession.addGameObject(new Brick(gameSession, new Point(10, 1)));
        gameSession.addGameObject(new Wall(gameSession, new Point(2, 1)));
        gameSession.addGameObject(new Wall(gameSession, new Point(3, 3)));
        gameSession.addGameObject(new Fire(gameSession, new Point(5, 5)));
        gameSession.addGameObject(new Fire(gameSession, new Point(6, 3)));
        gameSession.addGameObject(new Bomb(gameSession, new Point(6, 4)));
        gameSession.addGameObject(new Bomb(gameSession, new Point(7, 7)));
        return gameSession;
    }
}
