package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.BreakableWall;
import ru.atom.model.Character;
import ru.atom.model.GameSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private TestGameSessionCreator() {}

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new BreakableWall(2, 2));
        gameSession.addGameObject(new BreakableWall(2, 3));
        gameSession.addGameObject(new Bomb(3, 2, 3000));
        gameSession.addGameObject(new Bomb(5, 3, 2000));
        gameSession.addGameObject(new Character(1, 5));
        gameSession.addGameObject(new Character(8, 5));
        return gameSession;
    }
}
