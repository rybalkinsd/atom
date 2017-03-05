package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    
    private static int maxId = 0;

    public static int getUniqueId() {
        return ++maxId;
    }

    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        log.info(gameObject.getClass().getName() + " " + gameObject.getId() + " was added to game session.");
        gameObjects.add(gameObject);
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick " + elapsed + " ms.");
        ArrayList<GameObject> livingObjects = new ArrayList<>(gameObjects.size());
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
            boolean deleteCurrentObject = gameObject instanceof Temporary && ((Temporary) gameObject).isDead();
            if (!deleteCurrentObject) livingObjects.add(gameObject);
        }
        gameObjects = livingObjects;
    }
}
