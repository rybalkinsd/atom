package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.geometry.Point;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.model.Action;
import ru.atom.websocket.model.GameSession;
import ru.atom.websocket.model.Movable;
import ru.atom.websocket.model.Player;
import ru.atom.websocket.network.Broker;
import ru.atom.websocket.server.GameManager;
import ru.atom.websocket.util.JsonHelper;
import ru.atom.websocket.util.MatchMakerClient;
import ru.atom.websocket.util.Result;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static ru.atom.WorkWithProperties.getProperties;

public class Ticker extends Thread {
    private static final Logger log = LogManager.getLogger(Ticker.class);
    private static final int FPS = Integer.valueOf(getProperties().getProperty("TICKER_FPS"));
    private static final long FRAME_TIME =
            Integer.valueOf(getProperties().getProperty("TICKER_FRAME_TIME")) / FPS;
    private long tickNumber = 0;
    private GameSession gameSession = new GameSession();
    private Result result = new Result();
    private static final Object lock = new Object();

    public Ticker setId(int id) {
        result.setgameId(id);
        return this;
    }

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
                log.info("All tick finish at {} ms except {} ms", elapsed, FRAME_TIME);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
            if (Thread.currentThread().isInterrupted()) {
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
            // TODO: 14.05.17 надо что то сделать
            actions.forEach(TICKER_PARALLELISM_LEVEL, (pawnId, action) -> action.applyAction(gameSession, pawnId));
            log.info("===================== Actions ======================");
            actions.forEach(TICKER_PARALLELISM_LEVEL, (pawnId, action) ->
                    log.info("Action of player {} is {}", pawnId, action));
            log.info("====================================================");
            actions.clear();
            if (numberOfPlayers() == 1) {
                finishGame();
            }
            gameSession.tick(time);//Your logic here
        }
    }

    private void finishGame() {
        Player winner = (Player)gameSession.getGameObjects()
                .stream().filter(gameObject -> gameObject instanceof Player).findAny().get();
        playerPawn.forEach(4, (login, id) -> result.addScore(login, winner.getId() == id ? 30 : 10));
        log.info("result is: {}", JsonHelper.toJson(result));
        Broker.getInstance().broadcast(localPool, Topic.END_MATCH, JsonHelper.toJson(result));
        // TODO: 17.05.17   тут надо отправить в Broker сообщение об окончании игры для всех игроков
        try {
            log.info("answer: ", MatchMakerClient.finish(JsonHelper.toJson(result)));
        } catch (IOException e) {
            log.error(e);
        }
        Thread.currentThread().interrupt();
    }

    public long getTickNumber() {
        return tickNumber;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * Subclasses of <code>Thread</code> should override this method.
     * @see #start()
     * @see #stop()
     * @see Thread#run()
     */
    @Override
    public void run() {
        super.run();
        log.info("The Game begins!");
        loop();
        log.info("Results must be written somewhere");
    }

    /**
     * Here is represented the Game's logic:
     * collection commands to Pawns
     * and reply REPLICA to Broker
     */
    private static final int TICKER_PARALLELISM_LEVEL = Integer.valueOf(getProperties()
            .getProperty("TICKER_PARALLELISM_LEVEL"));
    private ConcurrentHashMap<Session, String> localPool = new ConcurrentHashMap<>(); //связь session<->login
    private ConcurrentHashMap<String, Integer> playerPawn = new ConcurrentHashMap<>(TICKER_PARALLELISM_LEVEL);
    //idPawn<->login
    private ConcurrentHashMap<Integer, Action> actions = new ConcurrentHashMap<>();
    private static final Point[] startPoint = new Point[]
    {
        new Point(481,352),
        new Point(32, 352),
        new Point(481, 32),
        new Point(32, 32)
    };

    /**
     * initial method, which chould be used before game is started
     * @param session session of player
     * @param login login of player
     * @return pawnId, connected with session, or -1 if Thread is running
     */
    public void addPawn(Session session, String login) {
        log.info("localPool.size() before add: {}", localPool.size());
        localPool.putIfAbsent(session, login);
        log.info("localPool.size() after add: {}", localPool.size());
        int playerId = gameSession.getCurrentId();
        log.info("player with login {} has new pawn with id {}", login, playerId);
        playerPawn.put(login, playerId);
        // TODO: 06.05.17   неправильно расставляются игроки в случае лива)
        synchronized (lock) {
            gameSession.addGameObject(new Player(playerId, startPoint[numberOfPlayers() % 4]));
            // TODO: 06.05.17   MAX_PLAYERS
            Broker.getInstance().send(login, Topic.POSSESS, playerId);
            Broker.getInstance().broadcast(localPool, Topic.REPLICA, gameSession.getGameObjects());
        }
    }

    /**
     * Not ThreadSafe.
     * @param session session of player
     * @return id of pawn
     */
    public int removePawn(Session session) {
        log.info("localPool.size() before killPawn: {}", localPool.size());
        int pawnId;
        pawnId = playerPawn.remove(localPool.remove(session)); //return pawnId in GameSession
        log.info("localPool.size() after killPawn: {}", localPool.size());
        synchronized (lock) {
            gameSession.removePawn(pawnId);
        }
        return pawnId;
    }

    /**
     * killing pawn in game, when connection is lost
     * @param session session of player
     * @return id of pawn
     */
    public int killPawn(Session session) {
        int pawnId = playerPawn.get(localPool.remove(session));
        actions.put(pawnId, Action.DIE);
        return pawnId;
    }

    public void plantBomb(Session session) {
        if (Thread.currentThread().isAlive()) {
            log.info("{} will place bomb", localPool.get(session));
            actions.put(playerPawn.get(localPool.get(session)), Action.BOMB_PLANT);
        } else {
            log.info("{} will not place bomb", localPool.get(session));
        }
    }

    public void move(Session session, Movable.Direction direction) {
        if (Thread.currentThread().isAlive()) {
            log.info("{} will move in direction {}", localPool.get(session), direction);
            actions.put(playerPawn.get(localPool.get(session)), direction.makeAction());
        } else {
            log.info("{} will not move in direction {}", localPool.get(session), direction);
        }
    }

    public int numberOfPlayers() {
        synchronized (lock) {
            return gameSession.numberOfPlayers();
        }
    }

    public boolean containSession(Session session) {
        return localPool.get(session) != null;
    }
}
