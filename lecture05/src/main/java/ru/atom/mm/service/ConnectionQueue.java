package ru.atom.mm.service;

import org.springframework.stereotype.Repository;
import ru.atom.mm.model.Connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sergey on 3/14/17.
 */
@Repository
public class ConnectionQueue {
    private BlockingQueue<Connection> queue = new LinkedBlockingQueue<>();

    public BlockingQueue<Connection> getQueue() {
        return queue;
    }
}
