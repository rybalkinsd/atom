package ru.atom;

import ru.atom.model.GameSession;


import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.BomberGirl;
import ru.atom.model.Box;
/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }


    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new BomberGirl(new Point(1, 1), 1));
        gameSession.addGameObject(new BomberGirl(new Point(2, 2), 2));

        gameSession.addGameObject(new Bomb(new Point(3, 3), 3L));
        gameSession.addGameObject(new Bomb(new Point(0, 0), 40L));

        gameSession.addGameObject(new Box(new Point(4, 4)));
        gameSession.addGameObject(new Box(new Point(5, 5)));
        return gameSession;
    }



}
