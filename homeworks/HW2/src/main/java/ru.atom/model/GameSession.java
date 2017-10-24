package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static int bombId = 0;
    private static int playerId = 0;
    private static int bonusId = 0;
    private static int boxId = 0;
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public static int getBombId() {
        return ++bombId;
    }

    public static int getPlayerId() {
        return ++playerId;
    }

    public static int getBonusId() {
        return ++bonusId;
    }

    public static int getBoxId() {
        return ++boxId;
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
