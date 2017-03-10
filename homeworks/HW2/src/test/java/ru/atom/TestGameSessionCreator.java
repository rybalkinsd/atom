package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.GameSession;
import ru.atom.model.Player;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Player(1, 1));
        gameSession.addGameObject(new Player(10, 10));
        gameSession.addGameObject(new Bomb(3, 4));
        return gameSession;
    }
}
