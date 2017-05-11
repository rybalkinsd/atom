package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects;
    private AtomicInteger id = new AtomicInteger(0);

    public GameSession() {
        gameObjects = new ArrayList<>();
        generateStandartMap(17, 13);
    }

    public int getCurrentId() {
        return id.get();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        id.incrementAndGet();
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
        explosion.add(new Fire(id.getAndIncrement(), bombPosition));
        // TODO: 11.05.17   надо тут исправить, когда будут коллизии с досками
        for (int i = 0; i < bomb.getPower(); i++) {
            explosion.add(new Fire(id.getAndIncrement(), new Point(bombPosition.getX() + (i + 1), bombPosition.getY())));
            explosion.add(new Fire(id.getAndIncrement(), new Point(bombPosition.getX() - (i + 1), bombPosition.getY())));
            explosion.add(new Fire(id.getAndIncrement(), new Point(bombPosition.getX(), bombPosition.getY() + (i + 1))));
            explosion.add(new Fire(id.getAndIncrement(), new Point(bombPosition.getX(), bombPosition.getY() - (i + 1))));
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
                        bomb.setId(id.getAndIncrement());
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

    /**
     * map like :
     * |-------------|
     * | * * * * * * |
     * |* * * * * * *|
     * | * * * * * * |
     * |-------------|
     * @param width
     * @param height
     */
    private void generateStandartMap(int width, int height) {
        for (int x = 0; x < width; x++) {
            addGameObject(new UnbreakableWall(getCurrentId(), new Point(x, 0)));
            addGameObject(new UnbreakableWall(getCurrentId(), new Point(x, height - 1)));
        }
        for (int y = 1; y < height - 1; y++) {
            addGameObject(new UnbreakableWall(getCurrentId(), new Point(0, y)));
            addGameObject(new UnbreakableWall(getCurrentId(), new Point(width - 1, y)));
        }
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (x % 2 == 0 && y % 2 == 0) {
                    addGameObject(new UnbreakableWall(getCurrentId(), new Point(x, y)));
                } else {
                    addGameObject(new Grass(getCurrentId(), new Point(x, y)));
                    if (x == 1 && y == 1 || x == 1 && y == 2 || x == 2 && y == 1
                            || x == width - 2 && y == height - 2 || x == width - 2 && y == height - 3
                            || x == width - 3 && y == height - 2
                            || x == 1 && y == height - 2 || x == 1 && y == height - 3 || x == 2 && y == height - 2
                            || x == width - 2 && y == 1 || x == width - 2 && y == 2 || x == width - 3 && y == 1) {
                    } else {
                        addGameObject(new Wall(getCurrentId(), new Point(x, y)));
                    }
                }
            }
        }
    }
}
