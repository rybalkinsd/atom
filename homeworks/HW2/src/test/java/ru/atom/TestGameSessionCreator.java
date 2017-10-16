package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.model.*;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator extends GameSession {
    private TestGameSessionCreator() {
    }

    private static Logger log = LogManager.getLogger(GameSession.class);

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        log.info("GameSession was created");
        gameSession.addGameObject(new Player(1, new Point (400, 1000)));
        gameSession.addGameObject(new Player(2, new Point(100, 700)));
        gameSession.addGameObject(new Bomb(3, new Point(300,200),1));
        gameSession.addGameObject(new Bomb(4, new Point(400, 200),2));
        gameSession.addGameObject(new Bonus(5, new Point(300, 600), Bonus.BonusType.bombQuantity));
        gameSession.addGameObject(new Bonus(6, new Point(900, 600), Bonus.BonusType.playerVelocity));
        gameSession.addGameObject(new Wall(7, new Point(100, 200)));
        gameSession.addGameObject(new Wall(8, new Point(200, 300)));
        gameSession.addGameObject(new Box(9, new Point(400, 100)));
        gameSession.addGameObject(new Box(10, new Point(500, 100)));
        gameSession.addGameObject(new Explosion(11, new Point(600, 100)));
        gameSession.addGameObject(new Explosion(12, new Point(600, 600)));
        log.info("All objects were created!\n");
        return gameSession;

    }
}
