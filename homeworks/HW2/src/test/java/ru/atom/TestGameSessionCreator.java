package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import ru.atom.model.Improvment;
import ru.atom.model.Player;
import ru.atom.model.Obstacle;

import static ru.atom.model.Improvment.ImprovmentType.EXPLOSIONRANGE;
import static ru.atom.model.Improvment.ImprovmentType.BOMBMAX;
import static ru.atom.model.Improvment.ImprovmentType.SPEED;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        Player player1 = new Player(new Point(0, 0));
        Player player2 = new Player(new Point(1, 1));
        Bomb bomb1 = new Bomb(new Point(2, 2), player1.getExplosionRange());
        Bomb bomb2 = new Bomb(new Point(3, 3), player2.getExplosionRange());
        Improvment improvment1 = new Improvment(new Point(4, 4), SPEED);
        Improvment improvment2 = new Improvment(new Point(5, 5), BOMBMAX);
        Obstacle obstacle1 = new Obstacle(new Point(6, 6), Obstacle.Durability.DESTRUCTIBLE);
        Obstacle obstacle2 = new Obstacle(new Point(7, 7), Obstacle.Durability.INDESTRUCTIBLE);

        gameSession.addGameObject(player1);
        gameSession.addGameObject(player2);
        gameSession.addGameObject(bomb1);
        gameSession.addGameObject(bomb2);
        gameSession.addGameObject(improvment1);
        gameSession.addGameObject(improvment2);
        gameSession.addGameObject(obstacle1);
        gameSession.addGameObject(obstacle2);
        return gameSession;
    }
}
