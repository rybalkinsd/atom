package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private final int playerCount;
    private final long id;
    //ID for game objects
    private int lastId = 0;

    public GameSession(int playerCount, long id) {
        this.playerCount = playerCount;
        this.id = id;
        Util.generateMap(this);
    }

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

    public long getId() {
        return id;
    }

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
