package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Box;
import ru.atom.model.Bomb;
import ru.atom.model.Bonus;
import ru.atom.model.Wall;
import ru.atom.geometry.Point;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Bomb(gameSession.giveId(), new Point(1,3)));
        gameSession.addGameObject(new Bomb(gameSession.giveId(), new Point(200,300)));
        gameSession.addGameObject(new Bonus(gameSession.giveId(), new Point(23,32)));
        gameSession.addGameObject(new Bonus(gameSession.giveId(), new Point(533,75)));
        gameSession.addGameObject(new Box(gameSession.giveId(), new Point(342,43)));
        gameSession.addGameObject(new Box(gameSession.giveId(), new Point(4,99)));
        gameSession.addGameObject(new Girl(gameSession.giveId(), new Point(75,22)));
        gameSession.addGameObject(new Girl(gameSession.giveId(), new Point(324,43)));
        gameSession.addGameObject(new Wall(gameSession.giveId(), new Point(44,34)));
        gameSession.addGameObject(new Wall(gameSession.giveId(), new Point(93,2)));

        return gameSession;
    }
}
