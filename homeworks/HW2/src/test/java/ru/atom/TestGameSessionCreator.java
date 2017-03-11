package ru.atom;


import ru.atom.model.Bomb;
import ru.atom.model.Fire;
import ru.atom.model.GameSession;
import ru.atom.model.Wall;
import ru.atom.model.Box;
import ru.atom.model.Bonus;
import ru.atom.model.Hero;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(0, 1, 100));
        gameSession.addGameObject(new Bomb(0, 2, 100));
        gameSession.addGameObject(new Fire(1, 1, 100));
        gameSession.addGameObject(new Fire(1, 2, 1900));
        gameSession.addGameObject(new Wall(3, 1));
        gameSession.addGameObject(new Wall(3, 2));
        gameSession.addGameObject(new Box(4, 1));
        gameSession.addGameObject(new Box(4, 2));
        gameSession.addGameObject(new Bonus(5, 1));
        gameSession.addGameObject(new Bonus(5, 2));
        gameSession.addGameObject(new Hero(7, 7));
        gameSession.addGameObject(new Hero(8, 8));


        //TODO populate your game session with sample objects
        return gameSession;
    }
}
