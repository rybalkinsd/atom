package gameserver;

import boxes.ConnectionPool;
import boxes.InputQueue;
import gameobjects.GameSession;
import gameobjects.Tickable;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private ConcurrentLinkedQueue<Tickable> tickables = new ConcurrentLinkedQueue<>();
    private long tickNumber = 0;
    private GameSession gameSession;

    public void gameLoop(GameSession gameSession) {
        this.gameSession = gameSession;
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            InputQueue.getInstance().clear();
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            Replicator replicator = new Replicator();
            replicator.writeReplica(gameSession);
            log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
        log.info("{} registered", tickable.getClass().getSimpleName());
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    private void act(long elapsed) {
        /*Tickable[] tickers = tickables.toArray(new Tickable[tickables.size()]);
        for (int i=0; i<tickers.length;i++)
            tickers[i].tick(elapsed);*/

        tickables.forEach(tickable -> tickable.tick(elapsed));
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
