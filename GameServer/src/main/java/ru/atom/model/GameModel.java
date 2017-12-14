package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.tick.Ticker;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Random;
import java.util.Date;
public class GameModel implements MoveEventListener, BombExplosListener, BoxCollapseListener, BombPlacedListener{
    private static final Logger log = LogManager.getLogger(GameModel.class);
    private static AtomicLong idGenerator = new AtomicLong();
    private Random randomizer = new Random();

    private Ticker ticker;
    int bonusFactor = 10;

    public HashMap<Long, Girl> players = new HashMap<>();
    public Vector<Fire> fires = new Vector<>();
    private TileMap tileMap = new TileMap(17, 13,32,32);
    public Vector<FormedGameObject> changed = new Vector<>();
    public Vector<FormedGameObject> deleted = new Vector<>();



    public TileMap getTileMap() {
        return this.tileMap;
    }
    public GameModel(Ticker ticker , int playerCount) {
        this.ticker = ticker;
        for (int i = 0; i < tileMap.getWidth(); i++) {
            for (int j = 0; j < tileMap.getHeight(); j++) {
                GeomObject geomObject = new Rectangle(
                        new Point(i * tileMap.getTileWidth(), j * getTileMap().getTileHeight()),
                        32,
                        32);
                if (i == 0 ||
                        j == 0 ||
                        i == tileMap.getWidth() - 1 ||
                        j == tileMap.getHeight() - 1) {
                    Wall wall = new Wall(geomObject);
                    changed.add(wall);
                    tileMap.placeGameObject(wall);
                    continue;
                }

                if (i % 2 == 0 && j % 2 == 0) {
                    Wall wall = new Wall(geomObject);
                    changed.add(wall);
                    tileMap.placeGameObject(wall);
                }
                if ((i % 2 != 0 ||
                        j % 2 != 0) &&
                        (i > 2 || j > 2) &&
                        (i > 2 || j < tileMap.getHeight() - 3) &&
                        (i < tileMap.getWidth() -3 || j > 2) &&
                        (i < tileMap.getWidth() -3 || j < tileMap.getHeight() - 3)) {
                    Box box;
                    switch (randomizer.nextInt(bonusFactor)) {
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

        GeomObject geomObject1 = new Rectangle(new Point(32, 32), 30, 30);
        GeomObject geomObject2 = new Rectangle(new Point((tileMap.getWidth() - 2) * tileMap.getTileWidth(), 32), 30, 30);

        Girl girl1 = new Girl(geomObject1, 0.1f);
        girl1.addMoveEventListener(this);
        girl1.addbombPlacedListener(this);
        Girl girl2 = new Girl(geomObject2, 0.1f);
        girl2.addMoveEventListener(this);
        girl2.addbombPlacedListener(this);
        players.put(girl1.getId(), girl1);
        players.put(girl2.getId(), girl2);

        tileMap.placeGameObject(girl1);
        tileMap.placeGameObject(girl2);

        changed.add(girl1);
        changed.add(girl2);

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
        changed.add(players.get(playerId));
    }



    public void handleBombEvent(long playerId) {
        Girl girl = players.get(playerId);
        girl.setBomb();


    }

    public void update() {
        changed.clear();
        deleted.clear();
        fires.forEach(fire -> {
            deleted.add(fire);
        } );
        fires.clear();
    }

    @Override
    public boolean getMovePermission(FormedGameObject geomObject, MovableFormedGameObject gameObject) {
        for (FormedGameObject formedGameObject : tileMap.getNearbyGameObjects(geomObject)) {
            if((formedGameObject instanceof Box ||
                    formedGameObject instanceof Wall ||
                    formedGameObject instanceof Girl) &&
                    formedGameObject.getCollider().isColliding(geomObject.getCollider())) {
                return false;
            }
        }
        return true;
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

        while(offset < radius + 1 && !isStopped) {
            fireForm = new Rectangle(
                    new Point(bomb.getPosition().getX()  + kx * offset * 30,
                            bomb.getPosition().getY() + ky * offset * 30),
                    30,
                    30);
            ++offset;
            Fire fire = new Fire(fireForm);

            for (FormedGameObject formedGameObject : tileMap.getNearbyGameObjects(fire)) {

                if(formedGameObject instanceof Box &&
                        formedGameObject.getCollider().isColliding(fire.getCollider())) {
                    ((Box) formedGameObject).collapse();
                    deleted.add(formedGameObject);
                    tileMap.removeGameObject(formedGameObject);
                } else if(formedGameObject instanceof Girl &&
                        formedGameObject.getCollider().isColliding(fire.getCollider())) {
                    deleted.add(formedGameObject);
                    tileMap.removeGameObject(formedGameObject);
                } else if (formedGameObject instanceof Wall &&
                            formedGameObject.getCollider().isColliding(fire.getCollider())) {
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
    public void handleMoveEvent(FormedGameObject geomObject, MovableFormedGameObject gameObject) {
        tileMap.removeGameObject(geomObject);
        tileMap.placeGameObject(gameObject);
        for (FormedGameObject formedGameObject : tileMap.getNearbyGameObjects(gameObject)) {
            if(formedGameObject instanceof Feed) {
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
        if(box.getFeedType() != Feed.FeedType.EMPTY ) {
            GeomObject geomObject = new Rectangle(
                    new Point(box.getPosition().getX(), box.getPosition().getY()),
                    32,
                    32);
            Feed feed = new Feed(geomObject, box.getFeedType());
            tileMap.placeGameObject(feed);
            changed.add(feed);
        }
    }

    @Override
    public void handleBombPlaceEvent(Girl girl) {
        Rectangle form = new Rectangle(
                new Point(girl.getPosition().getX(), girl.getPosition().getY()),
                28,
                28);
        Bomb bomb = new Bomb(form, girl.getBombExplosRadius(),2000);
        bomb.addExplosEventListener(this);
        bomb.addExplosEventListener(girl);
        changed.add(bomb);
        ticker.registerTickable(bomb);

    }


}
