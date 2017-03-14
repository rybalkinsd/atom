package ru.atom.thread.mm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sergey on 3/14/17.
 */
class ThreadSafeQueue {
    private static BlockingQueue<Connection> instance = new LinkedBlockingQueue<>();

    static BlockingQueue<Connection> getInstance() {
        return instance;
    }
}
