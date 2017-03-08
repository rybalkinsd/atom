package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Wall;
import ru.atom.model.Offer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Player(new Point(0, 0), 1));
        gameSession.addGameObject(new Player(new Point(10, 10), 1));
        gameSession.addGameObject(new Wall(new Point(20, 20)));
        gameSession.addGameObject(new Wall(new Point(30, 30)));
        gameSession.addGameObject(new Bomb(new Point(40, 40)));
        gameSession.addGameObject(new Bomb(new Point(50, 50)));
        gameSession.addGameObject(new Offer(new Point(60, 60), Offer.Type.SPEED));
        gameSession.addGameObject(new Offer(new Point(70, 70), Offer.Type.STRENGTH));
        return gameSession;
    }
}
