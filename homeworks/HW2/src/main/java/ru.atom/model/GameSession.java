package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {

    private static int counter = 0;

    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static String messageFormat = "Created object #{}, class={}";
    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        log.info(messageFormat, gameObject.getId(), gameObject.getClass());
    }

    public static int getGameObjectId() {
        return counter++;
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
                dead.add((Temporary)gameObject);
            }
        }
        gameObjects.removeAll(dead);
    }
}
