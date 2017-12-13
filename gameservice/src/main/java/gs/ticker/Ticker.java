package gs.ticker;

import gs.geometry.Point;
import gs.message.Message;
import gs.message.Topic;
import gs.model.Bomb;
import gs.model.GameSession;
import gs.model.Girl;
import gs.model.Tickable;
import gs.network.Broker;
import gs.storage.SessionStorage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker extends Thread{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;
    private GameSession gameSession;
    private Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private long tickNumber = 0;
    public static final int PLAYERS_COUNT = 2;
    private final Broker broker = Broker.getInstance();
    private final Set<String> players = new HashSet<>();
    private final ConcurrentHashMap<Integer, String> girlsIdToPlayer = new ConcurrentHashMap<Integer, String>();
    private final Queue<Action> inputQueue = new LinkedBlockingQueue<Action>();
    private ArrayList<Girl> movedGirls = new ArrayList<>(4);

    @Autowired
    SessionStorage storage;

    public Ticker(GameSession session) {
        this.gameSession = session;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            handleQueue();
            for(WebSocketSession session : storage.getWebsocketsByGameSession(gameSession)) {
                broker.send(session, Topic.REPLICA, gameSession.getGameObjects());
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
        for(Action action : inputQueue) {
            if(action.getAction().equals(Topic.PLANT_BOMB)) {
                Bomb bomb = new Bomb(gameSession, action.getActor().getPosition(), action.getActor());
                gameSession.addGameObject(bomb);
                registerTickable(bomb);
            }
            if(action.getAction().equals(Topic.MOVE) && !movedGirls.contains(action.getActor())) {
                action.getActor().move(action.getData(), FRAME_TIME);
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
        tickables.forEach(tickable -> tickable.tick(elapsed));
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
