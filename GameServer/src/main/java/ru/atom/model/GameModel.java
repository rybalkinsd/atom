package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

public class GameModel implements Tickable {
    private static final Logger log = LogManager.getLogger(GameModel.class);
    private static AtomicLong idGenerator = new AtomicLong();
    public HashMap<Long, FormedGameObject> gameObjects = new HashMap<>();
    public HashMap<Long, Girl> players = new HashMap<>();
    private Vector<Tickable> tickableObjects = new Vector<>();
    private TileMap tileMap = new TileMap(17, 13,16,16);

    public GameModel() {
        GeomObject geomObject1 = new Rectangle(new Point(128, 128), 32, 32);
        GeomObject geomObject2 = new Rectangle(new Point(256, 256), 32, 32);
        Girl girl1 = new Girl(geomObject1, 1f);
        Girl girl2 = new Girl(geomObject2, 1f);
        players.put(girl1.getId(), girl1);
        players.put(girl2.getId(), girl2);
        gameObjects.put(girl1.getId(), girl1);
        gameObjects.put(girl2.getId(), girl2);
    }

    public static long generateGameObjectId() {
        return idGenerator.getAndIncrement();
    }

    public long getPlayerObjectId(int playerNumber) {
        return (long)(players.keySet().toArray()[playerNumber]);

    }

    public void handleMoveEvent(long playerId, Movable.Direction direction, long time) {
        log.info("MOVE " + playerId + " " + direction);
        players.get(playerId).move(direction, time);
    }

    public void handleBombEvent(long playerId) {
        log.info("BOMB " + playerId);
        players.get(playerId).setBomb();
    }

    /*private List<GameObject> getGameObjects()
    }

    private void addGameObject(GameObject gameObject) {
    }*/


    @Override
    public void tick(long elapsed) {
        tickableObjects.stream().forEach(gameObject -> {
            gameObject.tick(elapsed);
        });
    }
}
