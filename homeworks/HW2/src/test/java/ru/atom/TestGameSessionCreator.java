package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.BomberGirl;
import ru.atom.model.Bomb;
import ru.atom.model.Wall;
import ru.atom.model.Bonus;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new BomberGirl(new Point(1, 1)));
        gameSession.addGameObject(new BomberGirl(new Point(8,8)));
        gameSession.addGameObject(new Bomb(new Point(2,2)));
        gameSession.addGameObject(new Bomb(new Point(9,9)));
        gameSession.addGameObject(new Wall(new Point(4,4)));
        gameSession.addGameObject(new Wall(new Point(10,10)));
        gameSession.addGameObject(new Bonus(new Point(5,5)));
        gameSession.addGameObject(new Bonus(new Point(11,11)));
        return gameSession;
    }
}
