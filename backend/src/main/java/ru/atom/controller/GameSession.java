package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.World;
import ru.atom.model.actor.Pawn;
import ru.atom.network.Player;
import ru.atom.network.message.Broker;
import ru.atom.network.message.Topic;
import ru.atom.util.V;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sergey on 2/3/17.
 */
public class GameSession implements Runnable {
    private final static Logger log = LogManager.getLogger(GameSession.class);
    private final static long SLEEP_TIME = 1_000;
    private final Broker broker;
    private final World world;
    private final List<Player> players;

    private long tickNumber = 0;


    GameSession(@NotNull List<Player> players) {
        this.players = players;
        broker = Broker.getInstance();
        world = new World();
        for (Player player : players) {
            Pawn pawn = new Pawn(V.of(1, 6));
            world.register(pawn);
            player.setPawn(pawn);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(SLEEP_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < SLEEP_TIME) {
                log.info("All tickers finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(SLEEP_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - SLEEP_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    private void act(long time) {
        world.tick(time);
        broker.broadcast(Topic.REPLICA, world);
    }

}
