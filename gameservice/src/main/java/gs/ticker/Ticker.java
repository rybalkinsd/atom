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
                gameSession.addGameObject(bomb);
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
        ArrayList<Bomb> bombList = new ArrayList<>();
        ArrayList<Brick> brickList = new ArrayList<>();
        ArrayList<Girl> girlList = new ArrayList<>();
        ArrayList<Fire> fireList = new ArrayList<>();

        boolean horizontalRight = true;
        boolean verticalUp = true;
        boolean horizontalLeft = true;
        boolean verticalDown = true;
        boolean notBonus = true;

        for (Fire fire : gameSession.getFire()) {
            if (fire.dead())
                fireList.add(fire);
        }
        for (Bomb bomb : gameSession.getBombs()) {
            if (bomb.dead()) {
                bombList.add(bomb);
                Bar barBombVerticalUp1 = new Bar(Point.getUp1Position(bomb.getPosition()));
                Bar barBombVerticalUp2 = new Bar(Point.getUp2Position(bomb.getPosition()));
                Bar barBombHorizontalRight1 = new Bar(Point.getRight1Position(bomb.getPosition()));
                Bar barBombHorizontalRight2 = new Bar(Point.getRight2Position(bomb.getPosition()));

                Bar barBombVerticalDown1 = new Bar(Point.getDown1Position(bomb.getPosition()));
                Bar barBombVerticalDown2 = new Bar(Point.getDown2Position(bomb.getPosition()));
                Bar barBombHorizontalLeft1 = new Bar(Point.getLeft1Position(bomb.getPosition()));
                Bar barBombHorizontalLeft2 = new Bar(Point.getLeft2Position(bomb.getPosition()));

                for (Wall wall : gameSession.getWalls()) {
                    Bar barWall = wall.getBar();
                    if (!barWall.isColliding(barBombHorizontalRight1)) {
                        horizontalRight = false;
                    }
                    if (!barWall.isColliding(barBombVerticalUp1)) {
                        verticalUp = false;
                    }
                    if (!barWall.isColliding(barBombHorizontalLeft1)) {
                        horizontalLeft = false;
                    }
                    if (!barWall.isColliding(barBombVerticalDown1)) {
                        verticalDown = false;
                    }
                }
                for (Brick brick : gameSession.getBricks()) {
                    Bar barBrick = brick.getBar();
                    if (verticalUp && !barBrick.isColliding(barBombVerticalUp1)) {
                        brickList.add(brick);
                        continue;
                    }
                    if (horizontalRight &&  !barBrick.isColliding(barBombHorizontalRight1)) {
                        brickList.add(brick);
                        continue;
                    }
                    if (verticalDown && !barBrick.isColliding(barBombVerticalDown1)) {
                        brickList.add(brick);
                        continue;
                    }
                    if (horizontalLeft && !barBrick.isColliding(barBombHorizontalLeft1)) {
                        brickList.add(brick);
                    }
                    /*if (verticalUp && (!barBrick.isColliding(barBombVerticalUp2)
                            || !barBrick.isColliding(barBombVerticalUp1))) {
                        brickList.add(brick);
                        continue;
                    }
                    if (horizontalRight && (!barBrick.isColliding(barBombHorizontalRight2)
                            || !barBrick.isColliding(barBombHorizontalRight1))) {
                        brickList.add(brick);
                        continue;
                    }
                    if (verticalDown && (!barBrick.isColliding(barBombVerticalDown2)
                            || !barBrick.isColliding(barBombVerticalDown1))) {
                        brickList.add(brick);
                        continue;
                    }
                    if (horizontalLeft && (!barBrick.isColliding(barBombHorizontalLeft2)
                            || !barBrick.isColliding(barBombHorizontalLeft1))) {
                        brickList.add(brick);
                    }*/
                }
                for (Girl girl : gameSession.getGirls()) {
                    Bar barGirl = girl.getBar();
                    /*if (verticalUp && (!barGirl.isColliding(barBombVerticalUp2)
                            || !barGirl.isColliding(barBombVerticalUp1))) {
                        girlList.add(girl);
                        continue;
                    }*/
                    if (verticalUp && !barGirl.isColliding(barBombVerticalUp1)) {
                        girlList.add(girl);
                        continue;
                    }
                    if (horizontalRight &&  !barGirl.isColliding(barBombHorizontalRight1)) {
                        girlList.add(girl);
                        continue;
                    }
                    if (verticalDown &&  !barGirl.isColliding(barBombVerticalDown1)) {
                        girlList.add(girl);
                        continue;
                    }
                    if (horizontalLeft && !barGirl.isColliding(barBombHorizontalLeft1)) {
                        girlList.add(girl);
                    }
                    /*if (horizontalRight && (!barGirl.isColliding(barBombHorizontalRight2)
                            || !barGirl.isColliding(barBombHorizontalRight1))) {
                        girlList.add(girl);
                        continue;
                    }
                    if (verticalDown && (!barGirl.isColliding(barBombVerticalDown2)
                            || !barGirl.isColliding(barBombVerticalDown1))) {
                        girlList.add(girl);
                        continue;
                    }
                    if (horizontalLeft && (!barGirl.isColliding(barBombHorizontalLeft2)
                            || !barGirl.isColliding(barBombHorizontalLeft1))) {
                        girlList.add(girl);
                    }*/
                }
            }
        }
        for (Bomb bomb : bombList) {
            notBonus = true;
            gameSession.addGameObject(new Fire(gameSession, bomb.getPosition()));
            if (verticalUp) {
                gameSession.addGameObject(new Fire(gameSession, Point.getUp1Position(bomb.getPosition())));
                if (notBonus && isBonus()) {
                    gameSession.addGameObject(new Bonus(gameSession, Point.getUp1Position(bomb.getPosition()), randomBonus()));
                    notBonus = false;
                    System.out.println("BONUS");
                }
                /*if (gameSession.getGameObjectByPosition(Point.getUp2Position(bomb.getPosition())) == null
                        || !Objects.equals("Wall",
                        gameSession.getGameObjectByPosition(Point.getUp2Position(bomb.getPosition())).getType()))
                    gameSession.addGameObject(new Fire(gameSession, Point.getUp2Position(bomb.getPosition())));*/
            }
            if (verticalDown) {
                gameSession.addGameObject(new Fire(gameSession, Point.getDown1Position(bomb.getPosition())));
                if (notBonus && isBonus()) {
                    gameSession.addGameObject(new Bonus(gameSession, Point.getDown1Position(bomb.getPosition()), randomBonus()));
                    notBonus = false;
                    System.out.println("BONUS");
                }
                /*if (gameSession.getGameObjectByPosition(Point.getDown2Position(bomb.getPosition())) == null
                        || !Objects.equals("Wall",
                        gameSession.getGameObjectByPosition(Point.getDown2Position(bomb.getPosition())).getType()))
                    gameSession.addGameObject(new Fire(gameSession, Point.getDown2Position(bomb.getPosition())));*/
            }
            if (horizontalRight) {
                gameSession.addGameObject(new Fire(gameSession, Point.getRight1Position(bomb.getPosition())));
                if (notBonus && isBonus()) {
                    gameSession.addGameObject(new Bonus(gameSession, Point.getRight1Position(bomb.getPosition()), randomBonus()));
                    notBonus = false;
                    System.out.println("BONUS");
                }
                /*if (gameSession.getGameObjectByPosition(Point.getRight2Position(bomb.getPosition())) == null
                        || !Objects.equals("Wall",
                        gameSession.getGameObjectByPosition(Point.getRight2Position(bomb.getPosition())).getType()))
                    gameSession.addGameObject(new Fire(gameSession, Point.getRight2Position(bomb.getPosition())));*/
            }
            if (horizontalLeft) {
                gameSession.addGameObject(new Fire(gameSession, Point.getLeft1Position(bomb.getPosition())));
                if (notBonus && isBonus()) {
                    System.out.println("BONUS");
                    gameSession.addGameObject(new Bonus(gameSession, Point.getLeft1Position(bomb.getPosition()), randomBonus()));
                }
                /*if (gameSession.getGameObjectByPosition(Point.getLeft2Position(bomb.getPosition())) == null
                        || !Objects.equals("Wall",
                        gameSession.getGameObjectByPosition(Point.getLeft2Position(bomb.getPosition())).getType()))
                    gameSession.addGameObject(new Fire(gameSession, Point.getLeft2Position(bomb.getPosition())));*/
            }
            gameSession.removeGameObject(bomb);
        }

        for (Brick brick : brickList) {
            gameSession.removeGameObject(brick);
        }
        for (Girl girl : girlList) {
            gameSession.removeGameObject(girl);
        }
        for (Fire fire : fireList) {
            gameSession.removeGameObject(fire);
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
