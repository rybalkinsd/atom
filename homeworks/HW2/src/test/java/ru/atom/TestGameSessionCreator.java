package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;

import ru.atom.model.GameSession;
import ru.atom.model.Wall;
import ru.atom.model.PlayGround;
import ru.atom.model.Player;
import ru.atom.model.Brick;
import ru.atom.model.Bomb;
import ru.atom.model.Explosion;



/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Wall(new Point(50, 50),
                                  new Rectangle(0,100,0,100),1));

        gameSession.addGameObject(new PlayGround(new Point(50, 50),
                new Rectangle(0,100,0,100),2));

        gameSession.addGameObject(new Player(new Point(2, 2),
                new Rectangle(1,3,1,3),3));

        gameSession.addGameObject(new Player(new Point(98, 98),
                new Rectangle(97,99,97,99),4));

        gameSession.addGameObject(new Brick(new Point(10, 10),
                new Rectangle(9,11,9,11),5));

        gameSession.addGameObject(new Brick(new Point(20, 20),
                new Rectangle(19,21,19,21),6));

        gameSession.addGameObject(new Bomb(new Point(30, 30),
                new Rectangle(29,31,29,31),7));

        gameSession.addGameObject(new Explosion(new Point(50, 50),
                new Rectangle(45,55,45,55),8));

        return gameSession;
    }
}
