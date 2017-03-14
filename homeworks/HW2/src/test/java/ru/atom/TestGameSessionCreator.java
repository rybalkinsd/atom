package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.BomberGirl;
import ru.atom.model.Box;
import ru.atom.model.GameSession;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new BomberGirl(new Point(1, 1), 5));
        gameSession.addGameObject(new BomberGirl(new Point(10, 10), 6));
        gameSession.addGameObject(new Box(new Point(20, 0)));
        gameSession.addGameObject(new Box(new Point(0, 20)));
        gameSession.addGameObject(new Bomb(new Point(10, 0), 5L));
        gameSession.addGameObject(new Bomb(new Point(0, 10), 10L));
        return gameSession;
    }
}
