package gs.model;

import gs.geometry.Point;
import gs.message.Message;
import gs.message.Topic;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private final int playerCount;
    private final long id;
    //ID for game objects
    private int lastId = -1;

    public GameSession(int playerCount, long id) {
        this.playerCount = playerCount;
        this.id = id;
        Util.generateMap(this);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public GameObject getById(int id) {
        for(GameObject i :gameObjects) {
            if(i.getId() == id) return i;
        }
        return null;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addPlayer(int id) {
        Point position;
        switch (id) {
            case 1 : position = new Point(1, 1);
                break;
            case 2 : position = new Point(15, 11);
                break;
            case 3 : position = new Point(15, 1);
                break;
            case 4 : position = new Point(1, 11);
                break;
            default : position = new Point(1, 1);
        }
        addGameObject(new Girl(this, position));
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

    public Message initReplica() {
        return new Message(Topic.REPLICA, gameObjects.toString());
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

    public int getLastId() {return lastId;}

    @Override
    public void tick(int elapsed) {
        logger.info("tick");
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
        }
    }
}
