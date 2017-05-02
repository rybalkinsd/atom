package ru.atom.bombergirl.mmserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.bombergirl.gamemodel.model.GameObject;
import ru.atom.bombergirl.gamemodel.model.Temporary;
import ru.atom.bombergirl.gamemodel.model.Tickable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ikozin on 17.04.17.
 */
public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    private static AtomicLong idGenerator = new AtomicLong();
    private List<GameObject> gameObjects = new ArrayList<>();
    private static AtomicInteger counter;

    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;
    private final long id = idGenerator.getAndIncrement();

    public GameSession(Connection[] connections) {
        this.connections = connections;
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

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }
}
