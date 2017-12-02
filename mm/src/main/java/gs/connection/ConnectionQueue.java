package gs.connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionQueue {
    private static BlockingQueue<String> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<String> getInstance() {
        return instance;
    }
}
