package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.items.Bomb;
import ru.atom.model.items.Bonus;
import ru.atom.model.players.Player;
import ru.atom.model.walls.StandartWall;
import ru.atom.model.walls.UnbreakableWall;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Bomb(0, 0,1));
        gameSession.addGameObject(new Bomb(1, 1, 3));
        gameSession.addGameObject(new Player(1,2));
        gameSession.addGameObject(new Player(2,3));

        gameSession.addGameObject(new StandartWall(7, 5));
        gameSession.addGameObject(new StandartWall(8, 5));

        gameSession.addGameObject(new UnbreakableWall(7, 7));
        gameSession.addGameObject(new UnbreakableWall(3, 7));

        gameSession.addGameObject(new Bonus(1, 3, Bonus.Type.SPEED));
        gameSession.addGameObject(new Bonus(5, 7, Bonus.Type.BOMB));

        return gameSession;
    }
}
