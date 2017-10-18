package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Block;
import ru.atom.geometry.Point;
/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation

        Girl girl1 = new Girl(0, new Point(0, 0), 1, 0, 0);
        Girl girl2 = new Girl(1, new Point(10, 10), 5, 100, 100);
        gameSession.addGameObject(girl1);
        gameSession.addGameObject(girl2);

        Bomb bomb1 = new Bomb(2, new Point(0, 1), 100);
        Bomb bomb2 = new Bomb(3, new Point(1, 0), 100);
        gameSession.addGameObject(bomb1);
        gameSession.addGameObject(bomb2);

        Block block1 = new Block(4, new Point(1, 1));
        Block block2 = new Block(5, new Point(2, 2));
        gameSession.addGameObject(block1);
        gameSession.addGameObject(block2);

        return gameSession;
    }
}
