package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.Girl;
import ru.atom.model.IndestructibleStone;
import ru.atom.model.Explodingstone;
import ru.atom.model.GameSession;
import ru.atom.model.BonusBox;
import ru.atom.model.Fire;

import ru.atom.model.GameSession;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Girl(12, 19));
        gameSession.addGameObject(new Bomb(62, 28));
        gameSession.addGameObject(new BonusBox(5, 4, BonusBox.TypesOfBonus.POWER));
        gameSession.addGameObject(new IndestructibleStone(6, 8));
        gameSession.addGameObject(new Explodingstone(777, 7));
        gameSession.addGameObject(new Fire(2, 2));

        return gameSession;
    }
}
