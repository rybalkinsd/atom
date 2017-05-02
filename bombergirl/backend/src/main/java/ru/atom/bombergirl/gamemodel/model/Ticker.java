package ru.atom.bombergirl.gamemodel.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.bombergirl.mmserver.GameSession;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
/**
 * Created by dmitriy on 01.05.17.
 */

public class Ticker {
    private static final Logger log = LogManager.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private long tickNumber = 0;
    private GameSession gameSession;

    public Ticker(GameSession gameSession) {
        this.gameSession = gameSession;
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
        }
    }

    private void act(long time) {
        //Your logic here
        gameSession.tick(time);
    }

    public long getTickNumber() {
        return tickNumber;
    }
}