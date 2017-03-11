package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.Box;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Wall;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Girl(0, 0, 1));
        gameSession.addGameObject(new Girl(1, 1, 1));

        gameSession.addGameObject(new Bomb(2, 2, 2));
        gameSession.addGameObject(new Bomb(3, 3, 3));

        gameSession.addGameObject(new Box(4, 4));
        gameSession.addGameObject(new Box(5, 5));
        gameSession.addGameObject(new Wall(6, 6));
        gameSession.addGameObject(new Wall(7, 7));
        return gameSession;
    }
}
