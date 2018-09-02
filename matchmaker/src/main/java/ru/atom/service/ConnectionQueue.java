package ru.atom.service;

import org.springframework.stereotype.Component;
import ru.atom.service.Connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ConnectionQueue {
    private static BlockingQueue<Connection> instance = new LinkedBlockingQueue<>();

    public static BlockingQueue<Connection> getInstance() {
        return instance;
    }
}
