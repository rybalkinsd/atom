package matchmaker.connection;

import java.util.concurrent.ConcurrentHashMap;

public class Connections {
    private static ConcurrentHashMap<String, Long> connections;

    public static ConcurrentHashMap<String, Long> getInstance() {
        if (connections == null) {
            synchronized (Connections.class) {
                if (connections == null) {
                    connections = new ConcurrentHashMap<>();
                }
            }
        }
        return connections;
    }
}
