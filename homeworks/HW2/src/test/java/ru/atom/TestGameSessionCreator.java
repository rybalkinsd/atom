package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Player;
import ru.atom.model.Box;
import ru.atom.model.Brick;


/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */
public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        //TODO populate your game session with sample objects and log their creation
        //вставляем двух игроков
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 10);
        Player pl1 = new Player(p1);
        Player pl2 = new Player(p2);
        gameSession.addGameObject(pl1);
        gameSession.addGameObject(pl2);

        //вставляем коробки
        p1 = new Point(10, 0);
        p2 = new Point(10, 10);
        Box bx1 = new Box(p1);
        Box bx2 = new Box(p2);
        gameSession.addGameObject(bx1);
        gameSession.addGameObject(bx2);

        //вставляем Brick'и
        p1 = new Point(20, 0);
        p2 = new Point(20, 10);
        Brick br1 = new Brick(p1);
        Brick br2 = new Brick(p2);
        gameSession.addGameObject(br1);
        gameSession.addGameObject(br2);

        //throw new UnsupportedOperationException();
        /*
        Window window = new Window(gameSession.getGameObjects());
        window.setVisible(true);
        */
        return gameSession;
    }
}


