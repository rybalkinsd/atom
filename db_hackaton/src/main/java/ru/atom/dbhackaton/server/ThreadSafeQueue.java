package ru.atom.dbhackaton.server;

import ru.atom.dbhackaton.server.model.User;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pavel on 17.04.17.
 */
public class ThreadSafeQueue {
    private static final BlockingQueue<User> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<User> getInstance() {
        return instance;
    }
}
