package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSession implements Tickable {

    private List<GameObject> gameObjects = new ArrayList<>();
    private static AtomicInteger counter;

    private static final Logger log = LogManager.getLogger(GameSession.class);

    public GameSession() {
        counter = new AtomicInteger();
    }

    public static int nextValue() {
        return counter.getAndIncrement();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        log.info("Created " + gameObject.getClass().getName().substring(17) + " with id: " + gameObject.getId());
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
        }
        gameObjects.removeAll(dead);
    }
}
