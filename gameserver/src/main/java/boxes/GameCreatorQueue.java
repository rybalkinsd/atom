package boxes;

import java.util.concurrent.ConcurrentLinkedQueue;


public class GameCreatorQueue {
    private static ConcurrentLinkedQueue<Integer> instance = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<Integer> getInstance() {
        return instance;
    }
}

