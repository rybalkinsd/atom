package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);

    private final int playerCount;
    private final long id;
    private List<GameObject> gameObjects = new ArrayList<>();
    private int lastId;

    public GameSession(int playerCount, long id) {
        this.playerCount = playerCount;
        this.id = id;
        GameField.generateMap(this);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public GameObject getById(int id) {
        for (GameObject i : gameObjects) {
            if (i.getId() == id) return i;
        }
        return null;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addPlayer(int id) {
        Point position;
        switch (id) {
            case 1:
                position = new Point(1, 1);
                break;
            case 2:
                position = new Point(15, 11);
                break;
            case 3:
                position = new Point(15, 1);
                break;
            case 4:
                position = new Point(1, 11);
                break;
            default:
                position = new Point(1, 1);
        }
        addGameObject(new Pawn(this, position));
    }

    public boolean removeGameObject(GameObject object) {
        return gameObjects.remove(object);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public int getNewId() {
        return ++lastId;
    }

    public long getId() {
        return id;
    }

    public int getLastId() {
        return lastId;
    }

    public ArrayList<Pawn> getPawns() {
        ArrayList<Pawn> pawns = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Pawn)
                pawns.add((Pawn) object);
        }
        return pawns;
    }

    public ArrayList<Bomb> getBombs() {
        ArrayList<Bomb> bombs = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Bomb)
                bombs.add((Bomb) object);
        }
        return bombs;
    }

    public ArrayList<Wall> getWalls() {
        ArrayList<Wall> walls = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Wall)
                walls.add((Wall) object);
        }
        return walls;
    }

    public ArrayList<Wood> getWood() {
        ArrayList<Wood> woods = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Wood)
                woods.add((Wood) object);
        }
        return woods;
    }

    public ArrayList<Fire> getFire() {
        ArrayList<Fire> fire = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Fire)
                fire.add((Fire) object);
        }
        return fire;
    }

    public ArrayList<GameObject> getObjectsWithoutWalls() {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Wall)
                continue;
            objects.add(object);
        }
        return objects;
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
