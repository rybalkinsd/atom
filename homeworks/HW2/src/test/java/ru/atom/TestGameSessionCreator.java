package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.GameField;
import ru.atom.model.Girl;
import ru.atom.model.Wall;
import ru.atom.model.GameSession;
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

        Girl girl0 = new Girl(new Point(1, 1), 0);
        Girl girl1 = new Girl(new Point(15, 1), 1);
        Wall wall0 = new Wall(new Point(3, 1), true, 0);
        Wall wall1 = new Wall(new Point(3, 2), false, 1);
        Bomb bomb0 = new Bomb(new Point(2, 1), 0);
        Bomb bomb1 = new Bomb(new Point(1, 2), 1);
        GameField gamefield1 = new GameField(0);
        GameField gamefield2 = new GameField(1);

        gameSession.addGameObject(girl0);
        gameSession.addGameObject(girl1);
        gameSession.addGameObject(wall0);
        gameSession.addGameObject(wall1);
        gameSession.addGameObject(bomb0);
        gameSession.addGameObject(bomb1);
        gameSession.addGameObject(gamefield1);
        gameSession.addGameObject(gamefield2);

        return gameSession;
    }
}
