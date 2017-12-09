package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.tick.Tickable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

public class GameModel {
    private static final Logger log = LogManager.getLogger(GameModel.class);
    private static AtomicLong idGenerator = new AtomicLong();
    public HashMap<Long, FormedGameObject> gameObjects = new HashMap<>();
    public HashMap<Long, Girl> players = new HashMap<>();
    public HashMap<Long, FormedGameObject> changed = new HashMap<>();
    public HashMap<Long, FormedGameObject> deleted = new HashMap<>();
    private TileMap tileMap = new TileMap(17, 13,16,16);

    public TileMap getTileMap() {
        return this.tileMap;
    }
    public GameModel() {
        GeomObject geomObject1 = new Rectangle(new Point(128, 128), 32, 32);
        GeomObject geomObject2 = new Rectangle(new Point(256, 256), 32, 32);
        GeomObject geomObject3 = new Rectangle(new Point(0, 0), 32, 32);
        Girl girl1 = new Girl(geomObject1, 0.2f);
        Girl girl2 = new Girl(geomObject2, 0.2f);
        Box box = new Box(geomObject3, Feed.FeedType.EMPTY);
        players.put(girl1.getId(), girl1);
        players.put(girl2.getId(), girl2);
        gameObjects.put(box.getId(), box);
        gameObjects.put(girl1.getId(), girl1);
        gameObjects.put(girl2.getId(), girl2);
        changed.put(box.getId(), box);
        changed.put(girl1.getId(), girl1);
        changed.put(girl2.getId(), girl2);

    }

    public static long generateGameObjectId() {
        return idGenerator.getAndIncrement();
    }

    public long getPlayerObjectId(int playerNumber) {
        return (long)(players.keySet().toArray()[playerNumber]);

    }

    public void handleMoveEvent(long playerId, Movable.Direction direction, long time) {
        //log.info("MOVE " + playerId + " " + direction);
        players.get(playerId).move(direction, time);
        changed.put(players.get(playerId).getId(), players.get(playerId));
    }

    public void handleBombEvent(long playerId) {
        deleted.put(players.get(playerId).getId(), players.get(playerId));
        //log.info("BOMB " + playerId);
        //players.get(playerId).setBomb();

    }

    public void update() {
        changed.clear();
        deleted.clear();
    }

}
