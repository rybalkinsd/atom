package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.BomberGirl;
import ru.atom.model.GameSession;
import ru.atom.model.Nishtyaki;
import ru.atom.model.Wall;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Wall(0,0));
        gameSession.addGameObject(new Wall(5,5));
        gameSession.addGameObject(new BomberGirl(1,1));
        gameSession.addGameObject(new BomberGirl(2,3));
        gameSession.addGameObject(new Bomb(1,5,120));
        gameSession.addGameObject(new Bomb(5,3,120));
        gameSession.addGameObject(new Nishtyaki(4,1, Nishtyaki.Type.NUMBER_BOMB));
        gameSession.addGameObject(new Nishtyaki(3,3, Nishtyaki.Type.SPEED));
        return gameSession;
    }
}
