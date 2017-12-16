package matchmaker.connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionQueue {
    private static BlockingQueue<String> instance;

    public static BlockingQueue<String> getInstance() {
        if (instance == null) {
            synchronized (Connections.class) {
                if (instance == null) {
                    instance = new LinkedBlockingQueue<>();
                }
            }
        }
        return instance;
    }
}
