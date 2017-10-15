package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Wall;
import ru.atom.model.Box;
import ru.atom.model.Explosion;
import ru.atom.model.Buff;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    private static int id = 0;

    private static int id() {
        return id++;
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Girl(id(), new Point(1,1), 20, 1));
        gameSession.addGameObject(new Girl(id(), new Point(1,1), 20, 1));
        gameSession.addGameObject(new Bomb(id(), new Point(3,3), 1500, 2));
        gameSession.addGameObject(new Bomb(id(), new Point(3,3), 1500, 2));
        gameSession.addGameObject(new Wall(id(), new Point(0,0)));
        gameSession.addGameObject(new Wall(id(), new Point(15,15)));
        gameSession.addGameObject(new Box(id(), new Point(2,1)));
        gameSession.addGameObject(new Box(id(), new Point(2,13)));
        gameSession.addGameObject(new Explosion(id(), new Point(32, 32), 1000));
        gameSession.addGameObject(new Explosion(id(), new Point(30, 32), 1000));
        gameSession.addGameObject(new Buff(id(), new Point(19,20), Buff.BuffType.CAPACITY));
        gameSession.addGameObject(new Buff(id(), new Point(10,20), Buff.BuffType.POWER));

        return gameSession;
    }
}
