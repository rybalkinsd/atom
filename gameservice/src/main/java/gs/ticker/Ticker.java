package gs.ticker;

import gs.geometry.Bar;
import gs.geometry.Point;
import gs.message.Topic;
import gs.model.GameObject;
import gs.model.GameSession;
import gs.model.Girl;
import gs.model.Bomb;
import gs.model.Wall;
import gs.model.Fire;
import gs.model.Bonus;
import gs.model.Brick;
import gs.model.Movable;

import gs.model.Tickable;
import gs.network.Broker;
import gs.storage.SessionStorage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static gs.message.Topic.GAME_OVER;

public class Ticker extends Thread {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;
    private final Broker broker = Broker.getInstance();
    private final Queue<Action> inputQueue = new LinkedBlockingQueue<Action>();

    @Autowired
    SessionStorage storage;

    private boolean isRunning;
    private GameSession gameSession;
    private ArrayList<Girl> movedGirls = new ArrayList<>(4);
    private ArrayList<GameObject> changedObjects = new ArrayList<>();
    private ArrayList<Girl> deadGirls = new ArrayList<>();

    public Ticker(GameSession session) {
        this.gameSession = session;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isRunning) {
                long started = System.currentTimeMillis();
                changedObjects.clear();
                deadGirls.clear();
                handleQueue();
                act(FRAME_TIME);
                checkCollisions();
                detonationBomb();
                sendReplica();
                long elapsed = System.currentTimeMillis() - started;
                if (elapsed < FRAME_TIME) {
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
                } else {
                    log.warn("tick lag {} ms", elapsed - FRAME_TIME);
                }
            } else {
                log.info("THE END!");
                return;
            }
        }
    }

    public void putAction(Action action) {
        inputQueue.offer(action);
    }

    private void sendReplica() {
        for (WebSocketSession session : storage.getWebsocketsByGameSession(gameSession)) {
            broker.send(session, Topic.REPLICA, changedObjects);
            storage.getGirlBySocket(session).setDirection(Movable.Direction.IDLE);
        }

        ArrayList<WebSocketSession> sessions = storage.getWebsocketsByGameSession(gameSession);
        ArrayList<WebSocketSession> closed = new ArrayList<>();
        for (WebSocketSession session : sessions) {
            for (Girl girl : deadGirls) {
                if (storage.getGirlBySocket(session) == girl) {
                        closed.add(session);
                        System.out.println("SESSION " + storage.getWebsocketByGirl(girl));
                        Broker.getInstance().send(session, GAME_OVER, "YOU LOSE");
                }
            }
        }
        for (WebSocketSession session: closed) {
            try {
                session.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleQueue() {
        movedGirls.clear();
        for (Action action : inputQueue) {
            if (action.getAction().equals(Topic.PLANT_BOMB)
                    && action.getActor().getBombCapacity() != 0) {
                Bomb bomb = new Bomb(gameSession,
                        closestPoint(action.getActor().getPosition()), action.getActor());
                action.getActor().decBombCapacity();
                gameSession.addGameObject(bomb);
                changedObjects.add(bomb);
            }
            if (action.getAction().equals(Topic.MOVE)
                    && !movedGirls.contains(action.getActor())) {
                action.getActor().setDirection(action.getData());
                movedGirls.add(action.getActor());
            }
        }
        inputQueue.clear();
    }

    private void act(int elapsed) {
        for (GameObject object : gameSession.getGameObjects()) {
            if (object instanceof Tickable)
                ((Tickable) object).tick(elapsed);
        }
    }

    public void checkCollisions() {
        for (Girl girl : gameSession.getGirls()) {
            Bar barGirl = girl.getGirlBar();
            for (Wall wall : gameSession.getWalls()) {
                Bar barWall = wall.getBar();
                if (!barGirl.isColliding(barWall))
                    girl.moveBack(FRAME_TIME);
            }
            for (Brick brick : gameSession.getBricks()) {
                Bar barBrick = brick.getBar();
                if (!barGirl.isColliding(barBrick))
                    girl.moveBack(FRAME_TIME);
            }
            for (Bonus bonus : gameSession.getBonuses()) {
                Bar barBonus = bonus.getBar();
                if (!barGirl.isColliding(barBonus)) {
                    girl.takeBonus(bonus);
                    changedObjects.add(bonus);
                    gameSession.removeGameObject(bonus);
                }
            }
            for (Bomb bomb : gameSession.getBombs()) {
                Bar barBomb = bomb.getBar();
                if (!barGirl.isColliding(barBomb) && !bomb.getOwner().equals(girl))
                    girl.moveBack(FRAME_TIME);
            }
            changedObjects.add(girl);
        }
    }

    public void kill() {
        isRunning = false;
    }

    public Point closestPoint(Point point) {
        double modX = point.getX() % GameObject.getWidthBox();
        double modY = point.getY() % GameObject.getHeightBox();
        int divX = (int) point.getX() / (int) GameObject.getWidthBox();
        int divY = (int) point.getY() / (int) GameObject.getHeightBox();
        if (modX < GameObject.getWidthBox() / 2) {
            if (modY < GameObject.getHeightBox() / 2)
                return new Point(divX * GameObject.getWidthBox(),
                        divY * GameObject.getHeightBox());
            else
                return new Point(divX * GameObject.getWidthBox(),
                        (divY + 1) * GameObject.getHeightBox());
        } else if (modY < 16)
            return new Point((divX + 1) * GameObject.getWidthBox(),
                    divY * GameObject.getWidthBox());
        return new Point((divX + 1) * GameObject.getWidthBox(),
                (divY + 1) * GameObject.getHeightBox());
    }


    public void detonationBomb() {
        ArrayList<GameObject> objectList = new ArrayList<>();
        for (Fire fire : gameSession.getFire()) {
            if (fire.dead()) {
                objectList.add(fire);
            }
        }

        for (Bomb bomb : gameSession.getBombs()) {
            if (bomb.dead()) {
                bomb.getOwner().incBombCapacity();
                objectList.add(bomb);
                changedObjects.add(bomb);

                Fire currentFire = new Fire(gameSession, bomb.getPosition());
                changedObjects.add(currentFire);
                gameSession.addGameObject(currentFire);

                ArrayList<ArrayList<Bar>> explosions = Bar.getExplosions(
                        Point.getExplosions(bomb.getPosition(), bomb.getOwner().getBombRange()));

                for (int i = 0; i < explosions.size(); i++) {
                    for (int j = 0; j < explosions.get(i).size(); j++) {
                        for (Girl girl : gameSession.getGirls()) {
                            if (!girl.getGirlBar().isColliding(explosions.get(i).get(j))) {
                                deadGirls.add(girl);
                                changedObjects.remove(girl);
                            }
                        }
                        if (gameSession.getGameObjectByPosition(explosions
                                .get(i).get(j).getLeftPoint()) == null) {
                            Fire fire = new Fire(gameSession, explosions.get(i).get(j).getLeftPoint());
                            changedObjects.add(fire);
                            gameSession.addGameObject(fire);
                            continue;
                        }
                        if (gameSession.getGameObjectByPosition(explosions
                                .get(i).get(j).getLeftPoint()).getType().equals("Bonus")
                                || gameSession.getGameObjectByPosition(explosions
                                .get(i).get(j).getLeftPoint()).getType().equals("Bomb")
                                || gameSession.getGameObjectByPosition(explosions
                                .get(i).get(j).getLeftPoint()).getType().equals("Pawn")) {
                            continue;
                        }
                        if (gameSession.getGameObjectByPosition(explosions.get(i).get(j)
                                .getLeftPoint()).getType().equals("Wall")) {
                            break;
                        }
                        if (gameSession.getGameObjectByPosition(explosions.get(i).get(j)
                                .getLeftPoint()).getType().equals("Wood")) {
                            objectList.add(gameSession.getGameObjectByPosition(explosions
                                    .get(i).get(j).getLeftPoint()));
                            changedObjects.add(gameSession.getGameObjectByPosition(explosions
                                    .get(i).get(j).getLeftPoint()));
                            Fire fire = new Fire(gameSession, explosions.get(i).get(j).getLeftPoint());
                            gameSession.addGameObject(fire);
                            changedObjects.add(fire);
                            if (isBonus()) {
                                Bonus bonus = new Bonus(gameSession, explosions.get(i).get(j).getLeftPoint(),
                                        bonusType());
                                changedObjects.add(bonus);
                                gameSession.addGameObject(bonus);
                            }
                            break;
                        }
                        objectList.add(gameSession.getGameObjectByPosition(explosions
                                .get(i).get(j).getLeftPoint()));
                        Fire fire = new Fire(gameSession, explosions
                                .get(i).get(j).getLeftPoint());
                        changedObjects.add(fire);
                        gameSession.addGameObject(fire);
                    }
                }
            }
        }

        for (GameObject gameObject : objectList) {
            gameSession.removeGameObject(gameObject);
        }
    }

    public boolean isBonus() {
        double random = Math.random();
        return (random < 0.3);
    }

    public Bonus.BonusType bonusType() {
        double random = Math.random();
        if (random < 0.4) return Bonus.BonusType.SPEED;
        else if (random < 0.8) return Bonus.BonusType.BOMBS;
        return Bonus.BonusType.RANGE;
    }

    public void begin() {
        isRunning = true;
    }
}
