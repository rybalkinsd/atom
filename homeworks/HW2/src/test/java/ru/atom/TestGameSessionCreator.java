package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.Bonus;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.TreeBlock;
import ru.atom.model.WallBlock;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(5, 5, 1000));
        gameSession.addGameObject(new Bomb(10, 1, 1500));
        gameSession.addGameObject(new Girl(3,3,2));
        gameSession.addGameObject(new Girl(12,12,3));
        gameSession.addGameObject(new TreeBlock(7,7));
        gameSession.addGameObject(new TreeBlock(1,1));
        gameSession.addGameObject(new WallBlock(8,4));
        gameSession.addGameObject(new WallBlock(8,5));
        gameSession.addGameObject(new Bonus(1,2));
        gameSession.addGameObject(new Bonus(2,1));
        return gameSession;
    }
}
