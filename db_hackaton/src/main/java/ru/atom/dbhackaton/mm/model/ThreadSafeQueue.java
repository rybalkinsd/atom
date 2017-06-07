package ru.atom.dbhackaton.mm.model;

import ru.atom.dbhackaton.mm.model.Connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadSafeQueue {
    private static BlockingQueue<Connection> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<Connection> getInstance() {
        return instance;
    }
}