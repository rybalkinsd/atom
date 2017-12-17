package bomber.games.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GeneratorIdSession {
    private static final AtomicLong idGenerator = new AtomicLong(0);


    public static long getAndIncrementId() {
        return idGenerator.getAndIncrement();
    }

    public static long getIdGenerator() {
        return idGenerator.get();
    }
}
