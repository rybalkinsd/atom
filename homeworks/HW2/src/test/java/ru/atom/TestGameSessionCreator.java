package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.geometry.Point;
import ru.atom.model.AbstractGameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Bomb;
import ru.atom.model.BreakableWall;
import ru.atom.model.UnbreakableWall;


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
        gameSession.addGameObject(new Bomb(new Point(6, 6)));

        gameSession.addGameObject(new Player(new Point(1, 2)));
        gameSession.addGameObject(new Player(new Point(2, 8)));



        gameSession.addGameObject(new BreakableWall(new Point(4, 5)));
        gameSession.addGameObject(new BreakableWall(new Point(2, 8)));

        gameSession.addGameObject(new UnbreakableWall(new Point(3, 3)));
        gameSession.addGameObject(new UnbreakableWall(new Point(4, 7)));

        return gameSession;
    }
}