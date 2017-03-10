package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.Box;
import ru.atom.model.Fire;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.StoneColumns;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(2,3,1));
        gameSession.addGameObject(new Bomb(5,8,2));
        gameSession.addGameObject(new Box(3,3));
        gameSession.addGameObject(new Box(6,8));
        gameSession.addGameObject(new Fire(1,1));
        gameSession.addGameObject(new Fire(2,2));
        gameSession.addGameObject(new Player(6,6));
        gameSession.addGameObject(new Player(7,6));
        gameSession.addGameObject(new StoneColumns(0,0));
        gameSession.addGameObject(new StoneColumns(0,1));
        return gameSession;
    }
}
