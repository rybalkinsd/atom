package ru.atom;

import ru.atom.model.Bomb;
import ru.atom.model.Boy;
import ru.atom.model.GameSession;
import ru.atom.model.Girl;
import ru.atom.model.Fire;
import ru.atom.model.Boots;
import ru.atom.model.SuperBomb;
import ru.atom.model.SuperFire;
import ru.atom.model.Wall;
import ru.atom.model.Cover;
import ru.atom.model.PlayingField;

/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {

    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();

        gameSession.addGameObject(new Boy());
        gameSession.addGameObject(new Girl());

        gameSession.addGameObject(new Bomb());
        gameSession.addGameObject(new Fire());

        gameSession.addGameObject(new Boots());
        gameSession.addGameObject(new SuperBomb());
        gameSession.addGameObject(new SuperFire());

        gameSession.addGameObject(new Wall());
        gameSession.addGameObject(new Cover());

        gameSession.addGameObject(new Boy());
        gameSession.addGameObject(new Girl());

        gameSession.addGameObject(new Bomb());
        gameSession.addGameObject(new Fire());

        gameSession.addGameObject(new Boots());
        gameSession.addGameObject(new SuperBomb());
        gameSession.addGameObject(new SuperFire());

        gameSession.addGameObject(new Wall());
        gameSession.addGameObject(new Cover());

        gameSession.addGameObject(new PlayingField());
        gameSession.addGameObject(new PlayingField());

        return gameSession;
    }
}