package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
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
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Girl(new Point(1,1),0));
        gameSession.addGameObject(new Bomb(new Point(4,10),100));
        gameSession.addGameObject(new Wall(new Point(1,23),200));
        gameSession.addGameObject(new Brick(new Point(34,23),300));
        gameSession.addGameObject(new Bomb(new Point(23,24),101));
        gameSession.addGameObject(new Girl(new Point(100,100),1));
        gameSession.addGameObject(new Brick(new Point(23,34),301));
        gameSession.addGameObject(new Wall(new Point(12,12),201));
        return gameSession;
    }
}
