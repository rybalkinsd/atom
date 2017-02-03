package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.World;
import ru.atom.network.message.Broker;
import ru.atom.network.message.Topic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sergey on 2/3/17.
 */
public class Game {
    private final static Logger log = LogManager.getLogger(Game.class);
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
}
