package ru.atom;

import ru.atom.model.*;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private TestGameSessionCreator() {

    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        gameSession.addGameObject(new Field());
        gameSession.addGameObject(new Stone(0, 0));
        gameSession.addGameObject(new Stone(0,0));
        gameSession.addGameObject(new Wood(0,0));
        gameSession.addGameObject(new Wood(0,0));
        gameSession.addGameObject(new Player(0,0));
        gameSession.addGameObject(new Player(0,0));
        gameSession.addGameObject(new Bomb(0,0));
        gameSession.addGameObject(new Bomb(0,0));
        gameSession.addGameObject(new Flame(0,0));
        gameSession.addGameObject(new Flame(0,0));
        gameSession.addGameObject(new SpeedBonus(0,0));
        gameSession.addGameObject(new SpeedBonus(0,0));
        gameSession.addGameObject(new FlameBonus(0,0));
        gameSession.addGameObject(new FlameBonus(0,0));
        gameSession.addGameObject(new BombBonus(0,0));
        gameSession.addGameObject(new BombBonus(0,0));
        return gameSession;
    }
}
