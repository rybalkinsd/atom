package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;
import ru.atom.websocket.util.JsonHelper;
import ru.atom.websocket.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.atom.WorkWithProperties.getProperties;
import static ru.atom.websocket.model.AbstractGameObject.BAR_SIZE;
import static ru.atom.websocket.model.AbstractGameObject.CENTERED_BAR_SHIFT;
import static ru.atom.websocket.model.Fire.CENTERED_BAR_SIZE;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    public static final int MAP_WIDTH = Integer.valueOf(getProperties().getProperty("MAP_WIDTH"));
    public static final int MAP_HEIGHT = Integer.valueOf(getProperties().getProperty("MAP_HEIGHT"));
    private List<GameObject> gameObjects;
    private AtomicInteger id = new AtomicInteger(0);
    private Result result;

    public GameSession() {
        gameObjects = new ArrayList<>();
        generateStandartMap(MAP_WIDTH, MAP_HEIGHT);
        result = new Result();
    }

    public int getCurrentId() {
        return id.get();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public int numberOfPlayers() {
        return (int) gameObjects.stream().filter(gameObject -> gameObject instanceof Player).count();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        id.incrementAndGet();
    }

    private Player findPawn(int pawnId) throws NoSuchElementException {
        return (Player)gameObjects.stream().filter(gameObject -> gameObject.getId() == pawnId).findAny().get();
    }

    /**
     * can be used only before game starts
     * @param pawnId Id of player's gameobject
     */
    public void removePawn(int pawnId) {
        gameObjects.remove(findPawn(pawnId));
    }

    public void plantBomb(int pawnId) {
        try {
            findPawn(pawnId).plant();
        } catch (NoSuchElementException e) {
            log.info("bomb wasn't planted because of die/leave");
        }
    }

    public void returnBomb(int pawnId) {
        try {
            findPawn(pawnId).returnBomb();
        } catch (NoSuchElementException e) {
            log.info("bomb wasn't returned because of die/leave");
        }
    }

    private List<Fire> explosionBomb(Bomb bomb) {
        List<Fire> explosion = new ArrayList<>();
        Point bombPosition = bomb.getPosition();
        explosion.add(new Fire(id.getAndIncrement(), bombPosition));
        // TODO: 11.05.17   надо тут исправить, когда будут коллизии с досками и стенами
        if ((bombPosition.getX() / BAR_SIZE) % 2 != 0) {
            for (int i = 0; i < bomb.getPower(); i++) {
                if (bombPosition.getY() + BAR_SIZE * (i + 1) < MAP_HEIGHT*BAR_SIZE)
                explosion.add(new Fire(id.getAndIncrement(),
                        new Point(bombPosition.getX(), bombPosition.getY() + BAR_SIZE * (i + 1))));
                if (bombPosition.getY() - BAR_SIZE * (i + 1) > 0*BAR_SIZE)
                explosion.add(new Fire(id.getAndIncrement(),
                        new Point(bombPosition.getX(), bombPosition.getY() - BAR_SIZE * (i + 1))));
            }
        }
        if ((bombPosition.getY() / BAR_SIZE) % 2 != 0) {
            for (int i = 0; i < bomb.getPower(); i++) {
                if (bombPosition.getX() + BAR_SIZE * (i + 1) < MAP_WIDTH*BAR_SIZE)
                explosion.add(new Fire(id.getAndIncrement(),
                        new Point(bombPosition.getX() + BAR_SIZE * (i + 1), bombPosition.getY())));
                if (bombPosition.getX() - BAR_SIZE * (i + 1) > 0 *BAR_SIZE)
                explosion.add(new Fire(id.getAndIncrement(),
                        new Point(bombPosition.getX() - BAR_SIZE * (i + 1), bombPosition.getY())));
            }
        }
        return explosion;
    }

    public void movePawn(int pawnId, Movable.Direction direction) {
        try {
            Player pawn = findPawn(pawnId);
            if (pawn.getDirection() == Movable.Direction.IDLE) {
                Point endPosition = direction.move(pawn.getPosition(), pawn.getVelocity());
                Bar pawnBar = new Bar(new Point(endPosition.getX() + CENTERED_BAR_SHIFT, endPosition.getY()),
                        CENTERED_BAR_SIZE);
                try {
                    GameObject block = gameObjects.stream()
                            .filter(gameObject -> ((AbstractGameObject) gameObject).getBar().isColliding(pawnBar))
                            .filter(gameObject -> gameObject instanceof Wall || gameObject instanceof UnbreakableWall)
                            .findAny().get();
                    log.info("I was blocked by : {}", JsonHelper.toJson(block));
                } catch (NoSuchElementException e) {
                    pawn.setDirection(direction);
                }
            } else {
                log.info("player has instruction to move already");
            }
        } catch (NoSuchElementException e) {
            log.info("pawn wasn't moved because of die/leave");
        }
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<GameObject> dead = new ArrayList<>();
        ArrayList<GameObject> born = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                if (gameObject instanceof Player) {
                    Player player = (Player) gameObject;
                    Bomb bomb = player.plantBomb();
                    if (bomb != null) {
                        bomb.setId(id.getAndIncrement());
                        born.add(bomb);
                    }
                }
                if (gameObject instanceof Bonus) {
                    try {
                        GameObject pawn = gameObjects.stream().filter(gameObject1 ->
                                ((AbstractGameObject) gameObject1).getBar().isColliding(((Bonus) gameObject).getBar()))
                                .filter(gameObject1 -> gameObject1 instanceof Player).findFirst().get();

                        ((Player) pawn).getBonus((Bonus) gameObject);
                        dead.add(gameObject);
                    } catch (NoSuchElementException e) {
                        log.error("Nobody wants to recieve me) - {}", ((Bonus)gameObject).getBonusType());
                    }
                }
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add(gameObject);
                if (gameObject instanceof Bomb) {
                    born.addAll(explosionBomb((Bomb)gameObject));
                    returnBomb(((Bomb) gameObject).getPawnId());
                }
            }
        }
        for (GameObject gameObject : born) {
            if (gameObject instanceof Fire) {
                Fire fire = (Fire) gameObject;
                try {
                    GameObject destruction = gameObjects.stream().filter(gameObject1 ->
                            fire.getBar().isColliding(((AbstractGameObject) gameObject1).getBar())
                                    && !(gameObject1 instanceof Grass)).findFirst().get();
                    if (destruction instanceof UnbreakableWall) {
                        log.info("I was blocked by Wall {}", JsonHelper.toJson(destruction));
                        dead.add(gameObject);
                    } else if (destruction instanceof Wall) {
                        log.info("I destroyed Wood {}", JsonHelper.toJson(destruction));
                        dead.add(destruction);
                        Bonus bonus = ((Wall) destruction).plantBonus();
                        if (bonus != null) {
                            bonus.setId(id.getAndIncrement());
                            addGameObject(bonus);
                        }
                    } else if (destruction instanceof Bomb) {
                        ((Bomb)destruction).setDead();
                    } else if (destruction instanceof Bonus) {
                        ((Bonus) destruction).setDead();
                    } else if (destruction instanceof Player) {
                        log.info("I killed you player {}", JsonHelper.toJson(destruction));
                        dead.add(destruction);
                    }
                } catch (NoSuchElementException e) {
                    log.warn("here should be Grass, but it is not");
                }
            }
        }
        gameObjects.addAll(born);
        gameObjects.removeAll(dead);
    }

    /**
     * map like :
     * |-------------|
     * | * * * * * * |
     * | * * * * * * |
     * | * * * * * * |
     * |-------------|
     * @param width of map
     * @param height of map
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
                    if (x == 1 && y == 1 || x == 1 && y == 2 || x == 2 && y == 1
                            || x == width - 2 && y == height - 2 || x == width - 2 && y == height - 3
                            || x == width - 3 && y == height - 2
                            || x == 1 && y == height - 2 || x == 1 && y == height - 3 || x == 2 && y == height - 2
                            || x == width - 2 && y == 1 || x == width - 2 && y == 2 || x == width - 3 && y == 1) {
                        addGameObject(new Grass(getCurrentId(), new Point(x, y)));
                    } else {
                        addGameObject(new Grass(getCurrentId(), new Point(x, y)));
                        addGameObject(new Wall(getCurrentId(), new Point(x, y)));
                    }
                }
            }
        }
    }
}
