package ru.atom.mm.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Repository;
import ru.atom.mm.model.Connection;

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
