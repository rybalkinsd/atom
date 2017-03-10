package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Bomb;
import ru.atom.model.Bonus;
import ru.atom.model.Box;
import ru.atom.model.Brick;
import ru.atom.model.Explosion;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Player(gameSession.getId(), new Point(25, 25)));
        gameSession.addGameObject(new Player(gameSession.getId(), new Point(44, 38)));
        gameSession.addGameObject(new Bonus(gameSession.getId(), new Point(2, 84), Bonus.TypesOfBonus.SPEED));
        gameSession.addGameObject(new Bonus(gameSession.getId(), new Point(64, 11), Bonus.TypesOfBonus.POWER));
        gameSession.addGameObject(new Bomb(gameSession.getId(), 1, new Point(44, 25)));
        gameSession.addGameObject(new Bomb(gameSession.getId(), 1, new Point(21, 17)));
        gameSession.addGameObject(new Box(gameSession.getId(), new Point(30, 80)));
        gameSession.addGameObject(new Box(gameSession.getId(), new Point(10, 6)));
        gameSession.addGameObject(new Brick(gameSession.getId(), new Point(40, 15)));
        gameSession.addGameObject(new Brick(gameSession.getId(), new Point(40, 35)));
        gameSession.addGameObject(new Explosion(gameSession.getId(), 1, new Point(20, 15)));
        gameSession.addGameObject(new Explosion(gameSession.getId(), 1, new Point(60, 31)));
        return gameSession;
    }
}
