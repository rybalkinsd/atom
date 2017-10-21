package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bonus;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Wall;
import ru.atom.model.Box;
import ru.atom.model.Bomb;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        Player girl = new Player(new Point(0, 0), Player.Type.GIRL);
        Player boy = new Player(new Point(0,0), Player.Type.BOY);
        gameSession.addGameObject(boy);
        gameSession.addGameObject(girl);

        Wall wall1 = new Wall(new Point(20,25));
        Wall wall2 = new Wall(new Point(40,50));
        gameSession.addGameObject(wall1);
        gameSession.addGameObject(wall2);

        Box box1 = new Box(new Point(24,35));
        Box box2 = new Box(new Point(14,25));
        gameSession.addGameObject(box1);
        gameSession.addGameObject(box2);

        Bonus bonus1 = new Bonus(new Point(37, 89), Bonus.Type.BOMB);
        Bonus bonus2 = new Bonus(new Point(56, 77), Bonus.Type.RANGE);
        Bonus bonus3 = new Bonus(new Point(65, 98), Bonus.Type.SPEED);
        gameSession.addGameObject(bonus1);
        gameSession.addGameObject(bonus2);
        gameSession.addGameObject(bonus3);

        Bomb bomb1 = new Bomb(new Point(40, 67));
        Bomb bomb2 = new Bomb(new Point(37, 55));
        gameSession.addGameObject(bomb1);
        gameSession.addGameObject(bomb2);

        return gameSession;
    }
}
