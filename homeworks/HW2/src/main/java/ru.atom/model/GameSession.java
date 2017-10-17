package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(AbstractGameObject gameObject) {
        gameObjects.add(gameObject);
        log.info("Object " + gameObject.getClass()
                + " with ID " + gameObject.getId()
                + " on position with coordinates X:" + gameObject.getPosition().getX()
                + " and Y:" + gameObject.getPosition().getY()
                + " was created");
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
        }
    }
}
