package ru.atom;

import ru.atom.model.AmountOfBomb;
import ru.atom.model.AmountOfFire;
import ru.atom.model.Bomb;
import ru.atom.model.Box;
import ru.atom.model.Boy;
import ru.atom.model.Fire;
import ru.atom.model.GameSession;
import ru.atom.model.Speed;
import ru.atom.model.Stones;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Stones(0,0));
        gameSession.addGameObject(new Stones(1,1));
        gameSession.addGameObject(new Box(10,10));
        gameSession.addGameObject(new Box(20,20));
        gameSession.addGameObject(new Boy(2,2));
        gameSession.addGameObject(new Boy(5,5));
        gameSession.addGameObject(new Bomb(8,8));
        gameSession.addGameObject(new Bomb(9,9));
        gameSession.addGameObject(new Fire(12,56));
        gameSession.addGameObject(new Fire(34,65));
        gameSession.addGameObject(new Speed(3,6));
        gameSession.addGameObject(new Speed(6,7));
        gameSession.addGameObject(new AmountOfBomb(5,8));
        gameSession.addGameObject(new AmountOfBomb(8,0));
        gameSession.addGameObject(new AmountOfFire(2,7));
        gameSession.addGameObject(new AmountOfFire(7,8));
        return gameSession;
    }
}
