package gs.ticker;

import gs.geometry.Bar;
import gs.geometry.Point;
import gs.message.Topic;
import gs.model.*;
import gs.network.Broker;
import gs.storage.SessionStorage;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker extends Thread {
    public static final int PLAYERS_COUNT = 2;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;
    private final Broker broker = Broker.getInstance();
    private final Set<String> players = new HashSet<>();
    private final ConcurrentHashMap<Integer, String> girlsIdToPlayer = new ConcurrentHashMap<Integer, String>();
    private final Queue<Action> inputQueue = new LinkedBlockingQueue<Action>();

    @Autowired
    SessionStorage storage;

    private GameSession gameSession;
    private Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private long tickNumber = 0;
    private ArrayList<Girl> movedGirls = new ArrayList<>(4);

    public Ticker(GameSession session) {
        this.gameSession = session;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            handleQueue();
            act(FRAME_TIME);
            checkCollisions();
            detonationBomb();
            for (WebSocketSession session : storage.getWebsocketsByGameSession(gameSession)) {
                broker.send(session, Topic.REPLICA, gameSession.getObjectsWithoutWalls());
            }
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                //log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            //log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    public void putAction(Action action) {
        inputQueue.offer(action);
    }

    private void handleQueue() {
        movedGirls.clear();
        for (Action action : inputQueue) {
            if (action.getAction().equals(Topic.PLANT_BOMB) && action.getActor().getBombCapacity() != 0) {
                Bomb bomb = new Bomb(gameSession, closestPoint(action.getActor().getPosition()), action.getActor());
                action.getActor().decBombCapacity();
                gameSession.addGameObject(bomb);
            }
            if (action.getAction().equals(Topic.MOVE) && !movedGirls.contains(action.getActor())) {
                action.getActor().setDirection(action.getData());
                movedGirls.add(action.getActor());
            }
        }
        inputQueue.clear();
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    private void act(int elapsed) {
        for (GameObject object : gameSession.getGameObjects()) {
            if (object instanceof Tickable)
                ((Tickable) object).tick(elapsed);
        }
    }

    public long getTickNumber() {
        return tickNumber;
    }

    public void doMechanic() {
        for (GameObject object : gameSession.getGameObjects()) {

        }
    }

    public void checkCollisions() {
        for (Girl girl : gameSession.getGirls()) {
            Bar barGirl = girl.getBar();
            for (Wall wall : gameSession.getWalls()) {
                Bar barWall = wall.getBar();
                if (!barGirl.isColliding(barWall)) {
                    girl.moveBack(FRAME_TIME);
                }
            }
            for (Brick brick : gameSession.getBricks()) {
                Bar barBrick = brick.getBar();
                if (!barGirl.isColliding(barBrick)) {
                    girl.moveBack(FRAME_TIME);
                }
            }
            girl.setDirection(Movable.Direction.IDLE);
        }
    }

    public Point closestPoint(Point point) {
        double modX = point.getX() % GameObject.getWidthBox();
        double modY = point.getY() % GameObject.getHeightBox();
        int divX = (int) point.getX() / (int) GameObject.getWidthBox();
        int divY = (int) point.getY() / (int) GameObject.getHeightBox();
        if (modX < GameObject.getWidthBox() / 2) {
            if (modY < GameObject.getHeightBox() / 2)
                return new Point(divX * GameObject.getWidthBox(), divY * GameObject.getHeightBox());
            else
                return new Point(divX * GameObject.getWidthBox(), (divY + 1) * GameObject.getHeightBox());
        } else if (modY < 16)
            return new Point((divX + 1) * GameObject.getWidthBox(), divY * GameObject.getWidthBox());
        return new Point((divX + 1) * GameObject.getWidthBox(), (divY + 1) * GameObject.getHeightBox());
    }


    public void detonationBomb() {
        ArrayList<GameObject> objectList = new ArrayList<>();

        for (Fire fire : gameSession.getFire()) {
            if (fire.dead())
                objectList.add(fire);
        }

        for (Bomb bomb : gameSession.getBombs()) {
            if (bomb.dead()) {
                bomb.getOwner().incBombCapacity();
                objectList.add(bomb);

                ArrayList<ArrayList<Bar>> explosions = Bar.getExplosions(Point.getExplosions(bomb.getPosition(),
                        bomb.getOwner().getBombRange()));

                for (int i = 0; i < explosions.size(); i++) {
                    for (int j = 0; j < explosions.get(i).size(); j++) {
                        for (Girl girl: gameSession.getGirls()) {
                            if (!girl.getBar().isColliding(explosions.get(i).get(j)))
                                objectList.add(girl);
                        }
                        if (gameSession.getGameObjectByPosition(explosions.get(i).get(j).getLeftPoint()) == null) {
                            gameSession.addGameObject(new Fire(gameSession, explosions.get(i).get(j).getLeftPoint()));
                            continue;
                        }
                        if (gameSession.getGameObjectByPosition(explosions.get(i).get(j).getLeftPoint()).getType().equals("Wall")) {
                            break;
                        }
                        if (gameSession.getGameObjectByPosition(explosions.get(i).get(j).getLeftPoint()).getType().equals("Wood")) {
                            objectList.add(gameSession.getGameObjectByPosition(explosions.get(i).get(j).getLeftPoint()));
                            gameSession.addGameObject(new Fire(gameSession, explosions.get(i).get(j).getLeftPoint()));
                            break;
                        }
                        objectList.add(gameSession.getGameObjectByPosition(explosions.get(i).get(j).getLeftPoint()));
                        gameSession.addGameObject(new Fire(gameSession, explosions.get(i).get(j).getLeftPoint()));
                    }
                }
            }
        }
        for (GameObject gameObject: objectList)  {
            gameSession.removeGameObject(gameObject);
        }
    }

    public String randomBonus() {
        double random = Math.random();
        if (random < 0.33) return "Speed";
        else if (random < 0.66) return "Bombs";
        return "Explosion";
    }

    public boolean isBonus() {
        double random = Math.random();
        if (random > 0.1) return true;
        return false;
    }
}
