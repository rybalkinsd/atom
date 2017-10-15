package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.GameObject;
import ru.atom.model.Bomb;
import ru.atom.model.Bomber;
import ru.atom.model.Fire;
import ru.atom.model.BombBonus;
import ru.atom.model.Speed;
import ru.atom.model.Wall;
import ru.atom.model.Box;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(1,100,100,1));
        gameSession.addGameObject(new Bomb(2,900,900,1));
        gameSession.addGameObject(new Bomber(3,100,900));
        gameSession.addGameObject(new Bomber(4,900,100));
        gameSession.addGameObject(new Fire(5,100,700));
        gameSession.addGameObject(new Fire(6,700,100));
        gameSession.addGameObject(new BombBonus(7,700,700));
        gameSession.addGameObject(new BombBonus(8,300,300));
        gameSession.addGameObject(new Speed(9,300,700));
        gameSession.addGameObject(new Speed(10,700,300));
        gameSession.addGameObject(new Wall(11,100,300));
        gameSession.addGameObject(new Wall(12,300,100));
        gameSession.addGameObject(new Box(13,200,300));
        gameSession.addGameObject(new Box(14,300,200));
        return gameSession;
    }
}
