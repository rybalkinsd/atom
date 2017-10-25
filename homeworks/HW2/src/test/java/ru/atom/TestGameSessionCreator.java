package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Player;
import ru.atom.model.Bonus;
import ru.atom.model.GameSession;
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
        Point point = new Point(20,20);
        //TODO populate your game session with sample objects and log their creation

        gameSession.addGameObject(new Player(1, point));
        gameSession.addGameObject(new Player(2, point));
        gameSession.addGameObject(new Bomb(3, point));
        gameSession.addGameObject(new Bomb(4, point));
        gameSession.addGameObject(new Box(7, point));
        gameSession.addGameObject(new Box(6, point));
        gameSession.addGameObject(new Bonus(7, 1, point));
        gameSession.addGameObject(new Bonus(8, 1, point));
        return gameSession;
    }
}
