package ru.atom.bombergirl.mmserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ikozin on 17.04.17.
 */
public class ThreadSafeQueue {
    private static BlockingQueue<Connection> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<Connection> getInstance() {
        return instance;
    }
}
