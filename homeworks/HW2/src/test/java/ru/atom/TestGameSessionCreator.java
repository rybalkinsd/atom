package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.BombGirl;
import ru.atom.model.Bomb;
import ru.atom.model.Box;
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
        gameSession.addGameObject(new BombGirl(new Point(1,1), 1));
        gameSession.addGameObject(new BombGirl(new Point(1,2), 3));
        gameSession.addGameObject(new Bomb(new Point(2,2), 2));
        gameSession.addGameObject(new Bomb(new Point(0,4), 3));
        gameSession.addGameObject(new Box(new Point(0, 0)));
        gameSession.addGameObject(new Box(new Point(40, 40)));
        return gameSession;
    }
}
