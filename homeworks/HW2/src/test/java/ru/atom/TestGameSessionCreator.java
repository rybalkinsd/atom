package ru.atom;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.model.GameSession;
import ru.atom.model.Feed;
import ru.atom.model.Girl;
import ru.atom.model.Bomb;
import ru.atom.model.Box;
import ru.atom.model.Wall;
/**
 * Create sample game session with all kinds of objects that will present in bomber-man game
 */

public final class TestGameSessionCreator {
    private TestGameSessionCreator() {
    }

    static GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        gameSession.addGameObject(new Bomb(new Rectangle(new Point(1, 2),3,4),
                                            5,
                                            6));
        gameSession.addGameObject(new Bomb(new Rectangle(new Point(2, 3),4,5),
                                            6,
                                            7));

        gameSession.addGameObject(new Girl(new Rectangle(new Point(1, 2),3,4),
                                            1));
        gameSession.addGameObject(new Girl(new Rectangle(new Point(2, 3),4,5),
                                            2));

        gameSession.addGameObject(new Feed(new Rectangle(new Point(1, 2),3,4),
                                             Feed.FeedType.SPEED_BOOTS));
        gameSession.addGameObject(new Feed(new Rectangle(new Point(2, 3),4,5),
                                             Feed.FeedType.AMMUNITION_INCR));

        gameSession.addGameObject(new Box(new Rectangle(new Point(1, 2),3,4),
                                            Feed.FeedType.SPEED_BOOTS));
        gameSession.addGameObject(new Box(new Rectangle(new Point(2, 3),4,5),
                                            Feed.FeedType.AMMUNITION_INCR));

        gameSession.addGameObject(new Wall(new Rectangle(new Point(2, 3),4,5)));
        gameSession.addGameObject(new Wall(new Rectangle(new Point(1, 2),3,4)));

        return gameSession;
    }
}
