package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Brick;
import ru.atom.model.Bomb;
import ru.atom.model.Girl;
import ru.atom.model.Wall;
import ru.atom.model.BonusExplosion;
import ru.atom.model.BonusSpeed;
import ru.atom.model.BonusCountBomb;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Girl(0, 5));
        gameSession.addGameObject(new Girl(5, 0));
        gameSession.addGameObject(new Wall(0, 4));
        gameSession.addGameObject(new Wall(4, 0));
        gameSession.addGameObject(new Bomb(1, 5));
        gameSession.addGameObject(new Bomb(5, 1));
        gameSession.addGameObject(new Brick(2, 3));
        gameSession.addGameObject(new Brick(3, 2));
        gameSession.addGameObject(new BonusExplosion(4, 2));
        gameSession.addGameObject(new BonusExplosion(2, 4));
        gameSession.addGameObject(new BonusCountBomb(4, 5));
        gameSession.addGameObject(new BonusCountBomb(5, 4));
        gameSession.addGameObject(new BonusSpeed(3, 5));
        gameSession.addGameObject(new BonusSpeed(5, 3));
        return gameSession;
    }
}
