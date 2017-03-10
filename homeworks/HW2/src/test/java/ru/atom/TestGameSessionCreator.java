package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bonus;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.Player;
import ru.atom.model.Wall;
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
        gameSession.addGameObject(new Bomb(gameSession.getCurrentId(),new Point(0, 0), 3));
        gameSession.addGameObject(new Bomb(gameSession.getCurrentId(),new Point(5, 0), 3));

        gameSession.addGameObject(new Player(gameSession.getCurrentId(),new Point(2, 4)));
        gameSession.addGameObject(new Player(gameSession.getCurrentId(),new Point(8, 2)));

        gameSession.addGameObject(new Wall(gameSession.getCurrentId(),new Point(8, 3)));
        gameSession.addGameObject(new Wall(gameSession.getCurrentId(),new Point(8, 5)));

        gameSession.addGameObject(new UnbreakableWall(gameSession.getCurrentId(),new Point(8, 8)));
        gameSession.addGameObject(new UnbreakableWall(gameSession.getCurrentId(),new Point(0, 8)));

        gameSession.addGameObject(new Bonus(gameSession.getCurrentId(),new Point(0, 0), Bonus.Type.RANGE));
        gameSession.addGameObject(new Bonus(gameSession.getCurrentId(),new Point(9, 8), Bonus.Type.BOMB));

        return gameSession;
    }
}
