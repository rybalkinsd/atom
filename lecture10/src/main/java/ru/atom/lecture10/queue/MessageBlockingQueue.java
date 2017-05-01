package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ikozin on 26.04.17.
 */
public class MessageBlockingQueue implements BlockingQueue<Message> {
    private static final Logger log = LogManager.getLogger(MessageBlockingQueue.class);
    private static final Object lock = new Object();
    private final int capacity;
    private int size = 0;

    private final Queue<Message> q = new LinkedList<>();

    public MessageBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void put(Message msg) throws InterruptedException {
        synchronized (lock) {
            while (this.remainingCapacity() == 0) {
                log.info("waiting for Message to be deleted");
                lock.wait();
            }
            lock.notifyAll();
            log.info("added new Message");
            q.add(msg);
            size++;
        }
    }

    @Override
    public Message take() throws InterruptedException {
        synchronized (lock) {
            while (this.size() == 0) {
                log.info("Waiting for Message to be added");
                lock.wait();
            }
            lock.notifyAll();
            log.info("deleted Message");
            size--;
            return q.poll();
        }
    }

    @Override
    public int remainingCapacity() {
        synchronized (lock) {
            return capacity - size;
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return size;
        }
    }
}
