package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.Explosion;
import ru.atom.model.Wall;
import ru.atom.model.Player;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Bomb(1, new Point(1, 3), 1));
        gameSession.addGameObject(new Player(2, new Point(3, 3), 1));
        gameSession.addGameObject(new Wall(3, new Point(2, 3), false));
        gameSession.addGameObject(new Wall(4, new Point(4, 3), true));
        gameSession.addGameObject(new Explosion(5, new Point(5, 3), 1));

        return gameSession;
    }
}
