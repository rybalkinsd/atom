package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.Flame;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.StoneBox;
import ru.atom.model.WoodBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in
 * bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        Bomb b1 = new Bomb();
        Bomb b2 = new Bomb();
        gameSession.addGameObject(b1);
        gameSession.addGameObject(b2);

        Girl g1 = new Girl(new Point(5, 5));
        Girl g2 = new Girl(new Point(10, 5));
        gameSession.addGameObject(g1);
        gameSession.addGameObject(g2);

        WoodBox wb1 = new WoodBox(new Point(5, 10));
        WoodBox wb2 = new WoodBox(new Point(10, 10));
        gameSession.addGameObject(wb1);
        gameSession.addGameObject(wb2);

        StoneBox sb1 = new StoneBox(new Point(5, 15));
        StoneBox sb2 = new StoneBox(new Point(10, 15));
        gameSession.addGameObject(sb1);
        gameSession.addGameObject(sb2);

        Flame f1 = new Flame();
        Flame f2 = new Flame();
        gameSession.addGameObject(f1);
        gameSession.addGameObject(f2);

        // throw new NotImplementedException();
        return gameSession;
    }
}
