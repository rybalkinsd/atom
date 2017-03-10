package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Block;
import ru.atom.model.Brick;
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
        gameSession.addGameObject(new Player(0,0));
        gameSession.addGameObject(new Player(100,100));
        gameSession.addGameObject(new Block(10,10));
        gameSession.addGameObject(new Block(20,20));
        gameSession.addGameObject(new Brick(30,30));
        gameSession.addGameObject(new Brick(40,40));
        gameSession.addGameObject(new Bonus(30,30, GameSession.DEFAULT_LIFETIME_OF_BONUS));
        gameSession.addGameObject(new Bonus(40,40, GameSession.DEFAULT_LIFETIME_OF_BONUS));
        gameSession.addGameObject(new Bomb(50,50, GameSession.DEFAULT_LIFETIME_OF_BOMB));
        gameSession.addGameObject(new Bomb(60,60, GameSession.DEFAULT_LIFETIME_OF_BOMB));

        return gameSession;
    }
}
