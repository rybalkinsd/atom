package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.Box;
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
        gameSession.addGameObject(new Girl(1, new Point(4, 5), 4));
        gameSession.addGameObject(new Girl(2, new Point(1, 1), 3));
        gameSession.addGameObject(new Bomb(3, 4, new Point(5, 5)));
        gameSession.addGameObject(new Bomb(4, 4, new Point(1, 3)));
        gameSession.addGameObject(new Bomb(5, 4, new Point(7, 7)));
        gameSession.addGameObject(new Box(6, new Point(1, 2)));
        gameSession.addGameObject(new Box(7, new Point(1, 3)));
        return gameSession;
    }
}
