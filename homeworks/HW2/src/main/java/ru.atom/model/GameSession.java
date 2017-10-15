package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private int lastId = 0;

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public boolean removeById(int id) {
        while (gameObjects.iterator().hasNext()) {
            if (gameObjects.iterator().next().id == id) {
                gameObjects.iterator().remove();
                return true;
            }
        }
        return false;
    }

    public GameObject getGameObjectByPosition(Point position) {
        for (GameObject object : gameObjects) {
            if (object.getPosition().equals(position)) {
                return object;
            }
        }
        return null; //TODO: Exception??
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public int getNewId() {
        return ++lastId;
    }

    @Override
    public void tick(int elapsed) {
        log.info("tick");
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
        }
    }
}
