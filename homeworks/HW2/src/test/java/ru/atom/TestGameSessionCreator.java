package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.PermanentBlock;
import ru.atom.model.TemporaryBlock;
import ru.atom.model.Fire;
import ru.atom.model.Bomb;
import ru.atom.model.PowerUp;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        gameSession.addGameObject(new Player(new Point(1, 2), 42));
        gameSession.addGameObject(new Player(new Point(3, 4), 17));
        gameSession.addGameObject(new PermanentBlock(new Point(5, 6)));
        gameSession.addGameObject(new PermanentBlock(new Point(7, 8)));
        gameSession.addGameObject(new TemporaryBlock(new Point(9, 10)));
        gameSession.addGameObject(new TemporaryBlock(new Point(11, 12)));
        gameSession.addGameObject(new Fire(new Point(12, 11)));
        gameSession.addGameObject(new Fire(new Point(10, 9)));
        gameSession.addGameObject(new Bomb(new Point(8, 7)));
        gameSession.addGameObject(new Bomb(new Point(6, 5)));
        gameSession.addGameObject(new PowerUp(new Point(4, 3)));
        gameSession.addGameObject(new PowerUp(new Point(2, 1)));

        return gameSession;
    }
}
