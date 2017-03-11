package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.Bomb;
import ru.atom.model.Bonus;
import ru.atom.model.Fire;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Tile;

/**
 * Create sample game session with all kinds of objects that will present in
 * bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        Bomb bomb1 = new Bomb(new Point(0,0), 1);
        Bomb bomb2 = new Bomb(new Point(0,1), 2);
        gameSession.addGameObject(bomb1);
        gameSession.addGameObject(bomb2);

        gameSession.addGameObject(new Fire(new Point(1, 0), bomb1));
        gameSession.addGameObject(new Fire(new Point(1, 1), bomb2));

        gameSession.addGameObject(new Bonus(new Point(10, 0), Bonus.Types.BOMB));
        gameSession.addGameObject(new Bonus(new Point(10, 1), Bonus.Types.FIRE));
        gameSession.addGameObject(new Bonus(new Point(10, 2), Bonus.Types.SPEED));

        gameSession.addGameObject(new Player(new Point(20, 0)));
        gameSession.addGameObject(new Player(new Point(20, 1)));

        gameSession.addGameObject(new Tile(new Point(30, 0), Tile.Materials.WALL));
        gameSession.addGameObject(new Tile(new Point(30, 1), Tile.Materials.GRASS));
        gameSession.addGameObject(new Tile(new Point(30, 2), Tile.Materials.WOOD));

        return gameSession;
    }
}
