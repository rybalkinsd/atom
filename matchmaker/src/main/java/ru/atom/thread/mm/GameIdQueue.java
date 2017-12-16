package ru.atom.thread.mm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameIdQueue {
    private static BlockingQueue<GameId> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<GameId> getInstance() {
        return instance;
    }
}
