package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {

    public static int DEFAULT_PLAYER_SPEED = 1;
    public static int DEFAULT_LIFETIME_OF_BOMB = 1;
    public static int DEFAULT_LIFETIME_OF_BONUS = 2;

    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        log.info(gameObject + " added to game");
        gameObjects.add(gameObject);
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
            log.info(gameObject);
        }
        gameObjects.removeAll(dead);
        log.info("after remove:");
        for (GameObject gameObject : gameObjects) {
            log.info(gameObject);
        }
    }
}
