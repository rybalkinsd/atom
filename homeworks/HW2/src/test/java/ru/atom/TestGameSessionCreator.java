package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.Box;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Bonus;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        Player player = new Player(new Point(1,10));
        Player player2 = new Player(new Point(10,1));

        gameSession.addGameObject(player);
        gameSession.addGameObject(player2);

        Box box = new Box(new Point(20,0), Box.Type.TMP);
        Box box2 = new Box(new Point(0,20), Box.Type.NotTMP);

        gameSession.addGameObject(box);
        gameSession.addGameObject(box2);

        Bomb bomb = new Bomb(new Point(2,3));
        Bomb bomb2 = new Bomb(new Point(5,7));

        gameSession.addGameObject(bomb);
        gameSession.addGameObject(bomb2);

        Bonus bonus = new Bonus(new Point(14,15));
        Bonus bonus2 = new Bonus(new Point(47,50));

        gameSession.addGameObject(bonus);
        gameSession.addGameObject(bonus2);
        return gameSession;
    }
}
