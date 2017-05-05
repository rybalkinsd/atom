package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.Ticker;
import ru.atom.geometry.Point;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.network.ConnectionPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BBPax on 02.05.17.
 */
public class GameThread extends Thread {
    private static final Logger log = LogManager.getLogger(GameThread.class);
    private static final int PARALLELISM_LEVEL = 4;
    Ticker ticker;
    ConcurrentHashMap<Session, Integer> localPool;//связь session<->idPawn
    private ConcurrentHashMap<Integer, String> playerPawn = new ConcurrentHashMap<>(4);//связь idPawn<->login

    public GameThread() {
        ticker = new Ticker();
        localPool = new ConcurrentHashMap<>(PARALLELISM_LEVEL + 1);
    }

    public boolean hasConnection(Session session) {
        return localPool.get(session) != null;
    }

    public int numberOfPlayers() {
        return localPool.size();
    }

    public int addPawn(Session session, String login, Point position) {
        GameSession gameSession = ticker.getGameSession();
        int playerId = gameSession.getCurrentId();
        log.info("player with login {} has new pawn with id {}", login, playerId);
        playerPawn.put(playerId, login);
        localPool.put(session, playerId);
        gameSession.addGameObject(new Player(playerId, position));
        return playerId;
    }

    public void plantBomb(Session session) {
        int playerId = localPool.get(session);
        Player player = (Player) ticker.getGameSession().getGameObjects().get(playerId);
        log.info("Bomb has been planted by {} in point: ({};{})",
                playerPawn.get(playerId), player.getPosition().getX(), player.getPosition().getY());
        // TODO: 02.05.17   надо добавить взаимодействие с GameSession
    }

    public void move(Session session, Movable.Direction direction) {
        int playerId = localPool.get(session);
        Player player = (Player) ticker.getGameSession().getGameObjects().get(playerId);
        log.info("Player {} should be moved in direction {}",
                playerPawn.get(playerId), direction);
        // TODO: 02.05.17   надо добавить взаимодействие с GameSession
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        log.info("The Game begins!");
        ticker.loop();
    }
}
