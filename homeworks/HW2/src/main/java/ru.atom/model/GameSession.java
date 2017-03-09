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

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Tickable) {
                ((Tickable) gameObjects.get(i)).tick(elapsed);
            }
            if (gameObjects.get(i) instanceof Temporary && ((Temporary) gameObjects.get(i)).isDead()) {
                gameObjects.remove(i);
                i--;
            }
        }
    }
}
