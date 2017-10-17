package ru.atom;

import ru.atom.geometry.Bomb;
import ru.atom.geometry.Brick;
import ru.atom.geometry.Explosion;
import ru.atom.geometry.Player;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.geometry.Wall;
import ru.atom.model.GameSession;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Wall(gameSession, new Point(50, 50),
                                  new Rectangle(0,100,0,100),1));
        gameSession.addGameObject(new Player(gameSession, new Point(2, 2),
                new Rectangle(1,3,1,3),2));
        gameSession.addGameObject(new Player(gameSession, new Point(98, 98),
                new Rectangle(97,99,97,99),3));
        gameSession.addGameObject(new Brick(gameSession, new Point(10, 10),
                new Rectangle(9,11,9,11),4));
        gameSession.addGameObject(new Brick(gameSession, new Point(20, 20),
                new Rectangle(19,21,19,21),5));
        gameSession.addGameObject(new Bomb(gameSession, new Point(30, 30),
                new Rectangle(29,31,29,31),6));
        gameSession.addGameObject(new Explosion(gameSession, new Point(50, 50),
                new Rectangle(45,55,45,55),7));
        return gameSession;
    }
}
