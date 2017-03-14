package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.Bonus;
import ru.atom.model.Box;
import ru.atom.model.Player;
import ru.atom.model.Wall;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Wall(new Point(0, 0)));
        gameSession.addGameObject(new Wall(new Point(4, 4)));
        gameSession.addGameObject(new Box(new Point(2, 5)));
        gameSession.addGameObject(new Box(new Point(12, 51)));
        gameSession.addGameObject(new Bomb(new Point(1,1), 6000));
        gameSession.addGameObject(new Bomb(new Point(5,3), 6000));
        gameSession.addGameObject(new Bonus(new Point(1, 10), Bonus.BonusType.SNEAKERS));
        gameSession.addGameObject(new Bonus(new Point(12, 1), Bonus.BonusType.BURST));
        gameSession.addGameObject(new Player(new Point(1, 10), Player.PlayerType.BOY, 1));
        gameSession.addGameObject(new Player(new Point(12, 1), Player.PlayerType.GIRL, 3));
        return gameSession;
    }
}
