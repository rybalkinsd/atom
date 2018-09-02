package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.IntersectionParams;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.model.listners.BombExplosListener;
import ru.atom.model.listners.BombPlacedListener;
import ru.atom.model.listners.BoxCollapseListener;
import ru.atom.model.listners.MoveEventListener;
import ru.atom.tick.Ticker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Random;

public class GameModel implements MoveEventListener, BombExplosListener, BoxCollapseListener, BombPlacedListener {
    private static final Logger log = LogManager.getLogger(GameModel.class);
    private static AtomicLong idGenerator = new AtomicLong();
    private static Random randomizer = new Random();

    private HashMap<Long, Girl> players = new HashMap<>();
    private Vector<Fire> fires = new Vector<>();
    public Vector<FormedGameObject> changed = new Vector<>();
    public Vector<FormedGameObject> deleted = new Vector<>();
    private boolean isOver = false;
    private Ticker ticker;
    private TileMap tileMap = new TileMap(GameServerParams.getInstance().getTileMapWidth(),
            GameServerParams.getInstance().getTileMapHeight(),
            GameServerParams.getInstance().getTileSize(),
            GameServerParams.getInstance().getTileSize());

    public boolean isGameOver() {
        return isOver;
    }

    public Long getWinner() {
        if (players.size() != 1) {
            return null;
        }

        return players.entrySet().stream().findFirst().get().getKey();
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void removePlayer(long playerId) {
        if (players.containsKey(playerId)) {
            deleted.add(players.get(playerId));
            tileMap.removeGameObject(players.get(playerId));
            players.remove(playerId);

        }


    }

    public GameModel(Ticker ticker , int playerCount) {
        this.ticker = ticker;
        for (int i = 0; i < tileMap.getWidth(); i++) {
            for (int j = 0; j < tileMap.getHeight(); j++) {
                GeomObject geomObject = new Rectangle(
                        new Point(i * tileMap.getTileWidth(), j * tileMap.getTileHeight()),
                        GameServerParams.getInstance().getBoxSize(),
                        GameServerParams.getInstance().getBoxSize());
                if (i == 0 || j == 0
                        || i == tileMap.getWidth() - 1
                        || j == tileMap.getHeight() - 1) {
                    Wall wall = new Wall(geomObject);
                    changed.add(wall);
                    tileMap.placeGameObject(wall);
                    continue;
                } else if (i % 2 == 0 && j % 2 == 0) {
                    Wall wall = new Wall(geomObject);
                    changed.add(wall);
                    tileMap.placeGameObject(wall);
                } else if ((i % 2 != 0 || j % 2 != 0)
                        && (i > 2 || j > 2)
                        && (i > 2 || j < tileMap.getHeight() - 3)
                        && (i < tileMap.getWidth() - 3 || j > 2)
                        && (i < tileMap.getWidth() - 3 || j < tileMap.getHeight() - 3)) {
                    Box box;
                    switch (randomizer.nextInt(GameServerParams.getInstance().getBonusFactor())) {
                        case 0: {
                            box = new Box(geomObject, Feed.FeedType.SPEED_BOOTS);
                            break;
                        }
                        case 1: {
                            box = new Box(geomObject, Feed.FeedType.AMMUNITION_INCR);
                            break;
                        }
                        case 2: {
                            box = new Box(geomObject, Feed.FeedType.EXPLOS_BOOST);
                            break;
                        }
                        default: {
                            box = new Box(geomObject, Feed.FeedType.EMPTY);
                            break;
                        }
                    }
                    box.addBoxCollapseListener(this);
                    changed.add(box);
                    tileMap.placeGameObject(box);
                }
            }
        }
        ArrayList<Point> playersStartPos = new ArrayList<>();
        playersStartPos.add(new Point(tileMap.getTileWidth(), tileMap.getTileHeight()));
        playersStartPos.add(new Point((tileMap.getWidth() - 2) * tileMap.getTileWidth(),
                tileMap.getTileHeight()));
        playersStartPos.add(new Point((tileMap.getWidth() - 2) * tileMap.getTileWidth(),
                (tileMap.getHeight() - 2) * tileMap.getTileHeight()));
        playersStartPos.add(new Point(tileMap.getTileWidth(),
                (tileMap.getHeight() - 2) * tileMap.getTileHeight()));

        int playerAmount = playerCount;

        if (playerCount > 4) {
            playerAmount = 4;
        }
        for (int i = 0; i < playerAmount; i++) {
            GeomObject geomObject = new Rectangle(playersStartPos.get(i),
                    GameServerParams.getInstance().getGirlSize(),
                    GameServerParams.getInstance().getGirlSize());
            Girl girl = new Girl(geomObject, 0.1f);
            girl.addMoveEventListener(this);
            girl.addBombPlacedListener(this);
            players.put(girl.getId(), girl);
            tileMap.placeGameObject(girl);
            changed.add(girl);
        }
    }

    public static long generateGameObjectId() {
        return idGenerator.getAndIncrement();
    }

    public long getPlayerObjectId(int playerNumber) {
        return (long)(players.keySet().toArray()[playerNumber]);

    }

    public void handleMoveEvent(long playerId, Movable.Direction direction, long time) {
        Girl girl = players.get(playerId);
        if (girl != null) {
            girl.move(direction, time);
            changed.add(girl);
        }

    }



    public void handleBombEvent(long playerId) {
        Girl girl = players.get(playerId);
        if (girl != null) {
            girl.setBomb();
        }


    }

    public void update() {
        changed.clear();
        deleted.clear();
        fires.forEach(fire -> {
            deleted.add(fire);
        });
        fires.clear();
        if (players.size() <= 1) {
            isOver = true;
        }

    }

    @Override
    public Point getMoveRecom(FormedGameObject newFormedGameObject, MovableFormedGameObject gameObject) {
        HashSet<FormedGameObject> gameObjects = tileMap.getNearbyGameObjects(newFormedGameObject);
        FormedGameObject collider = null;
        for (FormedGameObject formedGameObject : gameObjects) {
            if ((formedGameObject instanceof Box
                    || formedGameObject instanceof Wall
                    || formedGameObject instanceof Girl)
                    && (formedGameObject.getCollider().isColliding(newFormedGameObject.getCollider()))) {
                if (collider == null) {
                    collider = formedGameObject;
                } else {
                    return null;
                }
            }
        }

        if (collider == null) {
            return new Point(newFormedGameObject.getPosition().getX() ,
                    newFormedGameObject.getPosition().getY());
        }

        IntersectionParams params = ((Rectangle)newFormedGameObject.getForm())
                .getIntersection(((Rectangle)collider.getForm()));
        if (Math.abs(params.getDxImpos()) < GameServerParams.getInstance().getCornerHelpFactor()
                && Math.abs(params.getDyImpos()) < GameServerParams.getInstance().getCornerHelpFactor()) {
            Rectangle newForm = new Rectangle(
                    new Point(newFormedGameObject.getPosition().getX() + params.getDxImpos(),
                            newFormedGameObject.getPosition().getY() + params.getDyImpos()),
                    ((Rectangle) newFormedGameObject.getForm()).getWidth(),
                    ((Rectangle) newFormedGameObject.getForm()).getHeight());
            FormedGameObject newform = new FormedGameObject(newForm, gameObject.getId());
            gameObjects = tileMap.getNearbyGameObjects(newform);
            for (FormedGameObject formedGameObject : gameObjects) {
                if ((formedGameObject instanceof Box
                        || formedGameObject instanceof Wall
                        || formedGameObject instanceof Girl)
                        && (formedGameObject.getCollider().isColliding(newform.getCollider()))) {
                    return null;
                }
            }

            return newForm.getPosition();
        }
        return null;
    }

    private void placeFires(Bomb bomb, Movable.Direction direction) {
        int radius = bomb.getExploseRadius();
        int offset = 1;
        int kx = 0;
        int ky = 0;
        boolean isStopped = false;

        GeomObject fireForm;

        if (direction == Movable.Direction.RIGHT) {
            kx = 1;
        } else if (direction == Movable.Direction.LEFT)  {
            kx = -1;
        } else if (direction == Movable.Direction.DOWN) {
            ky = 1;
        } else if (direction == Movable.Direction.UP) {
            ky = -1;
        }  else if (direction == Movable.Direction.IDLE) {
            offset = 0;
            radius = 0;
        }

        while (offset < radius + 1 && !isStopped) {
            fireForm = new Rectangle(
                    new Point(bomb.getPosition().getX()  + kx * offset * GameServerParams.getInstance().getFireSize(),
                            bomb.getPosition().getY() + ky * offset * GameServerParams.getInstance().getFireSize()),
                    GameServerParams.getInstance().getFireSize(),
                    GameServerParams.getInstance().getFireSize());
            ++offset;
            Fire fire = new Fire(fireForm);

            for (FormedGameObject formedGameObject : tileMap.getNearbyGameObjects(fire)) {

                if (formedGameObject instanceof Box
                        && formedGameObject.getCollider().isColliding(fire.getCollider())) {
                    ((Box) formedGameObject).collapse();
                    deleted.add(formedGameObject);
                    tileMap.removeGameObject(formedGameObject);
                } else if (formedGameObject instanceof Girl
                        && formedGameObject.getCollider().isColliding(fire.getCollider())) {
                    deleted.add(formedGameObject);
                    tileMap.removeGameObject(formedGameObject);
                    players.remove(formedGameObject.getId());
                } else if (formedGameObject instanceof Wall
                        && formedGameObject.getCollider().isColliding(fire.getCollider())) {
                    isStopped = true;
                }
            }

            if (!isStopped) {
                fires.add(fire);
                changed.add(fire);
            }
        }
    }

    @Override
    public void handleBombExplodeEvent(Bomb bomb) {
        deleted.add(bomb);
        placeFires(bomb, Movable.Direction.RIGHT);
        placeFires(bomb, Movable.Direction.DOWN);
        placeFires(bomb, Movable.Direction.UP);
        placeFires(bomb, Movable.Direction.LEFT);
        placeFires(bomb, Movable.Direction.IDLE);

        ticker.unregisterTickable(bomb);

    }

    @Override
    public void handleMoveEvent(FormedGameObject oldForm, MovableFormedGameObject gameObject) {
        tileMap.removeGameObject(oldForm);
        tileMap.placeGameObject(gameObject);
        for (FormedGameObject formedGameObject : tileMap.getNearbyGameObjects(gameObject)) {
            if (formedGameObject instanceof Feed) {
                if (formedGameObject.getCollider().isColliding(gameObject.getCollider())) {
                    ((Girl)gameObject).processFeed(((Feed) formedGameObject).getType());
                    deleted.add(formedGameObject);
                    tileMap.removeGameObject(formedGameObject);

                }
            }
        }
    }

    @Override
    public void handleBoxCollapse(Box box) {
        if (box.getFeedType() != Feed.FeedType.EMPTY) {
            GeomObject geomObject = new Rectangle(
                    new Point(box.getPosition().getX(), box.getPosition().getY()),
                    GameServerParams.getInstance().getBonusSize(),
                    GameServerParams.getInstance().getBonusSize());
            Feed feed = new Feed(geomObject, box.getFeedType());
            tileMap.placeGameObject(feed);
            changed.add(feed);
        }
    }

    @Override
    public void handleBombPlaceEvent(Girl girl) {
        Rectangle form = new Rectangle(
                new Point(girl.getPosition().getX(), girl.getPosition().getY()),
                GameServerParams.getInstance().getBombSize(),
                GameServerParams.getInstance().getBombSize());
        Bomb bomb = new Bomb(form, girl.getBombExplosRadius(),
                GameServerParams.getInstance().getExplosDelay());
        bomb.addExplosEventListener(this);
        bomb.addExplosEventListener(girl);
        changed.add(bomb);
        ticker.registerTickable(bomb);

    }


}
