package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.AbstractGameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.BreakableWall;
import ru.atom.model.UnbreakableWall;
import ru.atom.model.Player;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Bomb(new Point(0, 0)));
        gameSession.addGameObject(new Bomb(new Point(4, 4));

        gameSession.addGameObject(new Player(new Point(1, 1)));
        gameSession.addGameObject(new Player(new Point(8, 8)));

        gameSession.addGameObject(new BreakableWall(new Point(5, 5)));
        gameSession.addGameObject(new BreakableWall(new Point(2, 2)));

        gameSession.addGameObject(new UnbreakableWall(new Point(7, 7)));
        gameSession.addGameObject(new UnbreakableWall(new Point(4, 4)));
        return gameSession;
    }
}
