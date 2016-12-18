package utils.idGeneration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alpi
 * @since 31.10.16
 *
 * Thread-safe sequential ID generator
 */
public class SequentialIDGenerator implements IDGenerator {
    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public int next() {
        return current.getAndIncrement();
    }
}
