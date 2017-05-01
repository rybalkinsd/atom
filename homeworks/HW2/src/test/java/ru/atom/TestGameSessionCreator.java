package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.GameSession;
import ru.atom.model.Bomb;
import ru.atom.model.Girl;
import ru.atom.model.Bonus;
import ru.atom.model.HeavyBlock;
import ru.atom.model.LightBlock;
import ru.atom.model.Fire;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private static final Logger log = LogManager.getLogger(TestGameSessionCreator.class);

    private TestGameSessionCreator(){}

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects
        gameSession.addGameObject(new Bomb(5, 5));
        gameSession.addGameObject(new Bomb(10, 10));
        gameSession.addGameObject(new Girl(0, 0));
        gameSession.addGameObject(new Girl(100, 100));
        gameSession.addGameObject(new Bonus(15, 15));
        gameSession.addGameObject(new Bonus(25, 25));
        gameSession.addGameObject(new HeavyBlock(20, 20));
        gameSession.addGameObject(new HeavyBlock(30, 30));
        gameSession.addGameObject(new LightBlock(40, 40));
        gameSession.addGameObject(new LightBlock(50, 50));
        gameSession.addGameObject(new Fire(50, 50));
        gameSession.addGameObject(new Fire(150, 150));
        return gameSession;
    }
}
