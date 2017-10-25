package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.Explosion;
import ru.atom.model.Wall;
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
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Bomb(1, 3, 5));
        gameSession.addGameObject(new Player(3, 3, 1));
        gameSession.addGameObject(new Wall(2, 3, false));
        gameSession.addGameObject(new Wall(4, 3, true));
        gameSession.addGameObject(new Explosion(5, 3, 5));
        gameSession.addGameObject(new Bonus(5, 3, 5));


        gameSession.addGameObject(new Bomb(11, 5, 5));
        gameSession.addGameObject(new Player(31, 5, 1));
        gameSession.addGameObject(new Wall(21, 5, false));
        gameSession.addGameObject(new Wall(41, 5, true));
        gameSession.addGameObject(new Explosion(51, 5, 5));
        gameSession.addGameObject(new Bonus(5, 3, 5));

        return gameSession;
    }
}
