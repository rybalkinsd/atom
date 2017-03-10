package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.geometry.Point;
import ru.atom.model.Bonus;
import ru.atom.model.AbstractGameObject;
import ru.atom.model.GameSession;
import ru.atom.model.BeautyGirl;
import ru.atom.model.Bomb;
import ru.atom.model.BreakableWall;
import ru.atom.model.UnbreakableWall;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Bomb(new Point(0, 0)));
        gameSession.addGameObject(new Bomb(new Point(6, 0)));

        gameSession.addGameObject(new BeautyGirl(new Point(1, 2)));
        gameSession.addGameObject(new BeautyGirl(new Point(2, 8)));

        gameSession.addGameObject(new Bonus(new Point(2,4), Bonus.TypeBonus.FIRE));
        gameSession.addGameObject(new Bonus(new Point(4,4), Bonus.TypeBonus.PLUSONEBOMD));
        gameSession.addGameObject(new Bonus(new Point(5,4), Bonus.TypeBonus.SPEED));

        gameSession.addGameObject(new BreakableWall(new Point(1, 5)));
        gameSession.addGameObject(new BreakableWall(new Point(2, 5)));

        gameSession.addGameObject(new UnbreakableWall(new Point(3, 5)));
        gameSession.addGameObject(new UnbreakableWall(new Point(4, 5)));

        return gameSession;
    }
}
