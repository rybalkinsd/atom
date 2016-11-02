package ticker;

import main.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by apomosov on 14.05.16.
 */
public class Ticker extends Service implements Tickable {
    private final static Logger log = LogManager.getLogger(Ticker.class);

    private final ConcurrentLinkedDeque<Tickable> tickables;
    private final long sleepTimeNanos;
    private final AtomicLong tickNumber;

    public Ticker(int maxTicksPerSecond) {
        super("ticker");
        this.tickables = new ConcurrentLinkedDeque<>();
        this.tickNumber = new AtomicLong(0);
        this.sleepTimeNanos = TimeUnit.SECONDS.toNanos(1) / maxTicksPerSecond;
    }

    public void registerTickable(Tickable tickable) {
        if (!tickables.contains(tickable)) {
            tickables.add(tickable);
        } else {
            log.warn(tickable + " is already in registered in Ticker");
        }
    }

    private void loop() {
        long elapsed = sleepTimeNanos;
        while (!isInterrupted()) {
            long started = System.nanoTime();
            tick(elapsed);
            elapsed = System.nanoTime() - started;
            if (elapsed < sleepTimeNanos) {
                log.info("All tickers finish at " + TimeUnit.NANOSECONDS.toMillis(elapsed) + " ms");
                LockSupport.parkNanos(sleepTimeNanos - elapsed);
            } else {
                log.warn("tick lag " + TimeUnit.NANOSECONDS.toMillis(elapsed - sleepTimeNanos) + " ms");
            }
        }
    }

    public void tick(long elapsedNanos) {
        log.info("=== tick " + tickNumber + " ===");
        tickNumber.incrementAndGet();
      for (Tickable tickable : tickables) {
        tickable.tick(elapsedNanos);
      }
    }

    @Override
    public void run() {
        loop();
    }
}
