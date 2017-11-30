package gs.connection;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeStorage {
    private static ConcurrentHashMap<String, Long> joins = new ConcurrentHashMap<String, Long>();

    public static ConcurrentHashMap<String, Long> getInstance() {
        return joins;
    }
}
