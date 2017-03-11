package ru.atom;

import ru.atom.model.Block;
import ru.atom.model.Bomb;
import ru.atom.model.BombGirl;
import ru.atom.model.WallBlock;
import ru.atom.model.GameSession;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        gameSession.addGameObject(new BombGirl(2,2,3));
        gameSession.addGameObject(new BombGirl(6,2,3));
        gameSession.addGameObject(new Bomb(7,7,3400));
        gameSession.addGameObject(new Bomb(2,8,1000));
        gameSession.addGameObject(new WallBlock(3,3));
        gameSession.addGameObject(new Block(3,4));
        gameSession.addGameObject(new BombGirl(5,2,1000));
        return gameSession;
    }
}
