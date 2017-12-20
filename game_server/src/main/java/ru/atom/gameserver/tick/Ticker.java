package ru.atom.gameserver.tick;


import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private final List<Tickable> tickables = new CopyOnWriteArrayList<>();
    private long tickNumber = 0;
    private volatile boolean gameStoped = false;

    public void stopGameLoop() {
        gameStoped = true; //this is atomic operation
    }

    public void gameLoop() {
        while (!gameStoped && !Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
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
        log.info("stoped ticker");
    }

    public void insertTickableFront(Tickable tickable) {
        tickables.add(0, tickable);
    }

    public void insertTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    private void act(long elapsed) {
        tickables.forEach(tickable -> tickable.tick(elapsed));
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
