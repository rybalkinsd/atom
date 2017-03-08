package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static int objectId = 0;

    public static int setObjectId() {
        return objectId++;
    }

    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        if (gameObjects == null || gameObjects.isEmpty()) {
            return new ArrayList<>(gameObjects);
        } else {
            return gameObjects;
        }
    }

    public void addGameObject(GameObject gameObject) {
        try {
            log.info("Object : {} with id={} created.", gameObject.getClass(), gameObject.getId());
            gameObjects.add(gameObject);
        } catch (IllegalArgumentException ex) {
            log.warn("Illegal argument exeption in {}", gameObject.getClass());
        }
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<Temporary> dead = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary) gameObject);
            }
        }
        gameObjects.removeAll(dead);
    }
}
