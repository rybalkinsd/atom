package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.geometry.Point;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.model.GameSession;
import ru.atom.websocket.model.Movable;
import ru.atom.websocket.model.Player;
import ru.atom.websocket.network.Broker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker extends Thread {
    private static final Logger log = LogManager.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private long tickNumber = 0;
    private GameSession gameSession = new GameSession();
    private static final Object lock = new Object();


    @Deprecated
    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
    @Deprecated
    public GameSession getGameSession() {
        return gameSession;
    }

    public void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
            if(Thread.currentThread().isInterrupted()) {
                break;
            }
            // TODO: 06.05.17   проверка на необходимость чутка почистить пул.
            synchronized (lock) {
                Broker.getInstance().broadcast(localPool, Topic.REPLICA, gameSession.getGameObjects());
            }
        }
    }

    private void act(long time) {
        synchronized (lock) {
            gameSession.tick(time);//Your logic here
        }
    }

    public long getTickNumber() {
        return tickNumber;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see Thread#run()
     */
    @Override
    public void run() {
        super.run();
        log.info("The Game begins!");
        loop();
        log.info("Rezults must be written somewhere");
    }

    /**
     * Here is represented the Game's logic:
     * collection commands to Pawns
     * and reply REPLICA to Broker
     */
    private static final int PARALLELISM_LEVEL = 4;
    private ConcurrentHashMap<Session, String> localPool = new ConcurrentHashMap<>();//связь session<->login
    private ConcurrentHashMap<String, Integer> playerPawn = new ConcurrentHashMap<>(PARALLELISM_LEVEL);//связь idPawn<->login
    private static final Point[] startPoint = {new Point(481,352), new Point(32, 352),
            new Point(481, 32), new Point(32, 32)};

    /**
     * initial method, which chould be used before game is started
     * @param session
     * @param login
     * @return pawnId, connected with session, or -1 if Thread is running
     */
    public void addPawn (Session session, String login) {
        log.info("localPool.size() before add: {}", localPool.size());
        localPool.putIfAbsent(session, login);
        log.info("localPool.size() after add: {}", localPool.size());
        int playerId = gameSession.getCurrentId();
        log.info("player with login {} has new pawn with id {}", login, playerId);
        playerPawn.put(login, playerId);
        // TODO: 06.05.17   неправильно расставляются игроки в случае лива)
        gameSession.addGameObject(new Player(playerId, startPoint[playerId%4])); // TODO: 06.05.17   MAX_PLAYERS
        Broker.getInstance().send(login, Topic.POSSESS, playerId);
        synchronized (lock) {
            Broker.getInstance().broadcast(localPool, Topic.REPLICA, gameSession.getGameObjects());
        }
    }

    /**
     * Not ThreadSafe.
     * @param session
     * @return
     */
    public int killPawn(Session session) {
        log.info("localPool.size() before killPawn: {}", localPool.size());
        int pawnId;
        pawnId = playerPawn.remove(localPool.remove(session)); //return pawnId in GameSession
        gameSession.killPawn(pawnId);
        log.info("localPool.size() after killPawn: {}", localPool.size());
        return pawnId;
    }

    public void plantBomb(Session session) {
        if (Thread.currentThread().isAlive()) {
            log.info("{} will place bomb", localPool.get(session));
            synchronized (lock) {
                gameSession.plantBomb(playerPawn.get(localPool.get(session)));
            }
        } else {
            log.info("{} will not place bomb", localPool.get(session));
        }
    }

    public void move(Session session, Movable.Direction direction) {
        synchronized (lock) {
            if (Thread.currentThread().isAlive()) {
                log.info("{} will move in direction {}", localPool.get(session), direction);
                synchronized (lock) {
                    gameSession.movePawn(playerPawn.get(localPool.get(session)), direction);
                }
            } else {
                log.info("{} will not move in direction {}", localPool.get(session), direction);
            }
        }
    }

    public int numberOfPlayers() {
        return localPool.size();
    }

    public boolean containSession(Session session) {
        return localPool.get(session) != null;
    }
}
