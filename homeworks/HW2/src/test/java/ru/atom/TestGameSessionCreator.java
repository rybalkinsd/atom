package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.StableBox;
import ru.atom.model.TemporaryBox;
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
        gameSession.addGameObject(new Girl(new Point(1, 1)));
        gameSession.addGameObject(new Girl(new Point(1, 5)));
        gameSession.addGameObject(new Bomb(new Point(2, 3)));
        gameSession.addGameObject(new Bomb(new Point(4, 1)));
        gameSession.addGameObject(new StableBox(new Point(1, 2)));
        gameSession.addGameObject(new StableBox(new Point(3, 4)));
        gameSession.addGameObject(new TemporaryBox(new Point(4,3)));
        gameSession.addGameObject(new TemporaryBox(new Point(3,5)));
        return gameSession;
    }
}
