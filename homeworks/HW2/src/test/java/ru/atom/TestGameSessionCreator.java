package ru.atom;

import ru.atom.geometry.Point;
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
        gameSession.addGameObject(new Girl(new Point(0,0)));
        gameSession.addGameObject(new Girl(new Point(10,10)));
        gameSession.addGameObject(new Bomb(new Point(3,3)));
        gameSession.addGameObject(new Bomb(new Point(5,5)));
        gameSession.addGameObject(new WoodenWall(new Point(3,0)));
        gameSession.addGameObject(new WoodenWall(new Point(0,3)));
        gameSession.addGameObject(new StoneWall(new Point(5,0)));
        gameSession.addGameObject(new StoneWall(new Point(0,5)));
        return  gameSession;
    }
}
