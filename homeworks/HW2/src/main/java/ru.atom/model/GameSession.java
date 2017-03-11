package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static int gameObjectId = 0;
    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        log.info("Created object " + gameObject.getClass().getSimpleName() + " with id " + gameObject.getId());
        gameObjects.add(gameObject);
    }

    public static int getGameObjectId() {
        return ++gameObjectId;
    }


    @Override
    public void tick(long elapsed) {
        log.info("tick " + elapsed);
        ArrayList<Temporary> dead = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary)gameObject);
            }
        }
        gameObjects.removeAll(dead);
    }
}
