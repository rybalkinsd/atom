package utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class SequentialIDGenerator {
    private final AtomicInteger current = new AtomicInteger(0);
    public int next() {
        return current.getAndIncrement();
    }
}
