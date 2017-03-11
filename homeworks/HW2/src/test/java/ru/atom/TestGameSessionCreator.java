package ru.atom;

import ru.atom.geometry.Bomb;
import ru.atom.model.GameSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ru.atom.geometry.Bomb;
import ru.atom.geometry.Bonus;
import ru.atom.geometry.Box;
import ru.atom.geometry.Fire;
import ru.atom.geometry.Girl;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb (5,5));
        gameSession.addGameObject(new Bomb (0,100));
        gameSession.addGameObject(new Bonus (10, 10));
        gameSession.addGameObject(new Bonus (100, 200));
        gameSession.addGameObject(new Box (50, 50));
        gameSession.addGameObject(new Box (0,0));
        gameSession.addGameObject(new Fire (20, 20));
        gameSession.addGameObject(new Girl (150, 150));
        return  gameSession;
    }
}
