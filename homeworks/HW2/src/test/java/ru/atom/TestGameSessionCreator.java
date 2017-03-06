package ru.atom;

import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Tile;
import ru.atom.model.Bomb;
import ru.atom.model.Fire;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Player(1, 1));
        gameSession.addGameObject(new Player(2, 2));
        gameSession.addGameObject(new Tile(1, 1, Tile.Material.GRASS));
        gameSession.addGameObject(new Tile(2, 3, Tile.Material.WALL));
        gameSession.addGameObject(new Bomb(5, 5, 1));
        gameSession.addGameObject(new Bomb(4, 5,3));
        gameSession.addGameObject(new Fire(2, 3));
        gameSession.addGameObject(new Fire(4, 4));
        return gameSession;
    }
}
