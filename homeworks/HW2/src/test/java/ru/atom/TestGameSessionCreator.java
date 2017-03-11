package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;

import ru.atom.model.Bomb;
import ru.atom.model.Bomber;
import ru.atom.model.Wall;
import ru.atom.model.Brick;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        Point point1 = new Point(1,3);
        Point point2 = new Point(3,5);
        gameSession.addGameObject(new Wall(0, point1));
        gameSession.addGameObject(new Wall(1, point2));

        gameSession.addGameObject(new Bomb(2,point1,1000));
        gameSession.addGameObject(new Bomb(3, point2, 10000));

        gameSession.addGameObject(new Bomber(4,point1, 1));
        gameSession.addGameObject(new Bomber(5, point2, 2));

        gameSession.addGameObject(new Brick(6, point1));
        gameSession.addGameObject(new Brick(7,point2));

        return gameSession;
    }
}
