package ru.atom;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.geometry.Point;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Movable;
import ru.atom.model.Temporary;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                Point currentPosition = ((Movable) gameObject).move(Movable.Direction.UP);
                Assert.assertTrue(currentPosition.getY() > firstPosition.getY());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.DOWN);
                Assert.assertTrue(currentPosition.getY() == firstPosition.getY());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.RIGHT);
                Assert.assertTrue(currentPosition.getX() > firstPosition.getX());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.LEFT);
                Assert.assertTrue(currentPosition.getX() == firstPosition.getX());

                currentPosition = ((Movable) gameObject).move(Movable.Direction.IDLE);
                Assert.assertTrue(currentPosition.getX() == firstPosition.getX());
                Assert.assertTrue(currentPosition.getY() == firstPosition.getY());
            }
        }
    }

    /**
     * Test checks that all temporary objects live at least for some time and are dead after very long time
     */
    @Test
    public void ticking() {
        GameSession gameSession = TestGameSessionCreator.createGameSession();
        List<Temporary> temporaries = gameSession.getGameObjects().stream()
                .filter(o -> o instanceof Temporary)
                .map(o -> (Temporary) o).collect(Collectors.toList());

        Assert.assertFalse(temporaries.isEmpty());

        long maxLifeTime = temporaries.stream()
                .max(Comparator.comparingLong(Temporary::getLifetimeMillis)).get().getLifetimeMillis();
        long minLifeTime = temporaries.stream()
                .min(Comparator.comparingLong(Temporary::getLifetimeMillis)).get().getLifetimeMillis();
        gameSession.tick(minLifeTime - 1);
        List<Temporary> temporariesAfterSmallTime = gameSession.getGameObjects().stream()
                .filter(o -> o instanceof Temporary)
                .map(o -> (Temporary) o).collect(Collectors.toList());
        Assert.assertTrue(temporaries.containsAll(temporariesAfterSmallTime));
        Assert.assertTrue(temporariesAfterSmallTime.containsAll(temporaries));

        gameSession.tick(maxLifeTime + 1);
        temporaries = gameSession.getGameObjects().stream()
                .filter(o -> o instanceof Temporary)
                .map(o -> (Temporary) o).collect(Collectors.toList());
        Assert.assertTrue(temporaries.isEmpty());
    }
}
