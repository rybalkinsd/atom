package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.*;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
//GameSession gameSession = new GameSession();
//TODO populate your game session with sample objects and log their creation
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new BomberGirl(new Point(1, 1)));
        gameSession.addGameObject(new Bomb(new Point(2,2)));
        Brick b = new Brick(new Point(3,3));
        b.tick(5);
        gameSession.addGameObject(b);
        gameSession.addGameObject(new Wall(new Point(4,4)));
        gameSession.addGameObject(new Bonus(new Point(5,5)));

        return gameSession;


// throw new UnsupportedOperationException();
    }
}
