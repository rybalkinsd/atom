package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects;
    private int id = 0;

    public GameSession() {
        gameObjects = new ArrayList<>();
        generateStandartMap(545, 416);
    }

    public int getCurrentId() {
        return id;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        id++;
    }

    private Player findPawn(int pawnId) {
        return (Player)gameObjects.stream().filter(gameObject -> gameObject.getId() == pawnId).findAny().get();
    }

    /**
     * can be used only before game starts
     * @param pawnId
     */
    public void killPawn(int pawnId) {
        gameObjects.remove(findPawn(pawnId));
    }

    public void plantBomb(int pawnId) {
        findPawn(pawnId).plant();
    }

    private List<Fire> explosionBomb(Bomb bomb) {
        List<Fire> explosion = new ArrayList<>();
        Point bombPosition = bomb.getPosition();
        explosion.add(new Fire(id, bombPosition));
        id++;
        // TODO: 11.05.17   надо тут исправить, когда будут коллизии с досками
        for (int i = 0; i < bomb.getPower(); i++) {
            explosion.add(new Fire(id, new Point(bombPosition.getX() + 38 * (i + 1), bombPosition.getY())));
            id++;
            explosion.add(new Fire(id, new Point(bombPosition.getX() - 38 * (i + 1), bombPosition.getY())));
            id++;
            explosion.add(new Fire(id, new Point(bombPosition.getX(), bombPosition.getY() + 38 * (i + 1))));
            id++;
            explosion.add(new Fire(id, new Point(bombPosition.getX(), bombPosition.getY() - 38 * (i + 1))));
            id++;
        }
        return explosion;
    }

    public void movePawn(int pawnId, Movable.Direction direction) {
        Player pawn = findPawn(pawnId);
        if (pawn.getDirection() == Movable.Direction.IDLE) {
            pawn.setDirection(direction);
        } else {log.info("player has instruction to move already");}
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<Temporary> dead = new ArrayList<>();
        ArrayList<GameObject> born = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                if (gameObject instanceof Player) {
                    Bomb bomb = ((Player) gameObject).plantBomb();
                    if (bomb !=null) {
                        bomb.setId(id);
                        id++;
                        born.add(bomb);
                    }
                }
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary)gameObject);
                if(gameObject instanceof Bomb) {
                    born.addAll(explosionBomb((Bomb)gameObject));
                }
            }
        }
        gameObjects.removeAll(dead);
        gameObjects.addAll(born);
    }

    private void generateStandartMap(int width, int height) {
        gameObjects.add(new UnbreakableWall(id, new Point(300, 125)));
        id++;
    }
}
