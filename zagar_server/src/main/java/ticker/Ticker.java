package ticker;

import model.GameConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by apomosov on 14.05.16.
 *
 * Calls tick() method for {@link Tickable} object with {@see GameConstants.TICKS_PER_SECOND} period
 */
public class Ticker {
    private final static Logger log = LogManager.getLogger(Ticker.class);

    private final Duration sleepTime;
    private final AtomicLong tickNumber;
    private final Tickable tickable;

    public Ticker(Tickable tickable) {
        this.tickable = tickable;
        this.tickNumber = new AtomicLong(0);
        this.sleepTime = Duration.ofMillis(TimeUnit.SECONDS.toMillis(1) / GameConstants.TICKS_PER_SECOND);
    }

    public void loop() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Duration elapsed = sleepTime;
                Duration started = Duration.ofMillis(System.currentTimeMillis());
                tickable.tick(elapsed);
                elapsed = Duration.ofMillis(System.currentTimeMillis()).minus(started);
                if (elapsed.compareTo(sleepTime) < 0) {
                    log.trace("All tickers finish at {} ms", elapsed.toMillis());
                    Thread.sleep(sleepTime.toMillis());
                } else {
                    log.warn("tick lag {} ms", sleepTime.minus(elapsed).toMillis());
                }
                log.trace("{} <tick> {}", tickable, tickNumber.incrementAndGet());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
