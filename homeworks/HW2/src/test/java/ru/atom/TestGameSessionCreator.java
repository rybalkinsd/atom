package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Box;
import ru.atom.model.Fire;
import ru.atom.model.Player;
import ru.atom.model.Wall;
import ru.atom.model.Bonus;
import ru.atom.model.Bomb;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        gameSession.addGameObject(new Player(30, 40));
        gameSession.addGameObject(new Player(50, 40));

        gameSession.addGameObject(new Wall(30, 60));
        gameSession.addGameObject(new Wall(30, 60));

        gameSession.addGameObject(new Box(30, 70));
        gameSession.addGameObject(new Box(50, 70));

        gameSession.addGameObject(new Fire(10, 40));
        gameSession.addGameObject(new Fire(10, 60));

        gameSession.addGameObject(new Bonus(60, 10));
        gameSession.addGameObject(new Bonus(50, 10));

        gameSession.addGameObject(new Bomb(70, 100));
        gameSession.addGameObject(new Bomb(90, 100));

        return gameSession;
    }
}
