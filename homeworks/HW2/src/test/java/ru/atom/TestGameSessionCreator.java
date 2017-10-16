package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.Obstacle;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Girl(gameSession.getNewId(), new Point(1, 1), 2));
        gameSession.addGameObject(new Girl(gameSession.getNewId(), new Point(3, 5), 8));
        gameSession.addGameObject(new Bomb(gameSession.getNewId(), 13, new Point(21, 34)));
        gameSession.addGameObject(new Bomb(gameSession.getNewId(), 55, new Point(89, 144)));
        gameSession.addGameObject(new Bomb(gameSession.getNewId(), 233, new Point(3777, 610)));
        gameSession.addGameObject(new Obstacle(gameSession.getNewId(), new Point(987, 1597)));
        gameSession.addGameObject(new Obstacle(gameSession.getNewId(), new Point(2584, 4181)));
        return gameSession;
    }
}
