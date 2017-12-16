package ru.atom.thread.mm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionQueue {
    private static BlockingQueue<GameSession> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<GameSession> getInstance() {
        return instance;
    }
}
