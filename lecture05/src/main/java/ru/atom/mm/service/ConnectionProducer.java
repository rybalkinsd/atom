package ru.atom.mm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.mm.model.Connection;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
@Service
public class ConnectionProducer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConnectionProducer.class);
    private static final String[] names = {"John", "Paul", "George", "Someone else"};

    private static AtomicLong id = new AtomicLong();

    @Autowired
    private ConnectionQueue connectionQueue;


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long newId = id.getAndIncrement();

            connectionQueue.getQueue().offer(new Connection(newId, names[(int) (newId % names.length)]));
            log.info("Connection {} added.", newId);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                log.info("Interrupted");
            }
        }
    }
}
