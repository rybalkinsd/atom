package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Wall;
import ru.atom.model.Bonus;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(0, 0, gameSession.getId(), 1));
        gameSession.addGameObject(new Bomb(2, 2, gameSession.getId(), 5));
        gameSession.addGameObject(new Girl(3, 3, gameSession.getId()));
        gameSession.addGameObject(new Girl(4, 4, gameSession.getId()));
        gameSession.addGameObject(new Wall(5, 4, gameSession.getId()));
        gameSession.addGameObject(new Wall(4, 5, gameSession.getId()));
        gameSession.addGameObject(new Bonus(7, 7, gameSession.getId(), Bonus.Type.SPEED));
        gameSession.addGameObject(new Bonus(2, 2, gameSession.getId(), Bonus.Type.BOMBPOWER));
        return gameSession;
    }
}
