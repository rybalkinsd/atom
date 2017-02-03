package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ru.atom.model.World;
import ru.atom.model.actor.Pawn;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;
import ru.atom.network.message.Broker;
import ru.atom.network.message.Topic;
import ru.atom.util.V;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sergey on 2/3/17.
 */
public class Game {
    private final static Logger log = LogManager.getLogger(Game.class);
    private static final Game game = new Game();
    private final long sleepTime = 1_000;
    private long tickNumber = 0;

    private World world = new World();

    public void start() {
        long started = System.currentTimeMillis();
        act(sleepTime);
        long elapsed = System.currentTimeMillis() - started;
        if (elapsed < sleepTime) {
            log.info("All tickers finish at {} ms", elapsed);
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(sleepTime - elapsed));
        } else {
            log.warn("tick lag {} ms", elapsed - sleepTime);
        }
        log.info("{}: tick ", tickNumber);
        tickNumber++;
    }

    private void act(long time) {
        world.tick(time);
        Broker.broadcast(Topic.REPLICA, world);
    }

    public static Game getInstance() {
        return game;
    }

    private Game() { }

    public synchronized void register(Session session, Player player) {
        Pawn pawn = new Pawn(V.ZERO);
        world.register(pawn);
        player.setPawn(pawn);
        ConnectionPool.putIfAbsent(session, player);

    }

}
