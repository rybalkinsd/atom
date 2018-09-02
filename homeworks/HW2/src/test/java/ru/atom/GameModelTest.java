package ru.atom;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.geometry.Point;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Movable;

import java.util.List;

@Ignore
public class GameModelTest {
    @Test
    public void gameIsCreated() {
        GameSession gameSession = TestGameSessionCreator.createGameSession();
        Assert.assertNotNull(gameSession);
    }

    @Test
    public void gameObjectsAreInstantiated() {
        GameSession gameSession = TestGameSessionCreator.createGameSession();
        List<GameObject> gameObjects = gameSession.getGameObjects();
        Assert.assertNotNull(gameObjects);
        Assert.assertFalse(gameObjects.size() == 0);
    }

    /**
     * Checks that Movable GameObjects-s move
     * Collisions are ignored
     */
    @Test
    public void movement() {
        GameSession gameSession = TestGameSessionCreator.createGameSession();
        List<GameObject> gameObjects = gameSession.getGameObjects();

        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Movable) {
                Point firstPosition = ((Movable) gameObject).getPosition();
                Point currentPosition = ((Movable) gameObject).move(Movable.Direction.UP, 1000);
                Assert.assertTrue(currentPosition.getY() > firstPosition.getY());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.DOWN, 1000);
                Assert.assertTrue(currentPosition.getY() == firstPosition.getY());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.RIGHT, 500);
                Assert.assertTrue(currentPosition.getX() > firstPosition.getX());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.LEFT, 500);
                Assert.assertTrue(currentPosition.getX() == firstPosition.getX());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.IDLE, 1000);

                Assert.assertTrue(currentPosition.getX() == firstPosition.getX());
                Assert.assertTrue(currentPosition.getY() == firstPosition.getY());
            }
        }
    }
}